package io.sponges.bot.client.protocol.parser;

import io.sponges.bot.client.cache.CacheManager;
import io.sponges.bot.client.cache.Channel;
import io.sponges.bot.client.cache.NetworkCache;
import io.sponges.bot.client.event.events.ChangeChannelTopicEvent;
import io.sponges.bot.client.event.framework.EventBus;
import org.json.JSONObject;

public final class ChangeChannelTopicParser extends MessageParser {

    private final EventBus eventBus;
    private final CacheManager cacheManager;

    protected ChangeChannelTopicParser(EventBus eventBus, CacheManager cacheManager) {
        super("CHANGE_CHANNEL_TOPIC");
        this.eventBus = eventBus;
        this.cacheManager = cacheManager;
    }

    @Override
    public void parse(long time, JSONObject content) {
        String network = content.getString("network");

        NetworkCache networkCache;
        if (cacheManager.getNetworkCaches().containsKey(network)) {
            networkCache = cacheManager.getNetworkCaches().get(network);
        } else {
            networkCache = new NetworkCache();
            cacheManager.getNetworkCaches().put(network, networkCache);
        }

        Channel channel;
        {
            JSONObject json = content.getJSONObject("channel");
            String id = json.getString("id");
            if (networkCache.getChannels().containsKey(id)) {
                channel = networkCache.getChannels().get(id);
            } else {
                channel = new Channel(id, false);
                networkCache.getChannels().put(id, channel);
            }
        }

        String topic = content.getString("topic");
        ChangeChannelTopicEvent event = new ChangeChannelTopicEvent(network, channel, time, topic);
        eventBus.post(event);
    }
}
