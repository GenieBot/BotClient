package io.sponges.bot.client.protocol.msg;

import io.sponges.bot.client.Bot;
import io.sponges.bot.client.event.events.UpdateChannelDataEvent;
import org.json.JSONObject;

public final class ChannelDataUpdateMessage extends Message {

    private final String networkId;
    private final String channelId;
    private final String userId;
    private final UpdateChannelDataEvent.Detail detail;
    private final String oldValue;
    private final String value;

    public ChannelDataUpdateMessage(Bot bot, String networkId, String channelId, String userId,
                                    UpdateChannelDataEvent.Detail detail, String oldValue, String value) {
        super(bot, "CHANNEL_DATA_UPDATE");
        this.networkId = networkId;
        this.channelId = channelId;
        this.userId = userId;
        this.detail = detail;
        this.oldValue = oldValue;
        this.value = value;
    }

    @Override
    protected JSONObject toJson() {
        return new JSONObject()
                .put("network", networkId)
                .put("channel", channelId)
                .put("user", userId)
                .put("detail", detail.name())
                .putOpt("old", oldValue)
                .put("value", value);
    }
}
