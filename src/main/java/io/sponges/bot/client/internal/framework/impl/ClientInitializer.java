package io.sponges.bot.client.internal.framework.impl;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public final class ClientInitializer extends ChannelInitializer<SocketChannel> {

    private final ClientHandler clientHandler;

    public ClientInitializer(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    @Override
    public void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        // text line codec
        pipeline.addLast(new DelimiterBasedFrameDecoder(16384, Delimiters.lineDelimiter()));
        pipeline.addLast(new StringDecoder()); // you can specify the charset in here
        pipeline.addLast(new StringEncoder());

        pipeline.addLast(clientHandler);
    }
}
