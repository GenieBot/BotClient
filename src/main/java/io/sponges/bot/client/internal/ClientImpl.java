package io.sponges.bot.client.internal;

import io.netty.channel.ChannelHandlerContext;
import io.sponges.bot.client.Bot;
import io.sponges.bot.client.Logger;
import io.sponges.bot.client.event.events.internal.ClientInputEvent;
import io.sponges.bot.client.internal.framework.Client;
import io.sponges.bot.client.internal.framework.ClientListener;
import io.sponges.bot.client.internal.framework.exception.ClientAlreadyRunningException;
import io.sponges.bot.client.internal.framework.exception.ClientNotRunningException;
import io.sponges.bot.client.protocol.msg.ConnectMessage;
import org.json.JSONObject;

public class ClientImpl {

    private final Object lock = new Object();
    private volatile boolean running = false;

    private final Bot bot;
    private final Client client;

    public ClientImpl(String host, int port, Bot bot) {
        this.bot = bot;
        this.client = new io.sponges.bot.client.internal.framework.impl.ClientImpl(host, port);
        this.client.registerListener(new ClientListener() {
            @Override
            public void onConnect(ChannelHandlerContext context) {
                sendMessage(new ConnectMessage(bot).toString());
            }

            @Override
            public void onDisconnect(ChannelHandlerContext context) {
                Bot.getLogger().log(Logger.Type.INFO, "Disconnected!");
                context.channel().close();
                context.channel().parent().close();
                context.close();
            }

            @Override
            public void onMessage(ChannelHandlerContext context, String message) {
                if (message.charAt(0) != '{' || message.charAt(message.length() - 1) != '}') {
                    Bot.getLogger().log(Logger.Type.WARNING, "Got invalid json " + message + "!");
                    return;
                }

                bot.getEventBus().post(new ClientInputEvent(new JSONObject(message)));
            }

            @Override
            public void onError(ChannelHandlerContext context, Throwable cause) {

            }
        });
    }

    public void start(Runnable runnable) {
        try {
            client.start(runnable);
        } catch (ClientAlreadyRunningException | InterruptedException e) {
            e.printStackTrace();
        }
        running = true;
    }

    public void stop() {
        try {
            client.stop(() -> Bot.getLogger().log(Logger.Type.DEBUG, "stopped"));
        } catch (ClientNotRunningException e) {
            e.printStackTrace();
        }
        running = false;
    }

    public boolean isRunning() {
        return running;
    }

    public void sendMessage(String message) {
        client.sendMessage(message);
    }
}
