package io.sponges.bot.client.event;

import io.sponges.bot.client.event.framework.Event;

public class BridgedChatEvent extends Event {

    private final String client, sourceRoom, sourceName, room, userId, username, message;

    public BridgedChatEvent(String client, String sourceRoom, String sourceName, String room, String userId, String username, String message) {
        this.client = client;
        this.sourceRoom = sourceRoom;
        this.sourceName = sourceName;
        this.room = room;
        this.userId = userId;
        this.username = username;
        this.message = message;
    }

    public String getClient() {
        return client;
    }

    public String getSourceRoom() {
        return sourceRoom;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getRoom() {
        return room;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }
}
