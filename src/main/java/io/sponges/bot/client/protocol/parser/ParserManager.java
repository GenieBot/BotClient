package io.sponges.bot.client.protocol.parser;

import io.sponges.bot.client.Bot;
import io.sponges.bot.client.Logger;
import io.sponges.bot.client.cache.CacheManager;
import io.sponges.bot.client.event.events.internal.ClientInputEvent;
import io.sponges.bot.client.event.framework.EventBus;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class ParserManager {

    private final Map<String, MessageParser> parsers = new HashMap<>();
    private final Map<String, Consumer<JSONObject>> callbacks = new ConcurrentHashMap<>();

    public ParserManager(Bot bot) {
        EventBus eventBus = bot.getEventBus();
        CacheManager cacheManager = bot.getCacheManager();

        register(
                new CommandResponseParser(bot, eventBus, cacheManager),
                new StopParser(bot),
                new SendRawParser(bot, eventBus, cacheManager),
                new KickUserParser(eventBus, cacheManager),
                new UpdateChannelDataParser(eventBus, cacheManager),
                new ResourceRequestParser(eventBus)
        );
    }

    private void register(MessageParser... parsers) {
        for (MessageParser parser : parsers) {
            register(parser);
        }
    }

    private void register(MessageParser parser) {
        parsers.put(parser.getType(), parser);
    }

    public void onClientInput(ClientInputEvent event) {
        JSONObject json = event.getJsonObject();
        Bot.getLogger().log(Logger.Type.DEBUG, json.toString());
        String type = json.getString("type").toUpperCase();
        long time = json.getLong("time");
        JSONObject content = json.getJSONObject("content");
        if (!json.isNull("id")) {
            String messageId = json.getString("id");
            if (callbacks.containsKey(messageId)) {
                getCallback(messageId).accept(content);
            }
        }
        if (parsers.containsKey(type)) {
            parsers.get(type).parse(time, content);
        } else {
            System.err.println("Got invalid message type \"" + type + "\"!");
        }
    }

    public void registerCallback(String messageId, Consumer<JSONObject> callback) {
        callbacks.put(messageId, callback);
    }

    public Consumer<JSONObject> getCallback(String messageId) {
        return callbacks.get(messageId);
    }

}