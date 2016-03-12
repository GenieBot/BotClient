package io.sponges.bot.client.event.events.internal;

import org.json.JSONObject;

public final class ClientInputEvent extends InternalEvent {

    private final JSONObject jsonObject;

    public ClientInputEvent(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }
}
