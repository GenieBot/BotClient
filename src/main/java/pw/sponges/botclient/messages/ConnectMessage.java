package pw.sponges.botclient.messages;

import org.json.JSONObject;
import pw.sponges.botclient.Bot;
import pw.sponges.botclient.util.JSONBuilder;

public class ConnectMessage extends Message {

    public ConnectMessage(Bot bot) {
        super(bot, "CONNECT");
    }

    @Override
    public JSONObject toJson() {
        return JSONBuilder.create(this)
                .build();
    }
}
