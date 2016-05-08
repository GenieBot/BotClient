package io.sponges.bot.client.protocol.msg;

import io.sponges.bot.client.Bot;
import io.sponges.bot.client.protocol.manager.ChannelMessageManager;
import org.json.JSONObject;

import java.util.function.Consumer;

public class ChannelMessage extends Message {

    public static final ChannelMessageManager MESSAGE_MANAGER = new ChannelMessageManager();

    private final String id;
    private final MessageType type;
    private final String message;

    public ChannelMessage(Bot bot, String id, String message, Consumer<String> callback, MessageType type) {
        super(bot, "CHANNEL_MESSAGE");
        this.id = id;
        this.type = type;
        this.message = message;
        if (type == MessageType.REQUEST && callback != null) MESSAGE_MANAGER.getMessages().put(id, callback);

    }

    @Override
    protected JSONObject toJson() {
        return new JSONObject()
                .put("message", message)
                .put("type", type.toString())
                .put("id", id);
    }

    public enum MessageType {
        REQUEST, RESPONSE;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
}
