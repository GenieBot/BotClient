package io.sponges.bot.client.event.events;

import io.sponges.bot.client.cache.User;
import io.sponges.bot.client.event.framework.Event;

public final class KickUserEvent extends Event {

    private final String network;
    private final User user;
    private final float time;

    public KickUserEvent(String network, User user, float time) {
        this.network = network;
        this.user = user;
        this.time = time;
    }

    public String getNetwork() {
        return network;
    }

    public User getUser() {
        return user;
    }

    public float getTime() {
        return time;
    }
}
