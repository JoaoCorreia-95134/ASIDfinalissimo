package Asid.G1.saga;

import Asid.G1.saga.model.entity.Club;
import Asid.G1.saga.model.entity.Parent;
import Asid.G1.saga.model.entity.Student;
import io.eventuate.tram.sagas.orchestration.SagaInstance;
import org.bouncycastle.crypto.io.SignerOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SagaService {

    private final KafkaConsumer kafkaConsumer;
    private final CreateStudentSagaOrchestrator sagaOrchestrator;

    @Autowired
    public SagaService(KafkaConsumer kafkaConsumer, CreateStudentSagaOrchestrator sagaOrchestrator) {
        this.kafkaConsumer = kafkaConsumer;
        this.sagaOrchestrator = sagaOrchestrator;
    }

    public StudentClubParentDTO  createStudent(Student student, Club club, Parent parent) throws InterruptedException {
        SagaInstance sagaInstance = sagaOrchestrator.orchestrateSaga(student, club, parent);

        if(sagaInstance == null){
            return null;
        }


        // Consuma eventos do Kafka até que a saga seja concluída
        while (!sagaOrchestrator.isSagaCompleted(sagaInstance)) {
            // Aguarde um pouco antes de verificar novamente
            Thread.sleep(1000);
        }
        System.out.println("----------------Saga completed-------------------");


        return new StudentClubParentDTO(student, club, parent);
    }

public class StudentClubParentDTO {
    private Student student;
    private Club club;
    private Parent parent;

    public StudentClubParentDTO(Student student, Club club, Parent parent) {
        this.student = student;
        this.club = club;
        this.parent = parent;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

}}
