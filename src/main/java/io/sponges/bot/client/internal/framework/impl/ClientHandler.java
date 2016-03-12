package io.sponges.bot.client.internal.framework.impl;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.sponges.bot.client.internal.framework.ClientListener;

public final class ClientHandler extends SimpleChannelInboundHandler<String> {

    private volatile Channel channel = null;

    private final ClientImpl client;

    public ClientHandler(ClientImpl client) {
        this.client = client;
    }

    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) {
        channelHandlerContext.newSucceededFuture().addListener(future -> {
            channel = channelHandlerContext.channel();
            for (ClientListener clientListener : client.getListeners()) {
                clientListener.onConnect(channelHandlerContext);
            }
        });
    }

    @Override
    public void channelInactive(ChannelHandlerContext channelHandlerContext) {
        for (ClientListener clientListener : client.getListeners()) {
            clientListener.onDisconnect(channelHandlerContext);
        }
    }

    @Override
    public void messageReceived(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        for (ClientListener clientListener : client.getListeners()) {
            clientListener.onMessage(channelHandlerContext, msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) {
        for (ClientListener clientListener : client.getListeners()) {
            clientListener.onError(channelHandlerContext, cause);
        }
    }

    public Channel getChannel() {
        return channel;
    }
}
