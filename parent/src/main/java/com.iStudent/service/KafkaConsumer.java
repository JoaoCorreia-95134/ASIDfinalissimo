package com.iStudent.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iStudent.model.DTOs.ParentDTO;
import com.iStudent.service.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class KafkaConsumer {

    private final ParentService parentService;
    private final ObjectMapper objectMapper;

    @Autowired
    public KafkaConsumer(ParentService parentService, ObjectMapper objectMapper) {
        this.parentService = parentService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "parentTopic", groupId = "myGroup")
    public void listen(String message) {
        System.out.println("Received message: " + message);
        if (message.startsWith("remover")){
            String[] messageParts = message.split("remover: ");
            String id = messageParts[1];
            parentService.deleteParentById(Long.valueOf(id));
        }else{
            try {
                // Convert the message to a JsonNode object
                JsonNode jsonNode = objectMapper.readTree(message);

                // Extract the parent object from the message
                JsonNode parentObject = jsonNode.get("parent");

                // Convert the parent object to ParentDTO
                ParentDTO parentDTO = objectMapper.treeToValue(parentObject, ParentDTO.class);

                System.out.println("Deserialized ParentDTO: " + parentDTO);

                // Validate the EGN field
                if (parentDTO.getEGN() == null || parentDTO.getEGN().isEmpty()) {
                    throw new IllegalArgumentException("EGN cannot be null or empty");
                }

                // Save the parent to the database
                parentService.addParent(parentDTO);
                System.out.println("Parent added to the database");
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
