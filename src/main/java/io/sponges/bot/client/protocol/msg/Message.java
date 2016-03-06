package io.sponges.bot.client.protocol.msg;

import org.json.JSONObject;

public abstract class Message {

    private final String type;

    protected Message(String type) {
        this.type = type;
    }

    protected abstract JSONObject toJson();

    public JSONObject getAsJson() {
        JSONObject json = new JSONObject();
        json.put("type", type);
        json.put("time", System.currentTimeMillis());
        json.put("content", toJson());
        return json;
    }

    @Override
    public String toString() {
        return getAsJson().toString();
    }
}
