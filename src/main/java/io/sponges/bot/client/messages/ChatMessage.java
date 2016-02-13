package io.sponges.bot.client.messages;

import io.sponges.bot.client.UserRole;
import org.json.JSONObject;
import io.sponges.bot.client.Bot;
import io.sponges.bot.client.util.JSONBuilder;

public class ChatMessage extends Message {

    private final String userId, username, userDisplayName, roomId, roomTopic, networkId, message;
    private final UserRole role;

    public ChatMessage(Bot bot, String channel, String userId, String username, String userDisplayName, String roomId, String roomTopic, String networkId, String message, UserRole role) {
        super(bot, channel, "CHAT");

        this.userId = userId;
        this.username = username;
        this.userDisplayName = userDisplayName;
        this.roomId = roomId;
        this.roomTopic = roomTopic;
        this.networkId = networkId;
        this.message = message;
        this.role = role;
    }

    @Override
    public JSONObject toJson() {
        return JSONBuilder.create(this)
                .withNewObject("user")
                    .withValue("id", userId)
                    .withValue("username", username)
                    .withValue("display-name", userDisplayName)
                    .withValue("role", role.toString())
                    .build()

                .withNewObject("network")
                    .withValue("id", networkId)
                    .build()

                .withNewObject("room")
                    .withValue("id", roomId)
                    .withValue("topic", roomTopic)
                    .build()

                .withValue("message", message)
                .build();
    }
}
