package ncadvanced2018.groupeone.parent.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.joda.time.format.DateTimeFormat;

import java.io.IOException;
import java.time.LocalDateTime;

public class CustomDeserializerForTime extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        JsonNode root = jsonParser.getCodec().readTree(jsonParser);

        String receiverAvailabilityTime;

        receiverAvailabilityTime = root.asText();

        String newTime = receiverAvailabilityTime + ":00";

        DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime time = LocalDateTime.parse(newTime);

        return time;
    }
}
