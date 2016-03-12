package io.sponges.bot.client.protocol.msg;

import io.sponges.bot.client.Bot;
import org.json.JSONObject;

public final class ChatMessage extends Message {

    private final Bot bot;
    private final String networkId;
    private final String channelId;
    private final boolean isPrivate;
    private final String userId;
    private final String username;
    private final String displayName;
    private final long time;
    private final String content;

    public ChatMessage(Bot bot, String networkId, String channelId, boolean isPrivate, String userId, String username, String displayName, long time, String content) {
        super(bot, "CHAT");
        this.bot = bot;
        this.networkId = networkId;
        this.channelId = channelId;
        this.isPrivate = isPrivate;
        this.userId = userId;
        this.username = username;
        this.displayName = displayName;
        this.time = time;
        this.content = content;
    }

    @Override
    protected JSONObject toJson() {
        JSONObject channel = new JSONObject();
        channel.put("id", channelId);
        channel.put("private", isPrivate);

        JSONObject user = new JSONObject();
        user.put("id", userId);
        user.putOpt("username", username);
        user.putOpt("display-name", displayName);

        JSONObject message = new JSONObject();
        message.put("time", time);
        message.put("content", content);

        return new JSONObject()
                .put("network", networkId)
                .put("channel", channel)
                .put("user", user)
                .put("message", message);
    }
}
