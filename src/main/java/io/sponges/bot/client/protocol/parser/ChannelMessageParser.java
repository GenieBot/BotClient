package io.sponges.bot.client.protocol.parser;

import io.sponges.bot.client.Bot;
import io.sponges.bot.client.Logger;
import io.sponges.bot.client.event.events.ChannelMessageReceiveEvent;
import io.sponges.bot.client.protocol.manager.ChannelMessageManager;
import io.sponges.bot.client.protocol.msg.ChannelMessage;
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
        ChannelMessage.MessageType type = ChannelMessage.MessageType.valueOf(content.getString("type").toUpperCase());
        if (type == ChannelMessage.MessageType.RESPONSE) {
            ChannelMessageManager manager = ChannelMessage.MESSAGE_MANAGER;
            if (!manager.getMessages().containsKey(id)) {
                return;
            }
            manager.getMessages().get(id).accept(message);
        } else {
            Bot.getLogger().log(Logger.Type.DEBUG, "got channel message id=" + id + "message=" + message);
            bot.getEventBus().post(new ChannelMessageReceiveEvent(message, id));
        }
    }
}
