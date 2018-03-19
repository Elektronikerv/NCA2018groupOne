package ncadvanced2018.groupeone.parent.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.model.entity.Address;
import ncadvanced2018.groupeone.parent.model.entity.Role;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealAddress;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealUser;
import ncadvanced2018.groupeone.parent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class CustomDeserializer extends JsonDeserializer<User> {

    private UserService userService;

    @Autowired
    public CustomDeserializer(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        User user = new RealUser();

        JsonNode root = jsonParser.getCodec().readTree(jsonParser);
        Long idNode = null;
        if (root.get("id") != null) {
            idNode = root.get("id").asLong();
        }
        String emailNode = root.get("email").asText();
        if (root.get("password") != null) {
            String passwordNode = root.get("password").asText();
            user.setPassword(passwordNode);
        }
        String firstNameNode = root.get("firstName").asText();
        String lastNameNode = root.get("lastName").asText();
        String phoneNumberNode = root.get("phoneNumber").asText();
        if (root.get("managerId") != null && !root.get("managerId").toString().equals("null")) {
            Long idManagerNode = root.get("managerId").asLong();
            User managerNode = userService.findById(idManagerNode);
            user.setManager(managerNode);
        }

        if (root.get("address") != null) {
            Address address = new RealAddress();
            if (root.get("address").get("id") != null) {
                Long streetIdNode = root.get("address").get("id").asLong();
                address.setId(streetIdNode);
            }
            address.setStreet(root.get("address").get("street").asText());
            address.setHouse(root.get("address").get("house").asText());
            address.setFloor(root.get("address").get("floor").asInt());
            address.setFlat(root.get("address").get("flat").asInt());
            user.setAddress(address);
        }

        if (root.get("roles") != null) {
            JsonNode rolesNode = root.get("roles");
            Set<Role> roles = new HashSet<>();

            for (JsonNode roleNode : rolesNode) {
                Role role = null;
                if (roleNode != null) {
                    role = Role.valueOf(roleNode.asText());//getRoleById
                }
                if(role != null){
                    roles.add(role);
                }
            }
            user.setRoles(roles);
        }

        user.setId(idNode);
        user.setEmail(emailNode);

        user.setFirstName(firstNameNode);
        user.setLastName(lastNameNode);
        user.setPhoneNumber(phoneNumberNode);

        return user;
    }
}
