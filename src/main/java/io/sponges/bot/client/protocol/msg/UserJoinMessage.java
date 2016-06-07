package io.sponges.bot.client.protocol.msg;

import io.sponges.bot.client.Bot;
import org.json.JSONObject;

public final class UserJoinMessage extends Message {

    private final String networkId;
    private final String channelId;
    private final String addedUserId;
    private final String initiatorUserId;

    public UserJoinMessage(Bot bot, String networkId, String channelId, String addedUserId, String initiatorUserId) {
        super(bot, "USER_JOIN");
        this.networkId = networkId;
        this.channelId = channelId;
        this.addedUserId = addedUserId;
        this.initiatorUserId = initiatorUserId;
    }

    @Override
    protected JSONObject toJson() {
        return new JSONObject()
                .put("network", networkId)
                .putOpt("channel", channelId)
                .put("added", addedUserId)
                .putOpt("initiator", initiatorUserId);
    }
}
