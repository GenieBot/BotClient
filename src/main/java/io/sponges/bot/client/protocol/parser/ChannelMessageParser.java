package io.sponges.bot.client.protocol.parser;

import io.sponges.bot.client.Bot;
import io.sponges.bot.client.event.events.ChannelMessageEvent;
import org.json.JSONObject;

public final class ChannelMessageParser extends MessageParser {

    private final Bot bot;

    protected ChannelMessageParser(Bot bot) {
        super("CHANNEL_MESSAGE");
        this.bot = bot;
    }

    @Override
    public void parse(long time, JSONObject content) {
        String message = content.getString("message");
        String id = content.getString("id");
        bot.getEventBus().post(new ChannelMessageEvent(message, id));
    }
}
