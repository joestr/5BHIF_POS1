/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg01cargeo.serialzers;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;
import java.lang.reflect.Type;

/**
 *
 * @author Joel
 */
public class PointSerializer implements JsonSerializer<Point>, JsonDeserializer<Point> {

    @Override
    public JsonElement serialize(Point t, Type type, JsonSerializationContext jsc) {
        JsonObject jo = new JsonObject();
        JsonArray ja = new JsonArray();
        ja.add(t.getCoordinates().getValues().get(0));
        ja.add(t.getCoordinates().getValues().get(1));
        jo.addProperty("type", "Point");
        jo.add("coordinates", ja);
        return jo;
    }

    @Override
    public Point deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        return new Point(
            new Position(
                je.getAsJsonObject().get("coordinates").getAsJsonArray().get(0).getAsDouble(),
                je.getAsJsonObject().get("coordinates").getAsJsonArray().get(1).getAsDouble()
            )
        );
    }
}
