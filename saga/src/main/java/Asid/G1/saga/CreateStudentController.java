package Asid.G1.saga;

import Asid.G1.saga.model.entity.Club;
import Asid.G1.saga.model.entity.Parent;
import Asid.G1.saga.model.entity.Student;
import Asid.G1.saga.model.entity.enums.GenderEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/saga")
public class CreateStudentController {

    private final SagaService sagaService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public CreateStudentController(SagaService sagaService, KafkaTemplate<String, String> kafkaTemplate) {
        this.sagaService = sagaService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/createStudent")
    public ResponseEntity<SagaService.StudentClubParentDTO> createStudent(@RequestBody Map<String, Object> payload) {
        System.out.println("Inicio");

        try {
            Student student = parseStudent(payload);
            Club club = parseClub(payload);
            Parent parent = parseParent(payload);

            SagaService.StudentClubParentDTO createdStudent = sagaService.createStudent(student, club, parent);

            if( createdStudent==null){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

            return ResponseEntity.ok(createdStudent);
        } catch (Exception e) {
            System.out.println("Student creation failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private Student parseStudent(Map<String, Object> payload) {
        Map<String, Object> studentData = (Map<String, Object>) payload.get("students");
        Student student = new Student();
        student.setFirstName((String) studentData.get("first_name"));
        student.setMiddleName((String) studentData.get("middle_name"));
        student.setLastName((String) studentData.get("last_name"));
        student.setEGN((String) studentData.get("egn"));
        student.setAge((Integer) studentData.get("age"));
        student.setTown(((Number) studentData.get("town")).longValue());
        student.setEmail((String) studentData.get("email"));
        student.setGender(GenderEnum.valueOf((String) studentData.get("gender")));
        student.setParent(((Number) studentData.get("parent")).longValue());


        return student;
    }

    private Club parseClub(Map<String, Object> payload) {
        Map<String, Object> clubData = (Map<String, Object>) payload.get("clubs");

        Club club = new Club();

        club.setName((String) clubData.get("name"));
        club.setDescription((String) clubData.get("description"));

        return club;
    }

    private Parent parseParent(Map<String, Object> payload) {
        Map<String, Object> parentData = (Map<String, Object>) payload.get("parents");

        Parent parent = new Parent();

        parent.setFirstName((String) parentData.get("first_name"));
        parent.setMiddleName((String) parentData.get("middle_name"));
        parent.setLastName((String) parentData.get("last_name"));
        parent.setAge((Integer) parentData.get("age"));
        parent.setTown(((Number) parentData.get("town")).longValue());
        parent.setEmail((String) parentData.get("email"));
        parent.setPhoneNumber((String) parentData.get("phoneNumber"));
        parent.setEGN((String) parentData.get("egn"));
        parent.setGender(GenderEnum.valueOf((String) parentData.get("gender")));


        return parent;
    }
}
