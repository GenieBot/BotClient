package io.sponges.bot.client.event;

import io.sponges.bot.client.event.framework.Event;

public class CommandEvent extends Event {

    private final String client, room, user, username, response;

    public CommandEvent(String client, String room, String user, String username, String response) {
        this.client = client;
        this.room = room;
        this.user = user;
        this.username = username;
        this.response = response;
    }

    public String getClient() {
        return client;
    }

    public String getRoom() {
        return room;
    }

    public String getUser() {
        return user;
    }

    public String getUsername() {
        return username;
    }

    public String getResponse() {
        return response;
    }
}
