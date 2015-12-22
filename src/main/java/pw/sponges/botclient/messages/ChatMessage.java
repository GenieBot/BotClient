package pw.sponges.botclient.messages;

import org.json.JSONObject;
import pw.sponges.botclient.Bot;
import pw.sponges.botclient.UserRole;
import pw.sponges.botclient.util.JSONBuilder;

public class ChatMessage extends Message {

    private final String userId, username, room, roomName, message;
    private final UserRole role;

    public ChatMessage(Bot bot, String userId, String username, String room, String roomName, String message, UserRole role) {
        super(bot, "CHAT");
        this.userId = userId;
        this.username = username;
        this.room = room;
        this.roomName = roomName;
        this.message = message;
        this.role = role;
    }

    @Override
    public JSONObject toJson() {
        return JSONBuilder.create(this)
                .withValue("userid", userId)
                .withValue("username", username)
                .withValue("room", room)
                .withValue("name", roomName)
                .withValue("message", message)
                .withValue("role", role.toString())
                .build();
    }
}
