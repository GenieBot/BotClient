package pw.sponges.botclient;

import org.json.JSONObject;
import pw.sponges.botclient.event.*;
import pw.sponges.botclient.event.framework.EventManager;
import pw.sponges.botclient.event.framework.Listener;
import pw.sponges.botclient.internal.Client;
import pw.sponges.botclient.messages.Message;
import pw.sponges.botclient.util.Msg;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (c) 2015 Joe Burnard ("SpongyBacon").
 * By viewing this code, you agree to the terms
 * in the enclosed license.txt file.
 */
public class Bot {

    private Client client;
    private final EventManager eventManager;

    private final String clientId;
    private Map<String, String> prefixes;

    public Bot(String clientId) {
        this.clientId = clientId;
        this.prefixes = new HashMap<>();
        this.eventManager = new EventManager();
        this.eventManager.registerListener(new Listener() {
            @Override
            public void onConnect(ConnectEvent event) {}

            @Override
            public void onInput(InputEvent event) {
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
                        try {
                            Msg.warning("STOPPING!");
                            client.stop();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    }

                    case "PREFIX": {
                        eventManager.handle(new PrefixChangeEvent(object.getString("room"), object.getString("prefix")));
                        break;
                    }

                    case "JOIN": {
                        eventManager.handle(new JoinRoomEvent(object.getString("room")));
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
            public void onCommand(CommandEvent event) {}

            @Override
            public void onBridgedChat(BridgedChatEvent event) {}

            @Override
            public void onPrefixChange(PrefixChangeEvent event) {
                prefixes.put(event.getRoom(), event.getPrefix());
            }

            @Override
            public void onJoinRoomRequest(JoinRoomEvent event) {}
        });
    }

    public void start() {
        new Thread(() -> {
            client = new Client(this);
            try {
                client.start();
            } catch (IOException e) {
                if (e.getMessage().equalsIgnoreCase("Connection refused: connect")) {
                    Msg.warning("Connection to server refused. Is the server down?");
                    try {
                        client.stop();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void sendOutput(Message message) {
        client.getOut().println(message.getJSON().toString());
    }

    public String getClientId() {
        return clientId;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public void stop() {
        try {
            client.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*public Map<String, String> getPrefixes() {
        return prefixes;
    }

    public String getPrefix(String room) {
        if (!hasPrefix(room)) {
            sendOutput(new PrefixRequestMessage(this, room));
        }

        return prefixes.get(room);
    }

    public boolean hasPrefix(String room) {
        return prefixes.containsKey(room);
    }*/

}
