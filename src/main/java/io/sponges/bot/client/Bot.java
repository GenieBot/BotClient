package io.sponges.bot.client;

import io.sponges.bot.client.cache.CacheManager;
import io.sponges.bot.client.event.events.internal.ClientInputEvent;
import io.sponges.bot.client.event.events.StopEvent;
import io.sponges.bot.client.event.framework.EventBus;
import io.sponges.bot.client.formatting.MessageFormatter;
import io.sponges.bot.client.internal.ClientImpl;
import io.sponges.bot.client.protocol.msg.ConnectMessage;
import io.sponges.bot.client.protocol.parser.ParserManager;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    private final String defaultPrefix;

    private final EventBus eventBus;
    private final CacheManager cacheManager;
    private final ParserManager parserManager;
    private final ClientImpl client;

    private MessageFormatter messageFormatter = null;

    private static Logger logger = new Logger() {
        private final String format = "[%s - %s] %s\r\n";
        private final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");

        @Override
        public void log(Type type, String message) {
            log(type.name(), message);
        }

        @Override
        public void log(String type, String message) {
            System.out.printf(format, TIME_FORMAT.format(new Date()), type, message);
        }
    };

    public Bot(String clientId, String defaultPrefix, String host, int port) {
        this.clientId = clientId;
        this.defaultPrefix = defaultPrefix;

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

    public String getDefaultPrefix() {
        return defaultPrefix;
    }

    public ClientImpl getClient() {
        return client;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public void stop() {
        client.stop();
        eventBus.post(new StopEvent());
    }

    public Map<String, Object> getSettings() {
        return settings;
    }

    public CacheManager getCacheManager() {
        return cacheManager;
    }

    public ParserManager getParserManager() {
        return parserManager;
    }

    public MessageFormatter getMessageFormatter() {
        return messageFormatter;
    }

    public void setMessageFormatter(MessageFormatter messageFormatter) {
        this.messageFormatter = messageFormatter;
    }

    public static Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        Bot.logger = logger;
    }
}
