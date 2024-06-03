package com.iStudent.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iStudent.model.DTOs.ClubDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class KafkaConsumer {

    private final ClubService clubService;
    private final ObjectMapper objectMapper;

    @Autowired
    public KafkaConsumer(ClubService clubService, ObjectMapper objectMapper) {
        this.clubService = clubService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "clubTopic", groupId = "myGroup")
    public void listen(String message) {
        System.out.println("Received message: " + message);
        if (message.startsWith("remover")){
            String[] messageParts = message.split("remover: ");
            String id = messageParts[1];
            clubService.deleteClubById(Long.valueOf(id));
        }else {
            try {

                JsonNode jsonNode = objectMapper.readTree(message);

                // Extract the parent object from the message
                JsonNode clubObject = jsonNode.get("club");

                // Convert the parent object to ParentDTO
                ClubDTO clubDTO = objectMapper.treeToValue(clubObject, ClubDTO.class);

                System.out.println("Deserialized ClubDTO: " + clubDTO);
                // Parse the JSON message
                // Assuming that the message contains only the ClubDTO object as JSON


                // Save the club to the database
                clubService.addClub(clubDTO);
                System.out.println("Club added to the database");

            } catch (IOException e) {
                System.out.println("Failed to convert message to ClubDTO: " + e.getMessage());
                e.printStackTrace();
            }
        }
        clubService.setCont(0);
    }
}
