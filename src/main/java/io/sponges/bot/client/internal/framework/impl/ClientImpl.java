package io.sponges.bot.client.internal.framework.impl;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.internal.ConcurrentSet;
import io.sponges.bot.client.internal.framework.Client;
import io.sponges.bot.client.internal.framework.ClientListener;
import io.sponges.bot.client.internal.framework.exception.ClientAlreadyRunningException;
import io.sponges.bot.client.internal.framework.exception.ClientNotRunningException;

import java.util.Set;

public final class ClientImpl implements Client {

    private final Object lock = new Object();
    private volatile boolean running = false;
    private final Set<ClientListener> listeners = new ConcurrentSet<>();

    private final String host;
    private final int port;
    private final NioEventLoopGroup eventLoopGroup;
    private final ClientHandler clientHandler;

    public ClientImpl(String host, int port) {
        this.host = host;
        this.port = port;
        this.eventLoopGroup = new NioEventLoopGroup();
        this.clientHandler = new ClientHandler(this);
    }

    @Override
    public void start(Runnable runnable) throws ClientAlreadyRunningException, InterruptedException {
        if (running) {
            throw new ClientAlreadyRunningException();
        }
        running = true;

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ClientInitializer(clientHandler));
        bootstrap.connect(host, port).sync().channel();
    }

    @Override
    public void stop(Runnable runnable) throws ClientNotRunningException {
        if (!running) {
            throw new ClientNotRunningException();
        }
        running = false;
        eventLoopGroup.shutdownGracefully();
        runnable.run();
    }

    @Override
    public void registerListener(ClientListener listener) {
        listeners.add(listener);
    }

    @Override
    public void unregisterListener(ClientListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void sendMessage(String message) {
        clientHandler.getChannel().writeAndFlush(message + "\r\n");
    }

    public Set<ClientListener> getListeners() {
        return listeners;
    }
}