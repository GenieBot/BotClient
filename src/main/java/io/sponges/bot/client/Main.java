package io.sponges.bot.client;

import io.sponges.bot.client.event.events.CommandResponseEvent;
import io.sponges.bot.client.event.events.ResourceRequestEvent;
import io.sponges.bot.client.event.events.StopEvent;
import io.sponges.bot.client.event.framework.EventBus;
import io.sponges.bot.client.protocol.msg.ChatMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

class Main {

    private static final String DUMMY_NETWORK = "cli";
    private static final String DUMMY_CHANNEL = "cli";
    private static final String DUMMY_USER = "cli";

    private final Bot bot;

    private Main() {
        Logger logger = Bot.getLogger();
        logger.setDebug(false);
        logger.log(Logger.Type.INFO, "Starting...");
        long startTime = System.currentTimeMillis();
        this.bot = new Bot("cli", "-", "localhost", 9574);
        EventBus eventBus = bot.getEventBus();
        eventBus.register(CommandResponseEvent.class, this::onCommandResponse);
        eventBus.register(ResourceRequestEvent.class, this::onResourceRequest);
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

    public static void main(String[] args) {
        new Main();
    }

    private void onCommandResponse(CommandResponseEvent event) {
        Bot.getLogger().log("CMD", event.getMessage());
    }

    private void onResourceRequest(ResourceRequestEvent event) {
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
    }

}
