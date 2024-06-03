package com.iStudent.web;

import com.iStudent.model.DTOs.SubjectDTO;
import com.iStudent.model.DTOs.TeacherDTO;
import com.iStudent.model.entity.Subject;
import com.iStudent.model.entity.Teacher;
import com.iStudent.service.SubjectService;
import com.iStudent.service.TeacherCommandService;
import com.iStudent.service.TeacherQueryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherQueryService teacherQueryService;
    private final TeacherCommandService teacherCommandService;
    private final SubjectService subjectService;
    private final ModelMapper mapper;

    @Autowired
    public TeacherController(TeacherQueryService teacherQueryService,ModelMapper mapper,SubjectService subjectService, TeacherCommandService teacherCommandService) {
        this.teacherQueryService = teacherQueryService;
        this.teacherCommandService = teacherCommandService;
        this.subjectService = subjectService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<TeacherDTO>> getAllTeachers() {
        return ResponseEntity
                .ok(teacherQueryService.getAllTeachers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDTO> getTeacherById(@PathVariable("id") Long teacherId) {
        Optional<TeacherDTO> teacher = this.teacherQueryService.getTeacherById(teacherId);

        if (teacher.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();

        } else {
            return ResponseEntity
                    .ok(teacher.get());

        }
    }

    @GetMapping("/getListByTown/{idTown}")
    public ResponseEntity<TeacherDTO[]> getListByTown(@PathVariable("idTown") Long idTown) {
        TeacherDTO[] lista = this.teacherQueryService.getListByTown(idTown);

        if (lista.length == 0) {
            return ResponseEntity
                    .notFound()
                    .build();

        } else {
            return ResponseEntity
                    .ok(lista);

        }
    }

    @PostMapping
    public ResponseEntity<TeacherDTO> addTeacher(@Valid @RequestBody TeacherDTO teacherDTO,
                                                 UriComponentsBuilder uriComponentsBuilder) {

        long teacherId = teacherCommandService.addTeacher(teacherDTO);

        return ResponseEntity.
                created(uriComponentsBuilder.path("/teachers/{id}").
                        build(teacherId)).build();
    }


    @PutMapping("/edit/{id}")
    public ResponseEntity<TeacherDTO> changeSubject(@Valid @RequestBody TeacherDTO teacherDTO,
                                                    @PathVariable("id") Long id) {

        TeacherDTO teacher = this.teacherCommandService.changeTeacherSubject(id, teacherDTO);

        if (teacher == null) {
            return ResponseEntity
                    .notFound()
                    .build();

        } else {
            return ResponseEntity
                    .ok(teacher);

        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TeacherDTO> deleteTeacherById(@PathVariable("id") Long teacherId) {
        this.teacherCommandService.deleteTeacherById(teacherId);

        return ResponseEntity
                .noContent()
                .build();
    }


}
