package pw.sponges.botclient;

public enum UserRole {

    OP, ADMIN, USER;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
