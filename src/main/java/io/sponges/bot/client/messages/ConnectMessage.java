package io.sponges.bot.client.messages;

import io.sponges.bot.client.Bot;
import io.sponges.bot.client.util.JSONBuilder;
import org.json.JSONObject;

public class ConnectMessage extends Message {

    public ConnectMessage(Bot bot) {
        super(bot, "CONNECT");
    }

    @Override
    public JSONObject toJson() {
        return JSONBuilder.create(this)
                .build();
    }
}
