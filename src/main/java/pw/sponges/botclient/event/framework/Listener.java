package pw.sponges.botclient.event.framework;

import pw.sponges.botclient.event.*;

public interface Listener {

    void onConnect(ConnectEvent event);

    default void onInput(InputEvent event) {}

    void onCommand(CommandEvent event);

    void onBridgedChat(BridgedChatEvent event);

    default void onPrefixChange(PrefixChangeEvent event) {}

    void onJoinRoomRequest(JoinRoomEvent event);

}
