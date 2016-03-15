package io.sponges.bot.client;

import io.sponges.bot.client.event.events.CommandResponseEvent;
import io.sponges.bot.client.protocol.msg.ChatMessage;

import java.util.Scanner;

public class Main {

    public Main() {
        Bot bot = new Bot("cli", "-", "localhost", 9574);

        bot.getEventBus().register(CommandResponseEvent.class, (event) -> {
            System.out.println(event.getMessage());
        });

        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            String input;
            while ((input = scanner.nextLine()) != null) {
                ChatMessage chatMessage = new ChatMessage(bot, "cli", "cli", true, "cli", null, null,
                        System.currentTimeMillis(), input);
                bot.getClient().sendMessage(chatMessage.toString());

                if (input.equalsIgnoreCase("-stop")) break;
            }
            scanner.close();
        }).start();

        bot.start();
    }

    public static void main(String[] args) {
        new Main();
    }

}
