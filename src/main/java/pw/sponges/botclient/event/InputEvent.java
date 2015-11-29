package pw.sponges.botclient.event;

import pw.sponges.botclient.event.framework.Event;

public class InputEvent extends Event {

    private final String input;

    public InputEvent(String input) {
        this.input = input;
    }

    public String getInput() {
        return input;
    }
}
