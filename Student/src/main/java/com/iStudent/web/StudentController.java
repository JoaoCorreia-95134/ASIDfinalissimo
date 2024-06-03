package com.iStudent.web;

import com.iStudent.model.DTOs.MarkDTO;
import com.iStudent.model.DTOs.StudentDTO;
import com.iStudent.model.entity.Student;
import com.iStudent.model.entity.base.BasePersonEntity;
import com.iStudent.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        return ResponseEntity
                .ok(studentService.getAllStudents());
    }

    @GetMapping("/getListByTown/{idTown}")
    public ResponseEntity< StudentDTO[]> getListByTown(@PathVariable("idTown") Long idTown) {
        StudentDTO[] lista = this.studentService.getListByTown(idTown);

        if (lista.length == 0) {
            return ResponseEntity
                    .notFound()
                    .build();

        } else {
            return ResponseEntity
                    .ok(lista);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable("id") Long studentId) {
        Optional<StudentDTO> student = this.studentService.getStudentById(studentId);

        if (student.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();

        } else {
            return ResponseEntity
                    .ok(student.get());

        }
    }


    @GetMapping("/real/{id}")
    public ResponseEntity<Student> getStudentRealById(@PathVariable("id") Long studentId) {
        Optional<Student> student = this.studentService.getStudentRealById(studentId);

        if (student.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();

        } else {
            return ResponseEntity
                    .ok(student.get());

        }
    }

    @GetMapping("getParent/{id}")
    public ResponseEntity<Long> getParentId(@PathVariable("id") Long studentId) {
        Optional<Long> parentId = this.studentService.getParentId(studentId);
        if (parentId.isEmpty()) {

            return ResponseEntity
                    .notFound()
                    .build();

        } else {
            return ResponseEntity
                    .ok(parentId.get());

        }
    }


    @PostMapping
    public ResponseEntity<StudentDTO> addStudent(@Valid @RequestBody StudentDTO studentDTO,
                                                 UriComponentsBuilder uriComponentsBuilder) {

        long newStudentId = studentService.addStudent(studentDTO);
        return ResponseEntity
                .created(uriComponentsBuilder.path("/students/{id}")
                        .build(newStudentId))
                .build();
    }


    @PostMapping("/{id}/add/mark")
    public ResponseEntity<StudentDTO> addMarkToStudent(@PathVariable("id") Long studentId,
                                                       @Valid @RequestBody MarkDTO markDTO) {

        if (studentService.addMarkToStudent(studentId, markDTO)) {
            return ResponseEntity.
                    ok().
                    build();
        } else {
            return ResponseEntity.
                    notFound().
                    build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StudentDTO> deleteStudentById(@PathVariable("id") Long studentId) {
        this.studentService.deleteStudentById(studentId);

        return ResponseEntity
                .noContent()
                .build();
    }

}
