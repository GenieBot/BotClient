package io.sponges.bot.client.protocol.parser;

import io.sponges.bot.client.cache.CacheManager;
import io.sponges.bot.client.cache.NetworkCache;
import io.sponges.bot.client.cache.User;
import io.sponges.bot.client.event.events.KickUserEvent;
import io.sponges.bot.client.event.framework.EventBus;
import org.json.JSONObject;

public final class KickUserParser extends MessageParser {

    private final EventBus eventBus;
    private final CacheManager cacheManager;

    protected KickUserParser(EventBus eventBus, CacheManager cacheManager) {
        super("KICK_USER");
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

        User user;
        {
            JSONObject json = content.getJSONObject("user");
            String id = json.getString("id");
            if (networkCache.getUsers().containsKey(id)) {
                user = networkCache.getUsers().get(id);
            } else {
                user = new User(id);
                if (!json.isNull("username")) {
                    user.setUsername(json.getString("username"));
                }
                if (!json.isNull("display-name")) {
                    user.setDisplayName(json.getString("display-name"));
                }
                networkCache.getUsers().put(id, user);
            }
        }

        KickUserEvent event = new KickUserEvent(network, user, time);
        eventBus.post(event);
    }
}
