package ncadvanced2018.groupeone.parent.util;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class LocalDateDeserializer extends JsonDeserializer <LocalDateTime> {

    @Override
    public LocalDateTime deserialize(com.fasterxml.jackson.core.JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        String string = jsonParser.getText();

        if (string.length() > 20) {
            ZonedDateTime zonedDateTime = ZonedDateTime.parse(string);
            return zonedDateTime.toLocalDateTime();
        }

        return LocalDateTime.parse(string);
    }
}