package io.sponges.bot.client.event.events;

import io.sponges.bot.client.cache.Channel;
import io.sponges.bot.client.event.framework.Event;

public final class UpdateChannelDataEvent extends Event {

    private final String network;
    private final Channel channel;
    private final Detail detail;
    private final String value;
    private final float time;

    public UpdateChannelDataEvent(String network, Channel channel, String detail, String value, float time) {
        this.network = network;
        this.channel = channel;
        this.detail = Detail.valueOf(detail.toUpperCase());
        this.value = value;
        this.time = time;
    }

    public String getNetwork() {
        return network;
    }

    public Channel getChannel() {
        return channel;
    }

    public Detail getDetail() {
        return detail;
    }

    public String getValue() {
        return value;
    }

    public float getTime() {
        return time;
    }

    public enum Detail {
        NAME, TOPIC
    }

}
