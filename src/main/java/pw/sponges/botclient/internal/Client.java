package pw.sponges.botclient.internal;

import pw.sponges.botclient.Bot;
import pw.sponges.botclient.event.ConnectEvent;
import pw.sponges.botclient.messages.ConnectMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Copyright (c) 2015 Joe Burnard ("SpongyBacon").
 * By viewing this code, you agree to the terms
 * in the enclosed license.txt file.
 */
public class Client {

    private Bot bot;

    private PrintWriter out = null;
    private BufferedReader stdIn = null;

    private Socket socket;
    private ClientThread clientThread = null;

    public Client(Bot bot) {
        this.bot = bot;
    }

    public void start() throws IOException {
        socket = new Socket("localhost", 9090);
        clientThread = new ClientThread(bot, this, socket);
        clientThread.start();

        out = new PrintWriter(socket.getOutputStream(), true);
        bot.sendOutput(new ConnectMessage(bot));
        bot.getEventManager().handle(new ConnectEvent());
    }

    public void stop() throws IOException {
        clientThread.exit();

        socket.close();
        System.exit(-1);
    }

    public PrintWriter getOut() {
        return out;
    }
}
