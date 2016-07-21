package io.sponges.bot.client;

import io.sponges.bot.client.event.events.ChannelMessageReceiveEvent;
import io.sponges.bot.client.event.events.CommandResponseEvent;
import io.sponges.bot.client.event.events.StopEvent;
import io.sponges.bot.client.protocol.msg.ChatMessage;
import io.sponges.bot.client.protocol.msg.UserJoinMessage;

import java.util.Scanner;

public class Main {

    public Main() {
        Bot bot = new Bot("cli", "-", "localhost", 9574);
        bot.getEventBus().register(CommandResponseEvent.class, event -> {
            System.out.println(event.getMessage());
        });
        bot.getEventBus().register(ChannelMessageReceiveEvent.class, event -> {
            event.reply(bot, "hello i am main test and i am hack!");
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
        bot.start();
    }

    public static void main(String[] args) {
        new Main();
    }

}
