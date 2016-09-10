package io.sponges.bot.client;

import io.sponges.bot.client.event.events.CommandResponseEvent;
import io.sponges.bot.client.event.events.ResourceRequestEvent;
import io.sponges.bot.client.event.events.StopEvent;
import io.sponges.bot.client.event.framework.EventBus;
import io.sponges.bot.client.protocol.msg.ChatMessage;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

class Main {

    private static final String LOGGER_FORMAT = "[%s - %s] %s\r\n";
    private static final String DUMMY_NETWORK = "cli";
    private static final String DUMMY_CHANNEL = "cli";
    private static final String DUMMY_USER = "cli";

    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");

    public static void main(String[] args) {
        Logger logger = Bot.getLogger();
        logger.setDebug(true);
        logger.log(Logger.Type.INFO, "Starting...");
        long startTime = System.currentTimeMillis();
        Bot bot = new Bot("cli", "-", "localhost", 9574);
        EventBus eventBus = bot.getEventBus();
        eventBus.register(CommandResponseEvent.class, event -> {
            logger.log("CMD", event.getMessage());
        });
        eventBus.register(ResourceRequestEvent.class, event -> {
            Map<String, String> parameters = new HashMap<>();
            switch (event.getType()) {
                case NETWORK:
                    if (!event.getNetworkId().equals(DUMMY_NETWORK)) {
                        event.replyInvalid(bot);
                        return;
                    }
                    parameters.put("name", "Dummy CLI network");
                    break;
                case CHANNEL:
                    if (!event.getChannelId().equals(DUMMY_CHANNEL)) {
                        event.replyInvalid(bot);
                        return;
                    }
                    parameters.put("id", DUMMY_CHANNEL);
                    parameters.put("private", String.valueOf(false));
                    break;
                case USER:
                    if (!event.getUserId().equals(DUMMY_USER)) {
                        event.replyInvalid(bot);
                        return;
                    }
                    parameters.put("id", DUMMY_USER);
                    parameters.put("admin", String.valueOf(true));
                    parameters.put("op", String.valueOf(true));
                    break;
            }
            event.reply(bot, parameters);
        });
        AtomicBoolean running = new AtomicBoolean(true);
        eventBus.register(StopEvent.class, event -> {
            logger.log(Logger.Type.DEBUG, "Got stop event lol");
            running.set(false);
            System.exit(-1);
        });
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            String input;
            try {
                while (running.get() && (input = scanner.nextLine()) != null) {
                    long time = System.currentTimeMillis();
                    ChatMessage message = new ChatMessage(bot, DUMMY_NETWORK, DUMMY_CHANNEL, DUMMY_USER, time, input);
                    bot.getClient().sendMessage(message.toString());
                }
            } catch (NoSuchElementException ignored) {
            }
            scanner.close();
        }).start();
        bot.start();
        long diff = System.currentTimeMillis() - startTime;
        logger.log(Logger.Type.INFO, "Started! Took " + diff + "ms.");
    }

}
