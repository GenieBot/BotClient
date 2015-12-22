package pw.sponges.botclient.event;

import pw.sponges.botclient.event.framework.Event;

public class KickRequestEvent extends Event {

    private final String room, user;

    public KickRequestEvent(String room, String user) {
        this.room = room;
        this.user = user;
    }

    public String getRoom() {
        return room;
    }

    public String getUser() {
        return user;
    }

}
