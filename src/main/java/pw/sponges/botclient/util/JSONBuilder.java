package pw.sponges.botclient.util;

import org.json.JSONObject;
import pw.sponges.botclient.Bot;

import java.util.HashMap;
import java.util.Map;

public class JSONBuilder {

    public static Builder create(Bot bot) {
        return new Builder(bot);
    }

    public static class Builder {

        private String clientId;

        private String type = null;
        private Map<String, Object> values = new HashMap<>();

        public Builder(Bot bot) {
            this.clientId = bot.getClientId();
        }

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public Builder withValue(String key, String value) {
            this.values.put(key, value);
            return this;
        }

        public Builder withValue(String key, Object object) {
            this.values.put(key, object);
            return this;
        }

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

}
