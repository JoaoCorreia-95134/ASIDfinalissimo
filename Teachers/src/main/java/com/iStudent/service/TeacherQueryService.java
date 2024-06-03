package com.iStudent.service;

import com.iStudent.model.DTOs.TeacherDTO;
import com.iStudent.model.entity.Subject;
import com.iStudent.model.entity.Teacher;
import com.iStudent.repository.TeacherRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherQueryService {

    private final TeacherRepository teacherRepository;

    private final SubjectService subjectService;


    private final ModelMapper mapper;

    @Autowired
    public TeacherQueryService(TeacherRepository teacherRepository, SubjectService subjectService, ModelMapper mapper) {
        this.teacherRepository = teacherRepository;
        this.subjectService = subjectService;
        this.mapper = mapper;
    }


    public Optional<TeacherDTO> getTeacherById(Long teacherId) {
        return teacherRepository.
                findById(teacherId).
                map(this::mapToTeacherDTO);
    }

    private TeacherDTO mapToTeacherDTO(Teacher teacher) {
        return mapper.map(teacher, TeacherDTO.class);
    }



    public List<TeacherDTO> getAllTeachers() {
        return teacherRepository.
                findAll().
                stream().
                map(this::mapToTeacherDTO).
                toList();
    }

    public TeacherDTO[] getListByTown(Long idTown) {
        List<Teacher> teachers = teacherRepository.findByTownId(idTown);

        return teachers.stream()
                .map(this::mapToTeacherDTO)
                .toArray(TeacherDTO[]::new);
    }
}
