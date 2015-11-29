package pw.sponges.botclient.internal;

import pw.sponges.botclient.Bot;
import pw.sponges.botclient.event.InputEvent;
import pw.sponges.botclient.util.Msg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Copyright (c) 2015 Joe Burnard ("SpongyBacon").
 * By viewing this code, you agree to the terms
 * in the enclosed license.txt file.
 */
public class ClientThread extends Thread implements Runnable {

    private Bot bot;
    private Client client;
    private Socket socket;

    private BufferedReader in = null;

    public ClientThread(Bot bot, Client client, Socket socket) {
        this.bot = bot;
        this.client = client;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String input;

            while ((input = in.readLine()) != null) {
                Msg.debug("INPUT> " + input);

                if (input.equalsIgnoreCase("Connected to Server.")) {
                    Msg.log(input);
                    continue;
                }

                bot.getEventManager().handle(new InputEvent(input));
            }

            Msg.warning(System.currentTimeMillis() + " Quiting as input closed.");
            exit();
            client.stop();
        } catch (IOException e) {
            if (e.getMessage().equalsIgnoreCase("Connection reset")) {
                Msg.warning("Server connection reset.");
                exit();
                try {
                    client.stop();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } else {
                e.printStackTrace();
            }
        }
    }

    public void exit() {
        try {
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
