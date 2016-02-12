package io.sponges.bot.client.messages;

import org.json.JSONObject;
import io.sponges.bot.client.Bot;

public abstract class Message {

    private final Bot bot;
    private final String type;

    public Message(Bot bot, String type) {
        this.bot = bot;
        this.type = type;
    }

    public abstract JSONObject toJson();

    public Bot getBot() {
        return bot;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return toJson().toString();
    }
}