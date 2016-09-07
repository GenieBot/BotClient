package io.sponges.bot.client.event.events;

import io.sponges.bot.client.cache.Channel;
import io.sponges.bot.client.event.framework.Event;

public final class SendRawEvent extends Event {

    private final String network;
    private final Channel channel;
    private final float time;
    private final String message;
    private final boolean formatted;

    public SendRawEvent(String network, Channel channel, float time, String message, boolean formatted) {
        this.network = network;
        this.channel = channel;
        this.time = time;
        this.message = message;
        this.formatted = formatted;
    }

    public String getNetwork() {
        return network;
    }

    public Channel getChannel() {
        return channel;
    }

    public float getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }

    public boolean isFormatted() {
        return formatted;
    }
}
