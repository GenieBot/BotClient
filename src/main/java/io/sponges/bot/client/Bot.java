package io.sponges.bot.client;

import io.sponges.bot.client.event.InputEvent;
import io.sponges.bot.client.event.framework.EventBus;
import io.sponges.bot.client.internal.Client;
import io.sponges.bot.client.internal.ClientImpl;
import io.sponges.bot.client.messages.Message;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (c) 2015 Joe Burnard ("SpongyBacon").
 * By viewing this code, you agree to the terms
 * in the enclosed license.txt file.
 */
public class Bot {

    private final Map<String, Object> settings = new HashMap<>();

    private final String clientId;
    private final String[] channels;
    private final EventBus eventBus;

    private Client client;

    public Bot(String clientId, String[] channels, String host) {
        this.clientId = clientId;
        this.channels = channels;
        this.eventBus = new EventBus();
        this.client = new ClientImpl(this, channels, host);

        Listener listener = new Listener(this, client);
        this.eventBus.register(InputEvent.class, listener::onInput);
    }

    public Bot(String clientId, String[] channels, String host, int port) {
        this.clientId = clientId;
        this.channels = channels;
        this.eventBus = new EventBus();
        this.client = new ClientImpl(this, channels, host, port);

        Listener listener = new Listener(this, client);
        this.eventBus.register(InputEvent.class, listener::onInput);
    }

    public void start() {
        new Thread(client::start).start();
    }

    public void publish(Message message) {
        client.publish(message);
    }

    public String getClientId() {
        return clientId;
    }

    public String[] getChannels() {
        return channels;
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
        client.stop();
    }

    public Map<String, Object> getSettings() {
        return settings;
    }
}
