package pw.sponges.botclient.event;

import pw.sponges.botclient.event.framework.Event;

public class SettingUpdateEvent extends Event {

    private final String room, setting;
    private final Object value;

    public SettingUpdateEvent(String room, String setting, Object value) {
        this.room = room;
        this.setting = setting;
        this.value = value;
    }

    public String getRoom() {
        return room;
    }

    public String getSetting() {
        return setting;
    }

    public Object getValue() {
        return value;
    }

}
