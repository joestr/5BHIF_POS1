/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgMisc;

import com.google.gson.*;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;
import java.lang.reflect.Type;

/**
 *
 * @author schueler
 */
public class GsonPointSerializer implements JsonSerializer<Point>, JsonDeserializer<Point> {

    @Override
    public JsonElement serialize(Point point, Type type, JsonSerializationContext jsc) {
        JsonObject jo = new JsonObject();
        JsonArray arrCoo = new JsonArray();
        arrCoo.add(point.getCoordinates().getValues().get(0));
        arrCoo.add(point.getCoordinates().getValues().get(1));
        jo.addProperty("type","Point");
        jo.add("coordinates",arrCoo);
        return jo;
    }

    @Override
    public Point deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        return new Point(new Position(
                je.getAsJsonObject().get("coordinates").getAsJsonArray().get(0).getAsDouble(),
                je.getAsJsonObject().get("coordinates").getAsJsonArray().get(1).getAsDouble()));
    }
}
