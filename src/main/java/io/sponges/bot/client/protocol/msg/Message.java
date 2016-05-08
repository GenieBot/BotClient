package io.sponges.bot.client.protocol.msg;

import io.sponges.bot.client.Bot;
import org.json.JSONObject;

import java.util.UUID;
import java.util.function.Consumer;

public abstract class Message {

    private final Bot bot;
    private final String type;
    private final String id;

    private volatile boolean hasCallback = false;

    protected Message(Bot bot, String type) {
        this.bot = bot;
        this.type = type;
        this.id = UUID.randomUUID().toString();
    }

    protected abstract JSONObject toJson();

    public JSONObject getAsJson() {
        JSONObject json = new JSONObject();
        json.put("type", type);
        json.put("client", bot.getClientId());
        json.put("time", System.currentTimeMillis());
        json.put("id", id);
        json.put("content", toJson());
        return json;
    }

    @Override
    public String toString() {
        return getAsJson().toString();
    }

    public void registerCallback(Consumer<JSONObject> callback) throws CallbackAlreadyRegisteredException {
        if (hasCallback) throw new CallbackAlreadyRegisteredException();
        hasCallback = true;
        bot.getParserManager().registerCallback(id, callback);
    }

    public class CallbackAlreadyRegisteredException extends Exception {
    }

}
