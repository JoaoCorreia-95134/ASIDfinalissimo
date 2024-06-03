package Asid.G1.saga;

import Asid.G1.saga.model.entity.Student;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.eventuate.tram.commands.common.Command;

import java.io.IOException;

public class CreateStudentCommand implements Command {
    private Student student;


    public CreateStudentCommand(ObjectNode studentJson) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.student = mapper.treeToValue(studentJson, Student.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to convert JSON to Student object");
        }
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
