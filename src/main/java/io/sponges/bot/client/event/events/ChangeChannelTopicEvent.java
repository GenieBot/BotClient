package io.sponges.bot.client.event.events;

import io.sponges.bot.client.cache.Channel;
import io.sponges.bot.client.event.framework.Event;

public final class ChangeChannelTopicEvent extends Event {

    private final String network;
    private final Channel channel;
    private final float time;
    private final String topic;

    public ChangeChannelTopicEvent(String network, Channel channel, float time, String topic) {
        this.network = network;
        this.channel = channel;
        this.time = time;
        this.topic = topic;
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

    public String getTopic() {
        return topic;
    }
}
