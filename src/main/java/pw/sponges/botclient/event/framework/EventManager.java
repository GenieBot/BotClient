package pw.sponges.botclient.event.framework;

import pw.sponges.botclient.event.*;

import java.util.ArrayList;
import java.util.List;

public class EventManager {

    private List<Listener> listeners;

    public EventManager() {
        this.listeners = new ArrayList<>();
    }

    public void registerListener(Listener listener) {
        this.listeners.add(listener);
    }

    public void handle(Event event) {
        if (event instanceof ConnectEvent) {
            for (Listener l : listeners) l.onConnect((ConnectEvent) event);
        } else if (event instanceof InputEvent) {
            for (Listener l : listeners) l.onInput((InputEvent) event);
        } else if (event instanceof CommandEvent) {
            for (Listener l : listeners) l.onCommand((CommandEvent) event);
        } else if (event instanceof BridgedChatEvent) {
            for (Listener l : listeners) l.onBridgedChat((BridgedChatEvent) event);
        } else if (event instanceof PrefixChangeEvent) {
            for (Listener l : listeners) l.onPrefixChange((PrefixChangeEvent) event);
        } else if (event instanceof JoinRoomEvent) {
            for (Listener l : listeners) l.onJoinRoomRequest((JoinRoomEvent) event);
        }
    }

}
