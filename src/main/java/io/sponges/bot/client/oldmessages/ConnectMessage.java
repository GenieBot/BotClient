package io.sponges.bot.client.oldmessages;

import io.sponges.bot.client.Bot;
import io.sponges.bot.client.util.JSONBuilder;
import org.json.JSONObject;

public class ConnectMessage extends Message {

    private final String localChannel;

    public ConnectMessage(Bot bot, String channel, String localChannel) {
        super(bot, channel, "CONNECT");

        this.localChannel = localChannel;
    }

    @Override
    public JSONObject toJson() {
        return JSONBuilder.create(this)
                .withValue("channel", localChannel)
                .build();
    }
}
