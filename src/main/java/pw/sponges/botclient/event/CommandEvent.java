package pw.sponges.botclient.event;

import pw.sponges.botclient.event.framework.Event;

public class CommandEvent extends Event {

    private final String client, room, user, response;

    public CommandEvent(String client, String room, String user, String response) {
        this.client = client;
        this.room = room;
        this.user = user;
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

    public String getResponse() {
        return response;
    }

}
