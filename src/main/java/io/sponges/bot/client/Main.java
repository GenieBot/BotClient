package io.sponges.bot.client;

import io.sponges.bot.client.event.events.CommandResponseEvent;
import io.sponges.bot.client.event.events.ResourceRequestEvent;
import io.sponges.bot.client.event.framework.EventBus;
import io.sponges.bot.client.protocol.msg.ChatMessage;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Main {

    private static final String LOGGER_FORMAT = "[%s - %s] %s\r\n";
    private static final String DUMMY_NETWORK = "cli";
    private static final String DUMMY_CHANNEL = "cli";
    private static final String DUMMY_USER = "cli";

    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");

    Main() {
        Logger logger = Bot.getLogger();
        logger.log(Logger.Type.INFO, "Starting...");
        Bot bot = new Bot("cli", "-", "localhost", 9574);
        EventBus eventBus = bot.getEventBus();
        eventBus.register(CommandResponseEvent.class, event -> {
            logger.log("CMD", event.getMessage());
        });
        eventBus.register(ResourceRequestEvent.class, event -> {
            Map<String, String> parameters = new HashMap<>();
            switch (event.getType()) {
                case NETWORK:
                    parameters.put("name", "Dummy CLI network");
                    break;
                case CHANNEL:
                    parameters.put("id", DUMMY_CHANNEL);
                    parameters.put("private", String.valueOf(false));
                    break;
                case USER:
                    parameters.put("id", DUMMY_USER);
                    parameters.put("admin", String.valueOf(true));
                    parameters.put("op", String.valueOf(true));
                    break;
            }
            event.reply(bot, parameters);
        });
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            String input;
            while ((input = scanner.nextLine()) != null) {
                long time = System.currentTimeMillis();
                ChatMessage message = new ChatMessage(bot, DUMMY_NETWORK, DUMMY_CHANNEL, DUMMY_USER, time, input);
                bot.getClient().sendMessage(message.toString());
            }
            scanner.close();
        }).start();
        bot.start();

        /*bot.getEventBus().register(CommandResponseEvent.class, event -> {
            System.out.println(event.getMessage());
        });
        bot.getEventBus().register(StopEvent.class, event -> {
            System.out.println("stop nigga");
            System.exit(-1);
        });
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            String input;
            while ((input = scanner.nextLine()) != null) {
                ChatMessage chatMessage = new ChatMessage(bot, "cli", "cli", "cli", System.currentTimeMillis(), input);
                bot.getClient().sendChannelMessage(bot, input, response -> {
                    System.out.println("Got response from callback: " + response);
                });
                bot.getClient().sendMessage(chatMessage.toString());
                if (input.equalsIgnoreCase("userjoin")) {
                    UserJoinMessage userJoinMessage = new UserJoinMessage(bot, "cli", "cli", "addedUserId", "initiatorId");
                    bot.getClient().sendMessage(userJoinMessage.toString());
                }
            }
            scanner.close();
        }).start();
        bot.start();*/
    }

    public static void main(String[] args) {
        new Main();
    }

}
