package io.sponges.bot.client.event.events;

import io.sponges.bot.client.Bot;
import io.sponges.bot.client.event.framework.Event;
import io.sponges.bot.client.protocol.msg.ResourceResponseMessage;

import java.util.Map;

public final class ResourceRequestEvent extends Event {

    private final ResourceType type;
    private final String requestId;
    private final String networkId;
    private final String channelId;
    private final String userId;

    public ResourceRequestEvent(String requestId, String networkId) {
        this.requestId = requestId;
        this.type = ResourceType.NETWORK;
        this.networkId = networkId;
        this.channelId = null;
        this.userId = null;
    }

    public ResourceRequestEvent(ResourceType type, String requestId, String networkId, String id) {
        this.type = type;
        this.requestId = requestId;
        this.networkId = networkId;
        switch (type) {
            case CHANNEL:
                this.channelId = id;
                this.userId = null;
                break;
            case USER:
                this.channelId = null;
                this.userId = id;
                break;
            default:
                this.channelId = null;
                this.userId = null;
        }
    }

    public void reply(Bot bot, Map<String, String> parameters) {
        bot.getClient().sendMessage(new ResourceResponseMessage(bot, requestId, type, networkId, parameters).toString());
    }

    public void replyInvalid(Bot bot) {
        bot.getClient().sendMessage(new ResourceResponseMessage(bot, requestId, type).toString());
    }

    public ResourceType getType() {
        return type;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getNetworkId() {
        return networkId;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getUserId() {
        return userId;
    }

    public enum ResourceType {

        NETWORK, CHANNEL, USER

    }

}
