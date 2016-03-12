package io.sponges.bot.client;

import io.sponges.bot.client.cache.CacheManager;
import io.sponges.bot.client.event.events.internal.ClientInputEvent;
import io.sponges.bot.client.event.framework.EventBus;
import io.sponges.bot.client.internal.ClientImpl;
import io.sponges.bot.client.protocol.msg.ConnectMessage;
import io.sponges.bot.client.protocol.parser.ParserManager;

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
    private final EventBus eventBus;
    private final CacheManager cacheManager;
    private final ParserManager parserManager;

    private ClientImpl client;

    public Bot(String clientId, String host, int port) {
        this.clientId = clientId;
        this.eventBus = new EventBus();
        this.cacheManager = new CacheManager();
        this.parserManager = new ParserManager(this);

        this.client = new ClientImpl(host, port, this);
        this.eventBus.register(ClientInputEvent.class, parserManager::onClientInput);
    }

    public void start() {
        client.start(() -> {
            client.sendMessage(new ConnectMessage(this).toString());
        });
    }

    public String getClientId() {
        return clientId;
    }

    public ClientImpl getClient() {
        return client;
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

    public CacheManager getCacheManager() {
        return cacheManager;
    }
}
