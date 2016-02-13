package io.sponges.bot.client.internal;

import io.sponges.bot.client.event.InputEvent;
import io.sponges.bot.client.event.framework.EventBus;
import io.sponges.bot.client.messages.ConnectMessage;
import io.sponges.bot.client.util.Msg;
import redis.clients.jedis.JedisPubSub;

public class ClientSubscriber extends JedisPubSub {

    private final ClientImpl client;
    private final EventBus eventBus;

    public ClientSubscriber(ClientImpl client) {
        this.client = client;
        this.eventBus = client.getBot().getEventBus();
    }

    @Override
    public void onMessage(String channel, String message) {
        boolean found = false;
        for (String c : client.getChannels()) {
            if (c.equals(channel)) {
                found = true;
                break;
            }
        }
        if (!found) return;
        eventBus.post(new InputEvent(message));
    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        Msg.log("[Jedis] Subscribed to " + channel + "!");

        client.publish(new ConnectMessage(client.getBot(), "server", client.getChannels()[0]));
    }

    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
        Msg.log("[Jedis] Unsubscribed from " + channel + "!");
    }
}
