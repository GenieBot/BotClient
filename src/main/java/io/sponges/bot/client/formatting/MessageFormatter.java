package io.sponges.bot.client.formatting;

import org.json.JSONArray;
import org.json.JSONObject;

@FunctionalInterface
public interface MessageFormatter {

    String format(String input, Attribute attribute);

    default String format(JSONObject json) {
        JSONArray array = json.getJSONArray("parts");
        JSONObject[] parts = new JSONObject[array.length()];
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            parts[object.getInt("order")] = object;
        }
        StringBuilder builder = new StringBuilder();
        for (JSONObject object : parts) {
            String content = object.getString("content");
            JSONArray attributes = object.getJSONArray("attributes");
            for (int i = 0; i < attributes.length(); i++) {
                Attribute attribute = Attribute.valueOf(attributes.getString(i).toUpperCase());
                String response = format(content, attribute);
                builder.append(response);
            }
        }
        return builder.toString();
    }

}
