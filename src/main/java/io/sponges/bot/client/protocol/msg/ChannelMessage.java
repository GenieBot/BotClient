package io.sponges.bot.client.protocol.msg;

import io.sponges.bot.client.Bot;
import org.json.JSONObject;

public class ChannelMessage extends Message {

    private final String id;
    private final String message;

    public ChannelMessage(Bot bot, String id, String message) {
        super(bot, "CHANNEL_MESSAGE");
        this.id = id;
        this.message = message;
    }

    @Override
    protected JSONObject toJson() {
        return new JSONObject()
                .put("message", message)
                .put("id", id);
    }
}
