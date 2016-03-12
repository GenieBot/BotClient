package io.sponges.bot.client.cache;

import java.util.Optional;

public class User {

    private final String id;

    private Optional<String> username = Optional.empty();
    private Optional<String> displayName = Optional.empty();

    public User(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Optional<String> getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = Optional.of(username);
    }

    public Optional<String> getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = Optional.of(displayName);
    }
}
