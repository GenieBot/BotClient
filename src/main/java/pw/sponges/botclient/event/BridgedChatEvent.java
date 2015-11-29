package pw.sponges.botclient.event;

import pw.sponges.botclient.event.framework.Event;

public class BridgedChatEvent extends Event {

    private final String client, sourceRoom, sourceName, room, user, message;

    public BridgedChatEvent(String client, String sourceRoom, String sourceName, String room, String user, String message) {
        this.client = client;
        this.sourceRoom = sourceRoom;
        this.sourceName = sourceName;
        this.room = room;
        this.user = user;
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

    public String getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }
}
