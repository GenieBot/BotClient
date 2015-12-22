package pw.sponges.botclient.event.framework;

import pw.sponges.botclient.event.*;

import java.util.ArrayList;
import java.util.List;

public class EventManager {

    private final List<BotListener> botListeners;
    private InternalListener internalListener = null;

    public EventManager() {
        this.botListeners = new ArrayList<>();
    }

    public void registerBotListener(BotListener listener) {
        this.botListeners.add(listener);
    }

    public void registerInternalListener(InternalListener internalListener) {
        this.internalListener = internalListener;
    }

    public void handle(Event event) {
        // bot events
        if (event instanceof ConnectEvent) {
            for (BotListener l : botListeners) {
                l.onConnect((ConnectEvent) event);
            }
        } else if (event instanceof CommandEvent) {
            for (BotListener l : botListeners) {
                l.onCommand((CommandEvent) event);
            }
        } else if (event instanceof BridgedChatEvent) {
            for (BotListener l : botListeners) {
                l.onBridgedChat((BridgedChatEvent) event);
            }
        } else if (event instanceof JoinRoomEvent) {
            for (BotListener l : botListeners) {
                l.onJoinRoomRequest((JoinRoomEvent) event);
            }
        } else if (event instanceof KickRequestEvent) {
            for (BotListener l : botListeners) {
                l.onKickRequest((KickRequestEvent) event);
            }
        } else if (event instanceof SendRawRequestEvent) {
            for (BotListener l : botListeners) {
                l.onSendRawRequest((SendRawRequestEvent) event);
            }
        }

        // internal events
        else if (event instanceof InputEvent) internalListener.onInput((InputEvent) event);
        else if (event instanceof SettingUpdateEvent) internalListener.onSettingUpdate((SettingUpdateEvent) event);
    }

}
