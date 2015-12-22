package pw.sponges.botclient.internal.impl;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import pw.sponges.botclient.Bot;
import pw.sponges.botclient.internal.Client;
import pw.sponges.botclient.util.Msg;

import javax.net.ssl.SSLException;

public class ClientImpl implements Client {

    public static final String HOST = "127.0.0.1";
    public static final int PORT = 9090;

    public static boolean accepting = true;

    private final Bot bot;

    private EventLoopGroup group = null;
    private Bootstrap bootstrap = null;
    private ChannelFuture future = null;
    private Channel channel = null;
    private ChannelFuture lastFuture = null;

    public ClientImpl(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void start() throws SSLException, InterruptedException {
        SslContext context = SslContext.newClientContext(InsecureTrustManagerFactory.INSTANCE);

        group = new NioEventLoopGroup();

        try {
            bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ClientInitializer(bot, context));

            future = bootstrap.connect(HOST, PORT).sync();
            channel = future.channel();

            while (accepting);

            // Wait until all messages are flushed before closing the channel
            if (lastFuture != null) {
                lastFuture.sync();
            }
        } finally {
            // The connection is closed automatically on shutdown.
            group.shutdownGracefully();
        }
    }

    @Override
    public void stop() throws InterruptedException {
        Msg.debug("Stop called");
        accepting = false;

        try {
            if (lastFuture != null) {
                Msg.debug("Syncing future");
                lastFuture.sync();
                Msg.debug("Synced future");
            }
        } finally {
            Msg.debug("Shutting down");
            group.shutdownGracefully();
            Msg.debug("shut down");
        }

        System.exit(500);
    }

    @Override
    public void write(String message) {
        lastFuture = channel.writeAndFlush(message + "\r\n");
        Msg.debug(message);
    }
}