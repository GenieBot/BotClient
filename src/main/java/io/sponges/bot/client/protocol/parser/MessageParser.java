package io.sponges.bot.client.protocol.parser;

import org.json.JSONObject;

public abstract class MessageParser {

    private final String type;

    protected MessageParser(String type) {
        this.type = type;
    }

    public abstract void parse(long time, JSONObject content);

    public String getType() {
        return type;
    }
}
