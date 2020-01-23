/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgMisc;

import com.google.gson.*;
import java.lang.reflect.Type;
import org.bson.types.ObjectId;

/**
 *
 * @author schueler
 */
public class ObjectIdSerializer implements JsonSerializer<ObjectId>, JsonDeserializer<ObjectId>{

    @Override
    public JsonElement serialize(ObjectId id, Type type, JsonSerializationContext jsc) {
        JsonObject json = new JsonObject();
        json.addProperty("$oid", id.toHexString());
        return json;
    }

    @Override
    public ObjectId deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        return new ObjectId(je.getAsJsonObject().get("$oid").getAsString());
    }
}
