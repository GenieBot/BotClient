package io.sponges.bot.client;

import io.sponges.bot.client.event.*;
import io.sponges.bot.client.event.framework.EventBus;
import io.sponges.bot.client.internal.Client;
import io.sponges.bot.client.util.Msg;
import org.json.JSONObject;

public class Listener {

    private final Client client;
    private final Bot bot;
    private final EventBus eventBus;

    public Listener(Bot bot, Client client) {
        this.client = client;
        this.bot = bot;
        this.eventBus = bot.getEventBus();
    }

    public void onInput(InputEvent event) {
        if (!event.getInput().contains("{")) {
            Msg.warning("Got non json: " + event.getInput());
            return;
        } else {
            Msg.debug("INPUT> " + event.getInput());
        }

        JSONObject object = new JSONObject(event.getInput());

        String type = object.getString("type");

        switch (type) {
            case "COMMAND": {
                eventBus.post(new CommandEvent(object.getString("client-id"), object.getString("network"), object.getString("room"), object.getString("user"), object.getString("username"), object.getString("response")));
                break;
            }

            case "CHAT": {
                eventBus.post(new BridgedChatEvent(object.getString("client-id"), object.getString("source-room"), object.getString("name"), object.getString("room"), object.getString("userid"), object.getString("username"), object.getString("message")));
                break;
            }

            case "STOP": {
                Msg.warning("STOPPING!");
                client.stop();
                System.exit(500);
                break;
            }

            case "JOIN": {
                eventBus.post(new JoinRoomEvent(object.getString("room")));
                break;
            }

            case "KICK": {
                eventBus.post(new KickRequestEvent(object.getString("room"), object.getString("user")));
                break;
            }

            case "RAW": {
                eventBus.post(new SendRawRequestEvent(object.getString("network"), object.getString("room"), object.getString("message")));
                break;
            }

            default: {
                Msg.warning("Unknown message type! " + type);
                Msg.debug(object.toString());
                break;
            }
        }
    }

}
