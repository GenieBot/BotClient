package io.sponges.bot.client.internal.impl;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.ssl.SslHandler;
import io.sponges.bot.client.event.ConnectEvent;
import io.sponges.bot.client.event.InputEvent;
import io.sponges.bot.client.internal.Client;
import io.sponges.bot.client.util.Msg;
import io.sponges.bot.client.Bot;
import io.sponges.bot.client.messages.ConnectMessage;

public class ClientHandler extends SimpleChannelInboundHandler<String> {

    private final Bot bot;
    private final Client client;

    public ClientHandler(Bot bot, Client client) {
        this.bot = bot;
        this.client = client;
    }

    @Override
    public void channelActive(final ChannelHandlerContext context) {
        context.pipeline().get(SslHandler.class).handshakeFuture().addListener(future -> {
            bot.sendOutput(new ConnectMessage(bot));
            bot.getEventBus().post(new ConnectEvent());
        });
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, String msg) {
        bot.getEventBus().post(new InputEvent(msg));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (!cause.getMessage().equalsIgnoreCase("An existing connection was forcibly closed by the remote host")) {
            cause.printStackTrace();
            ctx.close();
        } else {
            Msg.warning("SERVER CLOSED?!?!?");
            ctx.close();
            try {
                client.stop();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.exit(500);
        }
    }
}
