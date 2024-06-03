package Asid.G1.saga;

import Asid.G1.saga.model.entity.Student;
import Asid.G1.saga.model.entity.Club;
import Asid.G1.saga.model.entity.Parent;

public class CreateStudentSagaData {

    private Student student;
    private Club club;
    private Parent parent;
    private boolean isClubCreated = false;
    private boolean isParentCreated = false;

    public CreateStudentSagaData(Student student, Club club, Parent parent) {
        this.student = student;
        this.club = club;
        this.parent = parent;
    }

}
