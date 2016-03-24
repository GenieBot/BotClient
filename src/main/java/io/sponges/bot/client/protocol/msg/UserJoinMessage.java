package io.sponges.bot.client.protocol.msg;

import io.sponges.bot.client.Bot;
import org.json.JSONObject;

public final class UserJoinMessage extends Message {

    private final Bot bot;
    private final String networkId;
    private final String channelId;

    private final String addedUserId;
    private final String addedUsername;
    private final String addedDisplayName;
    private final boolean isAddedAdmin;
    private final boolean isAddedOp;

    private final String initiatorUserId;
    private final String initiatorUsername;
    private final String initiatorDisplayName;
    private final boolean isInitiatorAdmin;
    private final boolean isInitiatorOp;

    public UserJoinMessage(Bot bot, String networkId, String channelId, String addedUserId, String addedUsername,
                           String addedDisplayName, boolean isAddedAdmin, boolean isAddedOp, String initiatorUserId,
                           String initiatorUsername, String initiatorDisplayName, boolean isInitiatorAdmin,
                           boolean isInitiatorOp) {
        super(bot, "USER_JOIN");
        this.bot = bot;
        this.networkId = networkId;
        this.channelId = channelId;
        this.addedUserId = addedUserId;
        this.addedUsername = addedUsername;
        this.addedDisplayName = addedDisplayName;
        this.isAddedAdmin = isAddedAdmin;
        this.isAddedOp = isAddedOp;
        this.initiatorUserId = initiatorUserId;
        this.initiatorUsername = initiatorUsername;
        this.initiatorDisplayName = initiatorDisplayName;
        this.isInitiatorAdmin = isInitiatorAdmin;
        this.isInitiatorOp = isInitiatorOp;
    }

    @Override
    protected JSONObject toJson() {
        JSONObject channel = null;
        if (channelId != null) {
            channel = new JSONObject().put("id", channelId);
        }

        JSONObject added = new JSONObject();
        added.put("id", addedUserId);
        added.putOpt("username", addedUsername);
        added.putOpt("display-name", addedDisplayName);
        added.put("admin", isAddedAdmin);
        added.put("op", isAddedOp);

        JSONObject initiator = null;
        if (initiatorUserId != null) {
            initiator = new JSONObject();
            initiator.put("id", initiatorUserId);
            initiator.putOpt("username", initiatorUsername);
            initiator.putOpt("display-name", initiatorDisplayName);
            initiator.put("admin", isInitiatorAdmin);
            initiator.put("op", isInitiatorOp);
        }

        return new JSONObject()
                .put("network", networkId)
                .putOpt("channel", channel)
                .put("added", added)
                .putOpt("initiator", initiator);
    }
}
