package pw.sponges.botclient.event.framework;

import pw.sponges.botclient.event.*;

public interface BotListener {

    void onConnect(ConnectEvent event);

    void onCommand(CommandEvent event);

    void onBridgedChat(BridgedChatEvent event);

    void onJoinRoomRequest(JoinRoomEvent event);

    void onKickRequest(KickRequestEvent event);

    void onSendRawRequest(SendRawRequestEvent event);

}
