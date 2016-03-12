package io.sponges.bot.client.protocol.msg;

import io.sponges.bot.client.Bot;
import org.json.JSONObject;

public abstract class Message {

    private final Bot bot;
    private final String type;

    protected Message(Bot bot, String type) {
        this.bot = bot;
        this.type = type;
    }

    protected abstract JSONObject toJson();

    public JSONObject getAsJson() {
        JSONObject json = new JSONObject();
        json.put("type", type);
        json.put("client", bot.getClientId());
        json.put("time", System.currentTimeMillis());
        json.put("content", toJson());
        return json;
    }

    @Override
    public String toString() {
        return getAsJson().toString();
    }
}
