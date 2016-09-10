package io.sponges.bot.client.protocol.msg;

import io.sponges.bot.client.Bot;
import io.sponges.bot.client.event.events.ResourceRequestEvent;
import org.json.JSONObject;

import java.util.Map;

public final class ResourceResponseMessage extends Message {

    private final String requestId;
    private final ResourceRequestEvent.ResourceType type;
    private final String networkId;
    private final Map<String, String> parameters;
    private final boolean found;

    public ResourceResponseMessage(Bot bot, String requestId, ResourceRequestEvent.ResourceType type) {
        super(bot, "RESOURCE_RESPONSE");
        this.requestId = requestId;
        this.type = type;
        this.networkId = null;
        this.parameters = null;
        this.found = false;
    }

    public ResourceResponseMessage(Bot bot, String requestId, ResourceRequestEvent.ResourceType type, String networkId, Map<String, String> parameters) {
        super(bot, "RESOURCE_RESPONSE");
        this.requestId = requestId;
        this.type = type;
        this.networkId = networkId;
        this.parameters = parameters;
        this.found = true;
    }

    @Override
    protected JSONObject toJson() {
        JSONObject object = new JSONObject();
        object.put("resource", type.name());
        object.put("id", requestId);
        if (networkId != null) object.put("network", networkId);
        if (parameters != null) object.put("params", parameters);
        object.put("found", found);
        return object;
    }
}
