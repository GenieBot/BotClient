package io.sponges.bot.client.event.events;

import io.sponges.bot.client.Bot;
import io.sponges.bot.client.event.framework.Event;
import io.sponges.bot.client.protocol.msg.ChannelMessage;

public final class ChannelMessageReceiveEvent extends Event {

    private final String message;
    private final String id;

    public ChannelMessageReceiveEvent(String message, String id) {
        this.message = message;
        this.id = id;
    }

    public void reply(Bot bot, String message) {
        bot.getClient().sendMessage(new ChannelMessage(bot, id, message, null, ChannelMessage.MessageType.RESPONSE)
                .toString());
    }

    public String getMessage() {
        return message;
    }

    public String getId() {
        return id;
    }

}
