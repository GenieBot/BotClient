package io.sponges.bot.client;

import io.sponges.bot.client.event.CommandEvent;
import io.sponges.bot.client.oldmessages.ChatMessage;

import java.util.Scanner;

public class Main {

    public Main() {
        Bot bot = new Bot("test", new String[] { "testchannel" }, "localhost");

        bot.getEventBus().register(CommandEvent.class, (event) -> {
            System.out.println(event.getResponse());
        });

        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            String input;
            while ((input = scanner.nextLine()) != null) {
                bot.publish(new ChatMessage(bot, "server", "test", "test", "test", "test", "test", "test", input, UserRole.OP));
            }
        }).start();

        bot.start();
    }

    public static void main(String[] args) {
        new Main();
    }

}
