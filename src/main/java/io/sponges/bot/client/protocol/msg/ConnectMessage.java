package io.sponges.bot.client.protocol.msg;

import io.sponges.bot.client.Bot;
import org.json.JSONObject;

public final class ConnectMessage extends Message {

    private final Bot bot;

    public ConnectMessage(Bot bot) {
        super(bot, "CONNECT");
        this.bot = bot;
    }

    @Override
    protected JSONObject toJson() {
        return new JSONObject().put("prefix", bot.getDefaultPrefix());
    }
}
