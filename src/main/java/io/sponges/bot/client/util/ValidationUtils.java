package io.sponges.bot.client.util;

public final class ValidationUtils {

    public static boolean isValidJson(String input) {
        return input.startsWith("{") && input.endsWith("}");
    }

}
