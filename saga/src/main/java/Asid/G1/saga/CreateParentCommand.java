package Asid.G1.saga;

import Asid.G1.saga.model.entity.Parent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.eventuate.tram.commands.common.Command;
import java.io.IOException;

public class CreateParentCommand implements Command {
    private Parent parent;



    public CreateParentCommand(ObjectNode parentJson) {
        // Convert JSON to Parent object
        this.parent = convertJsonToParent(parentJson);

    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    // Method to convert JSON to Parent object
    private Parent convertJsonToParent(ObjectNode parentJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.treeToValue(parentJson, Parent.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to convert JSON to Parent object", e);
        }
    }
}
