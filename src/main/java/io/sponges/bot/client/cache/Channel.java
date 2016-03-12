package io.sponges.bot.client.cache;

public class Channel {

    private final String id;
    private final boolean isPrivate;

    public Channel(String id, boolean isPrivate) {
        this.id = id;
        this.isPrivate = isPrivate;
    }

    public String getId() {
        return id;
    }

    public boolean isPrivate() {
        return isPrivate;
    }
}
