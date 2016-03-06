package io.sponges.bot.client.oldmessages;

import io.sponges.bot.client.UserRole;
import org.json.JSONObject;
import io.sponges.bot.client.Bot;
import io.sponges.bot.client.util.JSONBuilder;

public class UserJoinMessage extends Message {

    private final String userId, username, userDisplayName, networkId, roomTopic, roomId;
    private final UserRole role;

    public UserJoinMessage(Bot bot, String channel, String userId, String username, String userDisplayName, String networkId, String roomTopic, String roomId, UserRole role) {
        super(bot, channel, "JOIN");

        this.userId = userId;
        this.username = username;
        this.userDisplayName = userDisplayName;
        this.networkId = networkId;
        this.roomTopic = roomTopic;
        this.roomId = roomId;
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

                .build();
    }
}
