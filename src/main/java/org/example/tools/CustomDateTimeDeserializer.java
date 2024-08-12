package org.example.tools;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.lang.reflect.Type;

public class CustomDateTimeDeserializer implements JsonDeserializer<DateTime>
{
    private static DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");

    public CustomDateTimeDeserializer(String dateTimeFormatPattern){
        formatter=DateTimeFormat.forPattern(dateTimeFormatPattern);
    }
    @Override
    public DateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        return formatter.parseDateTime(json.getAsString());
    }
}