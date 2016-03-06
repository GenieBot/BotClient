package io.sponges.bot.client.internal;

import io.sponges.bot.client.oldmessages.Message;

public interface Client {
    
    void start();

    void stop();

    void publish(Message message);

    void publish(String channel, String message);

}
