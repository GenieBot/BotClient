package io.sponges.bot.client.internal.framework;

import io.sponges.bot.client.internal.framework.exception.ClientAlreadyRunningException;
import io.sponges.bot.client.internal.framework.exception.ClientNotRunningException;

public interface Client {

    void start(Runnable runnable) throws ClientAlreadyRunningException, InterruptedException;

    void stop(Runnable runnable) throws ClientNotRunningException;

    void registerListener(ClientListener listener);

    void unregisterListener(ClientListener listener);

    void sendMessage(String message);

}