package pw.sponges.botclient.event;

import pw.sponges.botclient.event.framework.Event;

public class JoinRoomEvent extends Event {

    private final String room;

    public JoinRoomEvent(String room) {
        this.room = room;
    }

    public String getRoom() {
        return room;
    }
}
