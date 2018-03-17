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
        if (root.toString().startsWith("[")) {
            int i = 0;
            String year = root.toString().substring(1, 5);

            //root.toString() have format [yyyy,MM,dd,HH,mm,ss]
            String month = root.toString().substring(6, 8);
            if (month.endsWith(",")) {
                i += 1;
                month = root.toString().substring(6, 8 - i);
                StringBuilder stringBuilder = new StringBuilder(month);
                stringBuilder.setCharAt(0, '0');
                month = stringBuilder.toString() + month;
            }
            String day = root.toString().substring(9 - i, 11 - i);
            if (day.endsWith(",")) {
                i += 1;
                day = root.toString().substring(9 - i, 11 - i);
                StringBuilder stringBuilder = new StringBuilder(day);
                stringBuilder.setCharAt(0, '0');
                day = stringBuilder.toString() + day;
            }
            String hour = root.toString().substring(12 - i, 14 - i);
            if (hour.endsWith(",")) {
                i += 1;
                hour = root.toString().substring(12 - i, 14 - i);
                StringBuilder stringBuilder = new StringBuilder(hour);
                stringBuilder.setCharAt(0, '0');
                hour = stringBuilder.toString() + hour;
            }
            String minutes = root.toString().substring(15 - i, 17 - i);
            if (minutes.endsWith(",") || minutes.endsWith("]")) {
                i += 1;
                minutes = root.toString().substring(15 - i, 17 - i);
                StringBuilder stringBuilder = new StringBuilder(minutes);
                stringBuilder.setCharAt(0, '0');
                minutes = stringBuilder.toString();
            }

            receiverAvailabilityTime = year + "-" + month + "-" + day + "T" + hour + ":" + minutes;
        }

        String newTime = receiverAvailabilityTime + ":00";

        DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

        return LocalDateTime.parse(newTime);
    }

}
