package Asid.G1.saga;

import Asid.G1.saga.model.entity.Club;
import Asid.G1.saga.model.entity.Parent;
import Asid.G1.saga.model.entity.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.eventuate.tram.commands.consumer.CommandWithDestination;
import io.eventuate.tram.sagas.orchestration.SagaDefinition;
import io.eventuate.tram.sagas.simpledsl.SimpleSaga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.processing.Completion;
import java.util.ArrayList;
import java.util.List;

@Component
public class CreateStudentSaga implements SimpleSaga<CreateStudentSagaData> {


    private final SagaDefinition<CreateStudentSagaData> sagaDefinition;
    private final KafkaProducer kafkaProducer;
    private final ObjectMapper objectMapper;

    @Autowired
    public CreateStudentSaga(KafkaProducer kafkaProducer,
                             ObjectMapper objectMapper) {
        this.kafkaProducer = kafkaProducer;
        this.objectMapper = objectMapper;
        this.sagaDefinition = buildSagaDefinition();
    }

    private SagaDefinition<CreateStudentSagaData> buildSagaDefinition() {

        return step()
                        .invokeParticipant(data -> {
//                            System.out.println("CreateStudentSaga: clubService.create");
//                            ObjectNode clubJson = objectMapper.createObjectNode();
//
//                            clubJson.put("name", data.getClub().getName());
//                            clubJson.put("description", data.getClub().getDescription());
//
//                            try {
//                                kafkaProducer.sendMessage("clubTopic", objectMapper.writeValueAsString(new CreateClubCommand(clubJson)));
//                            } catch (JsonProcessingException e) {
//                                throw new RuntimeException(e);
//                            }
                            return null;

                        })
                        .withCompensation(data -> {
                            System.out.println("Compensating for failed club creation");

                            return null;
                        })
                        .step()
                        .invokeParticipant(data -> {
//                            System.out.println(data.getIsClubCreated());
//                            if (!data.getIsClubCreated()) {
//                                throw new RuntimeException("Clube não foi criado com sucesso");
//                            }
//
//                            System.out.println("CreateParentSaga: parentService.create");
//
//                            ObjectNode parentJson = objectMapper.createObjectNode();
//
//                            parentJson.put("firstName", data.getParent().getFirstName());
//                            parentJson.put("middleName", data.getParent().getMiddleName());
//                            parentJson.put("lastName", data.getParent().getLastName());
//                            parentJson.put("age", data.getParent().getAge());
//                            parentJson.put("gender", data.getParent().getGender().toString());
//                            parentJson.put("email", data.getParent().getEmail());
//                            parentJson.put("phoneNumber", data.getParent().getPhoneNumber());
//                            parentJson.put("town", data.getParent().getTown());
//                            parentJson.put("egn", data.getParent().getEGN());
//
//                            try {
//                                kafkaProducer.sendMessage("parentTopic", objectMapper.writeValueAsString(new CreateParentCommand(parentJson)));
//                            } catch (JsonProcessingException e) {
//                                throw new RuntimeException(e);
//                            }
//                            data.setIsParentCreated(true);
//
                            return null;

                        })
                        .withCompensation(data -> {
                            System.out.println("Compensating for failed parent creation");
                            // Implement your compensation logic here
                            return null;
                        })
                        .step()
                        .invokeParticipant(data -> {
//                            if (!data.getIsParentCreated()) {
//                                String desinstalar = "remover: " + data.getClub().getId();
//                                System.out.println(desinstalar);
//                                kafkaProducer.sendMessage("clubTopic", desinstalar);
//                                throw new RuntimeException("Parent não foi criado com sucesso");
//                            }
//
//                            ObjectNode studentJson = objectMapper.createObjectNode();
//
//                            studentJson.put("firstName", data.getStudent().getFirstName());
//                            studentJson.put("middleName", data.getStudent().getMiddleName());
//                            studentJson.put("lastName", data.getStudent().getLastName());
//                            studentJson.put("age", data.getStudent().getAge());
//                            studentJson.put("egn", data.getStudent().getEGN());
//                            studentJson.put("town", data.getStudent().getTown());
//                            studentJson.put("email", data.getStudent().getEmail());
//                            studentJson.put("gender", data.getStudent().getGender().toString());
//                            studentJson.put("parent", data.getStudent().getParentId());
//
//                            try {
//                                kafkaProducer.sendMessage("studentTopic", objectMapper.writeValueAsString(new CreateStudentCommand(studentJson)));
//                            } catch (JsonProcessingException e) {
//                                throw new RuntimeException(e);
//                            }

                            return null;
                        })
                        .withCompensation(data -> {
                            System.out.println("Compensating for failed student creation");
                            // Implement your compensation logic here
                            return null;
                        })
                        .build();
    }



    @Override
    public SagaDefinition<CreateStudentSagaData> getSagaDefinition() {
        return this.sagaDefinition;
    }
}
