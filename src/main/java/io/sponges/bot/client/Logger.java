package io.sponges.bot.client;

public interface Logger {

    void log(Type type, String message);

    void log(String type, String message);

    enum Type {
        INFO, DEBUG, WARNING
    }

}
