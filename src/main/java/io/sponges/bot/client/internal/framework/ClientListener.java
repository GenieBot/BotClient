package io.sponges.bot.client.internal.framework;

import io.netty.channel.ChannelHandlerContext;

public interface ClientListener {

    void onConnect(ChannelHandlerContext context);

    void onDisconnect(ChannelHandlerContext context);

    void onMessage(ChannelHandlerContext context, String message);

    void onError(ChannelHandlerContext context, Throwable cause);

}
