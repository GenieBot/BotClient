package io.sponges.bot.client.event.events;

import io.sponges.bot.client.cache.Channel;
import io.sponges.bot.client.event.framework.Event;

public final class SendRawEvent extends Event {

    private final String network;
    private final Channel channel;
    private final float time;
    private final String message;

    public SendRawEvent(String network, Channel channel, float time, String message) {
        this.network = network;
        this.channel = channel;
        this.time = time;
        this.message = message;
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
}
