// SubscriptionPlanAdapter.java
package org.spotifumtp37.util;

import com.google.gson.*;
import org.spotifumtp37.model.subscription.*;

import java.lang.reflect.Type;

/**
 * Handles both writing and reading SubscriptionPlan subtypes
 */
public class SubscriptionPlanAdapter
        implements JsonSerializer<SubscriptionPlan>, JsonDeserializer<SubscriptionPlan> {

    @Override
    public JsonElement serialize(SubscriptionPlan src, Type typeOfSrc, JsonSerializationContext ctx) {
        JsonObject obj = (JsonObject) ctx.serialize(src, src.getClass());
        // add a "type" property so we can round‑trip it
        obj.addProperty("type", src.getClass().getSimpleName());
        return obj;
    }

    @Override
    public SubscriptionPlan deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext ctx)
            throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        String kind = obj.has("type") ? obj.get("type").getAsString() : "FreePlan";
        return switch (kind) {
            case "PremiumBase" -> ctx.deserialize(obj, PremiumBase.class);
            case "PremiumTop" -> ctx.deserialize(obj, PremiumTop.class);
            default -> ctx.deserialize(obj, FreePlan.class);
        };
    }
}
