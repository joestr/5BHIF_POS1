/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg01cargeo.serialzers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 *
 * @author Joel
 */
public class LocalDateTimeSerializer implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
    
    @Override
    public JsonElement serialize(LocalDateTime date, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject json = new JsonObject();
        json.addProperty("$date", date.toInstant(ZoneOffset.UTC).toEpochMilli());
        return json;
    }

    @Override
    public LocalDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return Instant.ofEpochMilli(jsonElement.getAsJsonObject().get("$date").getAsLong()).atZone(ZoneOffset.UTC).toLocalDateTime();
    }
}