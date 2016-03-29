package io.sponges.bot.client.protocol.msg;

import io.sponges.bot.client.Bot;
import org.json.JSONObject;

public final class ChannelTopicChangeMessage extends Message {

    private final Bot bot;
    private final String networkId;
    private final String channelId;
    private final String userId;
    private final String username;
    private final String displayName;
    private final boolean admin;
    private final boolean op;
    private final String oldTopic;
    private final String newTopic;

    public ChannelTopicChangeMessage(Bot bot, String networkId, String channelId, String userId, String username, String displayName, boolean admin, boolean op, String oldTopic, String newTopic) {
        super(bot, "CHANNEL_TOPIC_CHANGE");
        this.bot = bot;
        this.networkId = networkId;
        this.channelId = channelId;
        this.userId = userId;
        this.username = username;
        this.displayName = displayName;
        this.admin = admin;
        this.op = op;
        this.oldTopic = oldTopic;
        this.newTopic = newTopic;
    }

    @Override
    protected JSONObject toJson() {
        JSONObject channel = new JSONObject();
        channel.put("id", channelId);

        JSONObject user = new JSONObject();
        user.put("id", userId);
        user.putOpt("username", username);
        user.putOpt("display-name", displayName);
        user.put("admin", admin);
        user.put("op", op);

        return new JSONObject()
                .put("network", networkId)
                .put("channel", channel)
                .put("user", user)
                .putOpt("old", oldTopic)
                .put("new", newTopic);
    }
}
