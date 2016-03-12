package io.sponges.bot.client.cache;

import java.util.HashMap;
import java.util.Map;

public class NetworkCache {

    private final Map<String, Channel> channels = new HashMap<>();
    private final Map<String, User> users = new HashMap<>();

    public Map<String, Channel> getChannels() {
        return channels;
    }

    public Map<String, User> getUsers() {
        return users;
    }
}
