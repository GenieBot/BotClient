import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import org.apache.commons.lang.StringUtils;
import pw.sponges.botclient.Bot;
import pw.sponges.botclient.UserRole;
import pw.sponges.botclient.event.*;
import pw.sponges.botclient.event.framework.BotListener;
import pw.sponges.botclient.messages.ChatMessage;
import pw.sponges.botclient.util.Msg;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Slack {

    public static final String ID_PREFIX = "slack-";

    private final Bot bot;
    private final Map<String, SlackSession> sessions;

    public Slack() {
        this.bot = new Bot("slack");
        this.bot.start();

        this.sessions = new HashMap<>();
        this.sessions.put("spongybot", SlackSessionFactory.createWebSocketSlackSession("xoxb-17182382598-eB9x1WxW7ChKcYMdcjp918h5"));
        this.sessions.put("codelanx", SlackSessionFactory.createWebSocketSlackSession("xoxb-17179772693-mmvj80apRW5dPMJo6rnJtll9"));
        this.sessions.put("ptp", SlackSessionFactory.createWebSocketSlackSession("xoxb-17193014242-rIUwdkDN6lx1hUdqxyn04xoi"));

        for (SlackSession session : sessions.values()) {
            try {
                session.connect();
            } catch (IOException e) {
                e.printStackTrace();
                continue; // stop trying to use this session
            }

            session.addMessagePostedListener(this::onMessage);
        }

        this.bot.getEventManager().registerBotListener(new BotListener() {
            @Override
            public void onConnect(ConnectEvent event) {
                Msg.log("Connected");
            }

            @Override
            public void onCommand(CommandEvent event) {
                SearchResultSet results = getChannelById(toLongId(event.getRoom()));
                SlackChannel channel = results.getChannel();
                results.getSession().sendMessageOverWebSocket(channel, String.format("@%s, %s", event.getUsername(), decapitalize(event.getResponse())), null);
            }

            @Override
            public void onBridgedChat(BridgedChatEvent event) {
                SearchResultSet results = getChannelById(toLongId(event.getRoom()));
                SlackChannel channel = results.getChannel();
                results.getSession().sendMessageOverWebSocket(channel, String.format("[%s] %s: %s", StringUtils.abbreviate(event.getSourceName(), 10), event.getUsername(), event.getMessage()), null);
            }

            @Override
            public void onJoinRoomRequest(JoinRoomEvent event) {
                // TODO room joining
            }

            @Override
            public void onKickRequest(KickRequestEvent event) {
                // TODO user kicking
            }

            @Override
            public void onSendRawRequest(SendRawRequestEvent event) {
                SearchResultSet results = getChannelById(toLongId(event.getRoom()));
                SlackChannel channel = results.getChannel();
                results.getSession().sendMessageOverWebSocket(channel, event.getMessage(), null);
            }
        });
    }

    public static void main(String[] args) {
        new Slack();
    }

    /**
     * Method to convert discord long id to a short id for easy storing
     * @param longId the long id to convert
     * @return short id
     */
    public static String toShortId(String longId) {
        return ID_PREFIX + longId;
    }

    /**
     * Method to convert discord short id to a long id for using with jDiscord
     * @param shortId the short id to convert to long id
     * @return long id
     */
    public static String toLongId(String shortId) {
        return shortId.replace(ID_PREFIX, "");
    }

    public static String decapitalize(String string) {
        if (string == null || string.length() == 0) {
            return string;
        }
        char c[] = string.toCharArray();
        c[0] = Character.toLowerCase(c[0]);
        return new String(c);
    }

    private void onMessage(SlackMessagePosted event, SlackSession slackSession) {
        String content = event.getMessageContent();
        String room = toShortId(event.getChannel().getId());
        String user = event.getSender().getId();
        String username = event.getSender().getUserName();
        String displayName = event.getSender().getRealName();
        String topic = event.getChannel().getName();

        String networkId = null;
        for (String key : sessions.keySet()) {
            if (sessions.get(key).equals(slackSession)) {
                networkId = key;
                break;
            }
        }

        Msg.log(String.format("[%s - %s] %s: %s", topic, room, username, content));

        if (event.getSender().isBot() && event.getSender().getUserName().equals("bot")) {
            return;
        }

        UserRole role;

        if (event.getSender().isAdmin()) {
            role = UserRole.ADMIN;
        } else {
            role = UserRole.USER;
        }

        bot.sendOutput(new ChatMessage(bot, user, username, displayName, room, topic, networkId, content, UserRole.ADMIN));
    }

    private SearchResultSet getChannelById(String id) {
        for (SlackSession session : sessions.values()) {
            SlackChannel toReturn = session.findChannelById(id);
            if (toReturn != null) return new SearchResultSet(session, toReturn);
        }

        return null;
    }

} class SearchResultSet {

    private final SlackSession session;
    private final SlackChannel channel;

    SearchResultSet(SlackSession session, SlackChannel channel) {
        this.session = session;
        this.channel = channel;
    }

    public SlackSession getSession() {
        return session;
    }

    public SlackChannel getChannel() {
        return channel;
    }
}
