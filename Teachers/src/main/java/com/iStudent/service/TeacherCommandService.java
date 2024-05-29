package com.iStudent.service;

import com.iStudent.model.DTOs.TeacherDTO;
import com.iStudent.model.entity.UsersWithLargeTown;
import com.iStudent.model.entity.Subject;
import com.iStudent.model.entity.Teacher;
import com.iStudent.repository.TeacherRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;


@Service
public class TeacherCommandService {

    private final TeacherRepository teacherRepository;

    private final SubjectService subjectService;

    private final TeacherQueryService teacherQueryService;

    private RestTemplate restTemplate;

    private final ModelMapper mapper;

    @Autowired
    public TeacherCommandService(TeacherRepository teacherRepository,TeacherQueryService teacherQueryService,RestTemplate restTemplate, SubjectService subjectService, ModelMapper mapper) {
        this.teacherRepository = teacherRepository;
        this.subjectService = subjectService;
        this.mapper = mapper;
        this.teacherQueryService = teacherQueryService;
        this.restTemplate = restTemplate;
    }

    public long addTeacher(TeacherDTO teacherDTO) {

        Teacher teacher = mapper.map(teacherDTO, Teacher.class);
        teacherRepository.save(teacher);
        return teacher.getId();
    }


    public TeacherDTO changeTeacherSubject(Long teacherId, TeacherDTO teacherDTO) {

        Optional<Teacher> optionalTeacher = teacherRepository.findById(teacherId);

        if (optionalTeacher.isPresent()) {
            Teacher teacher = optionalTeacher.get();
            Long currentSubjectId = teacher.getSubject().getId();
            teacher.setSubject(mapper.map(teacherDTO.getSubject(), Subject.class));
            teacherRepository.save(teacher);
            subjectService.deleteSubjectById(currentSubjectId);
            return mapToTeacherDTO(teacher);

        }
        return null;
    }


    public void deleteTeacherById(Long teacherId) {
        Optional<Teacher> teacher = teacherRepository.findById(teacherId);

        if (teacher.isPresent()) {
            teacher.get().setSubject(null);
            teacherRepository.save(teacher.get());
            teacherRepository.deleteById(teacherId);
        }
    }


    private TeacherDTO mapToTeacherDTO(Teacher teacher) {
        return mapper.map(teacher, TeacherDTO.class);
    }


}
