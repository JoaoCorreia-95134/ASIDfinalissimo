package Asid.G1.saga.model.entity;

import Asid.G1.saga.model.entity.base.BasePersonEntity;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "students")
public class Student extends BasePersonEntity {

    private Long parent_id;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "students_clubs",
//            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "club_id", referencedColumnName = "id")
//    )
//    private Set<Club> clubs;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "students_marks",
//            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "mark_id", referencedColumnName = "id")
//    )
    //private List<Mark> marks;

    @Column(name = "enroll_date", nullable = true)
    private LocalDate enrollDate;

    public Student() {

    }

//    public Set<Club> getClubs() {
//        return Collections.unmodifiableSet(clubs);
//    }
//
//    public List<Mark> getMarks() {
//        return Collections.unmodifiableList(marks);
//    }

    public LocalDate getEnrollDate() {
        return enrollDate ;
    }

    public void setEnrollDate(LocalDate enrollDate) {
        this.enrollDate = enrollDate;
    }


    public Long getParentId() {
        return parent_id;
    }

    public void setParent(Long parent_id) {
        this.parent_id = parent_id;
    }

//    public void addMark(Mark mark) {
//        marks.add(mark);
//    }
//
//    public void addClub(Club club) {
//        clubs.add(club);
//    }
//
//    public void removeClub(Club club) {
//        clubs.remove(club);
//    }

    public void removeParentId() {
        this.parent_id = null;
    }


}
