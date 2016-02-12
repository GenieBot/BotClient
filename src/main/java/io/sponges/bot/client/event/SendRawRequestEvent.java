package io.sponges.bot.client.event;

import io.sponges.bot.client.event.framework.Event;

public class SendRawRequestEvent extends Event {

    private final String room, message;

    public SendRawRequestEvent(String room, String message) {
        this.room = room;
        this.message = message;
    }

    public String getRoom() {
        return room;
    }

    public String getMessage() {
        return message;
    }
}
