package io.sponges.bot.client.protocol.msg;

import org.json.JSONObject;

public class PrivateChatMessage extends Message {

    private final String networkId;
    private final String channelId;
    private final String userId;
    private final String username;
    private final String displayName;
    private final long time;
    private final String content;

    protected PrivateChatMessage(String networkId, String channelId, String userId, String username, String displayName, long time, String content) {
        super("PRIVATE_CHAT");
        this.networkId = networkId;
        this.channelId = channelId;
        this.userId = userId;
        this.username = username;
        this.displayName = displayName;
        this.time = time;
        this.content = content;
    }

    @Override
    protected JSONObject toJson() {
        JSONObject user = new JSONObject();
        user.put("id", userId);
        user.putOpt("username", username);
        user.putOpt("display-name", displayName);

        JSONObject message = new JSONObject();
        message.put("time", time);
        message.put("content", content);

        return new JSONObject()
                .put("network", networkId)
                .put("channel", channelId)
                .put("user", user)
                .put("message", message);
    }
}
