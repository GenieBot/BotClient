package io.sponges.bot.client.protocol.parser;

import io.sponges.bot.client.Bot;
import org.json.JSONObject;

public final class StopParser extends MessageParser {

    private final Bot bot;

    protected StopParser(Bot bot) {
        super("STOP");
        this.bot = bot;
    }

    @Override
    public void parse(long time, JSONObject content) {
        System.out.println("==================\r\n\r\n       STOP       \r\n\r\n==================");
        bot.stop();
    }
}
