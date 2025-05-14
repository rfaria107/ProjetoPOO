package org.spotifumtp37.util;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapter
        implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

    // Choose your format (ISO is a good default)
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext ctx) {
        return new JsonPrimitive(src.format(FORMATTER));
    }

    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext ctx)
            throws JsonParseException {
        return LocalDateTime.parse(json.getAsString(), FORMATTER);
    }
}
