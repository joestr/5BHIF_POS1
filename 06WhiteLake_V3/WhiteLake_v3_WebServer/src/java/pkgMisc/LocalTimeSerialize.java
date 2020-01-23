/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgMisc;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;

/**
 *
 * @author schueler
 */
public class LocalTimeSerialize implements JsonSerializer<LocalTime>, JsonDeserializer<LocalTime>{

    @Override
    public JsonElement serialize(LocalTime t, Type type, JsonSerializationContext jsc) {
        JsonObject json = new JsonObject();
            json.addProperty("hour", t.getHour());
            json.addProperty("minute", t.getMinute());
            return json;
    }

    @Override
    public LocalTime deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        JsonObject jsonObject = je.getAsJsonObject();
        return LocalTime.of(jsonObject.get("hour").getAsInt(), jsonObject.get("minute").getAsInt());
    }
    
}
