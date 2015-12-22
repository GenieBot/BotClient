package pw.sponges.botclient.messages;

import org.json.JSONObject;
import pw.sponges.botclient.Bot;
import pw.sponges.botclient.util.JSONBuilder;

public class UserJoinMessage extends Message {

    private final String room, user;

    public UserJoinMessage(Bot bot, String room, String user) {
        super(bot, "JOIN");

        this.room = room;
        this.user = user;
    }

    @Override
    public JSONObject toJson() {
        return JSONBuilder.create(this)
                .withValue("room", room)
                .withValue("user", user)
                .build();
    }
}
