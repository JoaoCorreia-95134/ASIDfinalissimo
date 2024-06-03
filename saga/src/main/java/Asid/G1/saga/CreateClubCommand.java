package Asid.G1.saga;

import Asid.G1.saga.model.entity.Club;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.eventuate.tram.commands.common.Command;
import java.io.IOException;

public class CreateClubCommand implements Command {
    private Club club;


    public CreateClubCommand(ObjectNode clubJson) {
        // Convert JSON to Club object
        this.club = convertJsonToClub(clubJson);
    }

    // Getters and Setters
    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    // Method to convert JSON to Club object
    private Club convertJsonToClub(ObjectNode clubJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.treeToValue(clubJson, Club.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to convert JSON to Club object", e);
        }
    }
}
