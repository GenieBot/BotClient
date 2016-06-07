package io.sponges.bot.client.protocol.msg;

import io.sponges.bot.client.Bot;
import org.json.JSONObject;

public final class ChatMessage extends Message {

    private final String networkId;
    private final String channelId;
    private final String userId;
    private final long time;
    private final String content;

    public ChatMessage(Bot bot, String networkId, String channelId, String userId, long time, String content) {
        super(bot, "CHAT");
        this.networkId = networkId;
        this.channelId = channelId;
        this.userId = userId;
        this.time = time;
        this.content = content;
    }

    @Override
    protected JSONObject toJson() {
        JSONObject message = new JSONObject();
        message.put("time", time);
        message.put("content", content);
        return new JSONObject()
                .put("network", networkId)
                .put("channel", channelId)
                .put("user", userId)
                .put("message", message);
    }
}
