package io.sponges.bot.client;

import io.sponges.bot.client.event.framework.EventBus;
import io.sponges.bot.client.internal.Client;
import io.sponges.bot.client.internal.impl.ClientImpl;
import io.sponges.bot.client.messages.Message;
import io.sponges.bot.client.util.Msg;

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
    private final EventBus eventBus;

    private final String clientId;
    private Map<String, Object> settings;

    public Bot(String clientId) {
        this.clientId = clientId;
        this.settings = new HashMap<>();
        this.client = new ClientImpl(this);
        this.eventBus = new EventBus();
    }

    public void start() {
        new Thread(() -> {
            try {
                client.start();
            } catch (IOException e) {
                if (e.getMessage().equalsIgnoreCase("Connection refused: connect")) {
                    Msg.warning("Connection to server refused. Is the server down?");
                    try {
                        client.stop();
                        System.exit(-1);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    e.printStackTrace();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void sendOutput(Message message) {
        client.write(message.toString());
    }

    public String getClientId() {
        return clientId;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public void stop() {
        try {
            client.stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Object> getSettings() {
        return settings;
    }
}
