package org.example.tools;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.lang.reflect.Type;

public class CustomDateTimeSerializer implements JsonSerializer<DateTime>
{
    private static DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");

    public CustomDateTimeSerializer(String dateTimeormatPattern)
    {
        formatter =DateTimeFormat.forPattern(dateTimeormatPattern);

    }

    @Override
    public JsonElement serialize(DateTime src, Type typeOfSrc, JsonSerializationContext context)
    {
        return new JsonPrimitive(formatter.print(src));
    }
}