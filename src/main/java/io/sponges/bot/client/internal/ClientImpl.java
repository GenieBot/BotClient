package io.sponges.bot.client.internal;

import io.sponges.bot.client.Bot;
import io.sponges.bot.client.messages.Message;
import redis.clients.jedis.Jedis;

import java.util.Optional;

public class ClientImpl implements Client {

    private final Bot bot;
    private final String[] channels;

    private final String host;
    private int port = -1;

    private final ClientSubscriber clientSubscriber;

    private Jedis jedisSubscriber;
    private Jedis jedisPublisher;

    public ClientImpl(Bot bot, String[] channels, String host) {
        this.bot = bot;
        this.channels = channels;
        this.host = host;

        this.clientSubscriber = new ClientSubscriber(this);
        setupJedis(host, Optional.<Integer>empty());
    }

    public ClientImpl(Bot bot, String[] channels, String host, int port) {
        this.bot = bot;
        this.channels = channels;
        this.host = host;
        this.port = port;

        this.clientSubscriber = new ClientSubscriber(this);
        setupJedis(host, Optional.of(port));
    }

    private void setupJedis(String host, Optional<Integer> port) {
        if (port.isPresent()) {
            this.jedisSubscriber = new Jedis(host, port.get());
            this.jedisPublisher = new Jedis(host, port.get());
        } else {
            this.jedisSubscriber = new Jedis(host);
            this.jedisPublisher = new Jedis(host);
        }
    }

    @Override
    public void start() {
        new Thread(() -> {
            jedisSubscriber.subscribe(clientSubscriber, channels);
        }).start();
    }

    @Override
    public void stop() {
        jedisSubscriber.close();
        jedisPublisher.close();
    }

    @Override
    public void publish(Message message) {
        publish(message.getChannel(), message.toString());
    }

    @Override
    public void publish(String channel, String message) {
        new Thread(() -> {
            jedisPublisher.publish(channel, message);
        }).start();
    }

    public Bot getBot() {
        return bot;
    }

    public String[] getChannels() {
        return channels;
    }
}
