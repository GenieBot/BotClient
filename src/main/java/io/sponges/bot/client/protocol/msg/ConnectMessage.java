package io.sponges.bot.client.protocol.msg;

import io.sponges.bot.client.Bot;
import org.json.JSONObject;

public final class ConnectMessage extends Message {

    public ConnectMessage(Bot bot) {
        super(bot, "CONNECT");
    }

    @Override
    protected JSONObject toJson() {
        return new JSONObject();
    }
}
