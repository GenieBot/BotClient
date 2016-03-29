package io.sponges.bot.client.protocol.parser;

import io.sponges.bot.client.Bot;
import io.sponges.bot.client.cache.CacheManager;
import io.sponges.bot.client.event.events.internal.ClientInputEvent;
import io.sponges.bot.client.event.framework.EventBus;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ParserManager {

    private final Map<String, MessageParser> parsers = new HashMap<>();

    public ParserManager(Bot bot) {
        EventBus eventBus = bot.getEventBus();
        CacheManager cacheManager = bot.getCacheManager();

        register(
                new CommandResponseParser(eventBus, cacheManager),
                new StopParser(bot),
                new SendRawParser(eventBus, cacheManager),
                new KickUserParser(eventBus, cacheManager),
                new ChangeChannelTopicParser(eventBus, cacheManager)
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
        String type = json.getString("type").toUpperCase();
        long time = json.getLong("time");
        JSONObject content = json.getJSONObject("content");
        if (parsers.containsKey(type)) {
            parsers.get(type).parse(time, content);
        } else {
            System.err.println("Got invalid message type \"" + type + "\"!");
        }
    }

}