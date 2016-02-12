package io.sponges.bot.client.event;

import io.sponges.bot.client.event.framework.Event;

public class InputEvent extends Event {

    private final String input;

    public InputEvent(String input) {
        this.input = input;
    }

    public String getInput() {
        return input;
    }
}
