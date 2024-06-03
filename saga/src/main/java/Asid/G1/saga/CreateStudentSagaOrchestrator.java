package Asid.G1.saga;

import Asid.G1.saga.model.entity.Club;
import Asid.G1.saga.model.entity.Parent;
import Asid.G1.saga.model.entity.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.eventuate.tram.sagas.orchestration.SagaInstance;
import io.eventuate.tram.sagas.orchestration.SagaManager;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;

@Service
public class CreateStudentSagaOrchestrator {

    private final KafkaProducer kafkaProducer;
    private final KafkaConsumer<String, String> kafkaConsumer;
    private final SagaManager<CreateStudentSagaData> sagaManager;

    private boolean studentOk = false;
    private boolean clubOk = false;
    private boolean parentOk = false;
    private boolean erro = false;
    private Long idClub = null;
    private Long  idStudent = null;
    private Long idParent = null;


    @Autowired
    public CreateStudentSagaOrchestrator(KafkaProducer kafkaProducer, KafkaConsumer<String, String> kafkaConsumer, SagaManager<CreateStudentSagaData> sagaManager) {
        this.kafkaProducer = kafkaProducer;
        this.kafkaConsumer = kafkaConsumer;

        this.sagaManager = sagaManager;
    }

    public SagaInstance orchestrateSaga(Student student, Club club, Parent parent) {
        System.out.println("-------Orchestrating saga------------");
        this.kafkaConsumer.subscribe(Arrays.asList("parentOkTopic", "studentOkTopic", "clubOkTopic"));
        kafkaConsumer.poll(Duration.ofMillis(3000));

        CreateStudentSagaData sagaData = new CreateStudentSagaData(student, club, parent);
        SagaInstance sagaInstance = sagaManager.create(sagaData);

        Integer contParent=0;
        Integer contStudent=0;
        Integer contErro=0;
        Integer contClub=0;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode parentJson = objectMapper.createObjectNode();
            ObjectNode clubJson = objectMapper.createObjectNode();
            ObjectNode studentJson = objectMapper.createObjectNode();

            parentJson.put("firstName", parent.getFirstName());
            parentJson.put("middleName", parent.getMiddleName());
            parentJson.put("lastName", parent.getLastName());
            parentJson.put("age", parent.getAge());
            parentJson.put("gender", parent.getGender().toString());
            parentJson.put("email", parent.getEmail());
            parentJson.put("phoneNumber", parent.getPhoneNumber());
            parentJson.put("town", parent.getTown());
            parentJson.put("egn", parent.getEGN());
            System.out.println("----------------parentJson-------------------");
            System.out.println(parentJson);

            clubJson.put("name", club.getName());
            clubJson.put("description", club.getDescription());
            System.out.println("----------------clubJson-------------------");
            System.out.println(clubJson);

            studentJson.put("firstName", student.getFirstName());
            studentJson.put("middleName", student.getMiddleName());
            studentJson.put("lastName", student.getLastName());
            studentJson.put("age", student.getAge());
            studentJson.put("egn", student.getEGN());
            studentJson.put("town", student.getTown());
            studentJson.put("email", student.getEmail());
            studentJson.put("gender", student.getGender().toString());
            studentJson.put("parent", student.getParentId());
            System.out.println("----------------studentJson-------------------");
            System.out.println(studentJson);



            kafkaProducer.sendMessage("parentTopic", objectMapper.writeValueAsString(new CreateParentCommand(parentJson)));
            kafkaProducer.sendMessage("clubTopic", objectMapper.writeValueAsString(new CreateClubCommand(clubJson)));
            kafkaProducer.sendMessage("studentTopic", objectMapper.writeValueAsString(new CreateStudentCommand(studentJson)));



            while (!studentOk || !clubOk || !parentOk) {

                ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(3000));

                for (ConsumerRecord<String, String> record : records) {

                    if (record.topic().equals("parentOkTopic") && record.value().startsWith("OK")) {
                        System.out.println(record.value());
                        System.out.println("parent");
                        String[] messageParts = record.value().split("OK: ");
                        String id = messageParts[1];

                        parentOk = true;
                        idParent = Long.valueOf(id);

                        contParent=1;
                    } else if (record.topic().equals("studentOkTopic") && record.value().startsWith("OK")) {
                        System.out.println(record.value());
                        System.out.println("student");
                        String[] messageParts = record.value().split("OK: ");
                        String id = messageParts[1];

                        studentOk = true;
                        idStudent = Long.valueOf(id);


                        contStudent=1;
                    } else if (record.topic().equals("clubOkTopic") && record.value().startsWith("OK")) {

                        System.out.println(record.value());
                        System.out.println("club");
                        String[] messageParts = record.value().split("OK: ");
                        String id = messageParts[1];

                        clubOk = true;
                        idClub = Long.valueOf(id);


                        contClub=1;
                    } else if (record.value().startsWith("Erro")) {
                        System.out.println(record.value());
                        erro=true;
                        contErro=1;
                    }
                    if(erro==true){
                        if(clubOk){
                            String desinstalar = "remover: " + idClub;
                            System.out.println(desinstalar);
                            kafkaProducer.sendMessage("clubTopic", desinstalar);
                            clubOk=false;
                        }
                        if(parentOk){
                            String desinstalar = "remover: " + idParent;
                            kafkaProducer.sendMessage("parentTopic", desinstalar);
                            parentOk=false;
                        }
                        if(studentOk){
                            String desinstalar = "remover: " + idStudent;
                            kafkaProducer.sendMessage("studentTopic", desinstalar);
                            studentOk=false;
                        }
                        if(contParent+contErro+contClub+contStudent==3){

                            erro=false;
                            System.out.println("-------Saga orchestration completed------------");

                            return null;
                        }
                    }
                }
            }

            System.out.println("-------Saga orchestration completed------------");

        } catch (JsonProcessingException e) {
            System.out.println("Failed to convert command to JSON: " + e.getMessage());
        }



        sagaInstance.setEndState(true);

        return sagaInstance;
    }





    public boolean isSagaCompleted(SagaInstance sagaInstance) {
        if(sagaInstance.isEndState() && studentOk && clubOk && parentOk){
            studentOk=false;
            clubOk=false;
            parentOk=false;

            return sagaInstance.isEndState();
        }
        return false;

    }
}
