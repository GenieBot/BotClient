package pw.sponges.botclient.messages;

import org.json.JSONObject;
import pw.sponges.botclient.Bot;
import pw.sponges.botclient.util.JSONBuilder;

public class ChatMessage extends Message {

    private final String user, room, roomName, message;

    public ChatMessage(Bot bot, String user, String room, String roomName, String message) {
        super(bot, "CHAT");
        this.user = user;
        this.room = room;
        this.roomName = roomName;
        this.message = message;
    }

    @Override
    public JSONObject toJson() {
        return JSONBuilder.create(getBot())
                .setType(this.getType())
                .withValue("user", user)
                .withValue("room", room)
                .withValue("name", roomName)
                .withValue("message", message)
                .build();
    }
}
