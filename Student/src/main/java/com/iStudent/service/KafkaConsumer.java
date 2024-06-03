package com.iStudent.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.iStudent.model.DTOs.StudentDTO;
import com.iStudent.model.entity.Student;
import com.iStudent.repository.StudentRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class KafkaConsumer {
    private final StudentService studentService;
    private final ObjectMapper objectMapper;

    public KafkaConsumer(StudentService studentService, ObjectMapper objectMapper) {
        this.studentService = studentService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = {"studentTopic"}, groupId = "myGroup")
    public void listen(String message) {
        System.out.println("Received message: " + message);
        if (message.startsWith("remover")){
            String[] messageParts = message.split("remover: ");
            String id = messageParts[1];
            studentService.deleteStudentById(Long.valueOf(id));
        }else {
            try {
                // Parse the JSON message
                JsonNode jsonNode = objectMapper.readTree(message);

                // Extract the parent object from the message
                JsonNode studentObject = jsonNode.get("student");

                // Convert the parent object to ParentDTO
                StudentDTO studentDTO = objectMapper.treeToValue(studentObject, StudentDTO.class);

                System.out.println("Deserialized StudentDTO: " + studentDTO);


                studentService.addStudent(studentDTO);
                System.out.println("Student added to the database");

            } catch (IOException e) {
                System.out.println("Failed to convert message to ParentDTO: " + e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
                System.out.println("An error occurred while processing the message: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}