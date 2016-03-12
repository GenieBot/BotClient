package io.sponges.bot.client.event.events;

import io.sponges.bot.client.cache.Channel;
import io.sponges.bot.client.cache.User;
import io.sponges.bot.client.event.framework.Event;

public final class CommandResponseEvent extends Event {

    private final String network;
    private final Channel channel;
    private final User user;
    private final float time;
    private final String message;

    public CommandResponseEvent(String network, Channel channel, User user, float time, String message) {
        this.network = network;
        this.channel = channel;
        this.user = user;
        this.time = time;
        this.message = message;
    }

    public String getNetwork() {
        return network;
    }

    public Channel getChannel() {
        return channel;
    }

    public User getUser() {
        return user;
    }

    public float getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }
}
