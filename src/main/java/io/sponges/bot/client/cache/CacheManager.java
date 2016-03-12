package io.sponges.bot.client.cache;

import java.util.HashMap;
import java.util.Map;

public class CacheManager {

    private final Map<String, NetworkCache> networkCaches = new HashMap<>();

    public Map<String, NetworkCache> getNetworkCaches() {
        return networkCaches;
    }
}
