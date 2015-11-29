package pw.sponges.botclient.newinternal.impl;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import pw.sponges.botclient.Bot;
import pw.sponges.botclient.event.InputEvent;

public class ClientHandler extends SimpleChannelInboundHandler<String> {

    private final Bot bot;

    public ClientHandler(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, String msg) {
        bot.getEventManager().handle(new InputEvent(msg));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
