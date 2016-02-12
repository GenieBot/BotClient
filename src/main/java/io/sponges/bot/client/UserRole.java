package io.sponges.bot.client;

public enum UserRole {

    OP, ADMIN, USER;

    @Override
    public String toString() {
        String[][] arr = {
                {
                        "key",
                        "value"
                },
                {
                        "key2",
                        "value2"
                }
        };

        String[] keys = {
                "key",
                "key2"
        };

        String[] values = {
                "value",
                "value2"
        };

        return name().toLowerCase();
    }
}
