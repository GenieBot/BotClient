package io.sponges.bot.client.util;

import io.sponges.bot.client.messages.Message;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JSONBuilder {

    /**
     * Create an instance of the Builder
     * @param message the Message in which JSON is required
     * @return Builder instance
     */
    public static Builder create(Message message) {
        return new Builder(message);
    }

    /**
     * Static Builder class with Builder pattern
     */
    public static class Builder {

        private final String clientId;
        private final String type;

        private Map<String, Object> values = new HashMap<>();

        public Builder(Message message) {
            this.clientId = message.getBot().getClientId();
            this.type = message.getType();
        }

        /**
         * Set the values Map
         * @param values the Map to set values to
         * @return Builder instance
         */
        public Builder withValues(Map<String, Object> values) {
            this.values = values;
            return this;
        }

        /**
         * Adds a value to the values Map
         * @param key the key to assign the value to
         * @param value the value to assign to the key
         * @return Builder instance
         */
        public Builder withValue(String key, Object value) {
            this.values.put(key, value);
            return this;
        }

        public BuilderObject withNewObject(String key) {
            return new BuilderObject(this, key);
        }

        /**
         * Constructs the JSON from the set values & data from Message instance
         * @return JSONObject for message
         */
        public JSONObject build() {
            JSONObject object = new JSONObject();

            object.put("type", type);
            object.put("client-id", clientId);

            for (String key : values.keySet()) {
                object.put(key, values.get(key));
            }

            return object;
        }

    }

    public static class BuilderObject {

        private Map<String, Object> values = new HashMap<>();

        private final Builder builder;
        private final String key;

        public BuilderObject(Builder builder, String key) {
            this.builder = builder;
            this.key = key;
        }

        /**
         * Adds a value to the values Map
         * @param key the key to assign the value to
         * @param value the value to assign to the key
         * @return Builder instance
         */
        public BuilderObject withValue(String key, Object value) {
            this.values.put(key, value);
            return this;
        }

        public Builder build() {
            JSONObject object = new JSONObject();

            for (String key : values.keySet()) {
                object.put(key, values.get(key));
            }

            builder.withValue(key, object);
            return builder;
        }

    }

}
