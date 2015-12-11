package pw.sponges.botclient;

import org.json.JSONObject;
import pw.sponges.botclient.event.*;
import pw.sponges.botclient.event.framework.EventManager;
import pw.sponges.botclient.event.framework.InternalListener;
import pw.sponges.botclient.newinternal.Client;
import pw.sponges.botclient.util.Msg;

public class Listener implements InternalListener {

    private final Client client;
    private final Bot bot;
    private final EventManager eventManager;

    public Listener(Bot bot, Client client) {
        this.client = client;
        this.bot = bot;
        this.eventManager = bot.getEventManager();

    }

    @Override
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
                eventManager.handle(new CommandEvent(object.getString("client-id"),
                        object.getString("room"),
                        object.getString("user"),
                        object.getString("response")));
                break;
            }

            case "CHAT": {
                eventManager.handle(new BridgedChatEvent(object.getString("client-id"), object.getString("source-room"), object.getString("name"), object.getString("room"), object.getString("user"), object.getString("message")));
                break;
            }

            case "STOP": {
                Msg.warning("STOPPING!");
                try {
                    client.stop();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            }

            case "JOIN": {
                eventManager.handle(new JoinRoomEvent(object.getString("room")));
                break;
            }

            case "SETTING": {
                eventManager.handle(new SettingUpdateEvent(object.getString("room"), object.getString("setting"), object.get("value")));
                break;
            }

            default: {
                Msg.warning("Unknown message type! " + type);
                Msg.debug(object.toString());
                break;
            }
        }
    }

    @Override
    public void onSettingUpdate(SettingUpdateEvent event) {
        Msg.debug("Setting update! " + event.getSetting() + ": " + event.getValue().toString());
        // TODO call bot events
        bot.getSettings().put(event.getSetting(), event.getValue());
    }

}
