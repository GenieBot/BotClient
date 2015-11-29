package pw.sponges.botclient.messages;

import org.json.JSONObject;
import pw.sponges.botclient.Bot;
import pw.sponges.botclient.util.JSONBuilder;

public class PrefixRequestMessage extends Message {

    private final String room;

    public PrefixRequestMessage(Bot bot, String room) {
        super(bot, "PREFIX");
        this.room = room;
    }

    @Override
    public JSONObject toJson() {
        return JSONBuilder.create(getBot())
                .setType(this.getType())
                .withValue("room", room)
                .build();
    }
}
