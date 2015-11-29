package pw.sponges.botclient.event;

import pw.sponges.botclient.event.framework.Event;

public class PrefixChangeEvent extends Event {

    private final String room, prefix;

    public PrefixChangeEvent(String room, String prefix) {
        this.room = room;
        this.prefix = prefix;
    }

    public String getRoom() {
        return room;
    }

    public String getPrefix() {
        return prefix;
    }

}
