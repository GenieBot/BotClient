package pw.sponges.botclient.newinternal.impl;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.ssl.SslHandler;
import pw.sponges.botclient.Bot;
import pw.sponges.botclient.event.ConnectEvent;
import pw.sponges.botclient.event.InputEvent;
import pw.sponges.botclient.messages.ConnectMessage;

public class ClientHandler extends SimpleChannelInboundHandler<String> {

    private final Bot bot;

    public ClientHandler(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void channelActive(final ChannelHandlerContext context) {
        context.pipeline().get(SslHandler.class).handshakeFuture().addListener(future -> {
            bot.sendOutput(new ConnectMessage(bot));
            bot.getEventManager().handle(new ConnectEvent());
        });
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, String msg) {
        //System.err.println(msg);
        bot.getEventManager().handle(new InputEvent(msg));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
