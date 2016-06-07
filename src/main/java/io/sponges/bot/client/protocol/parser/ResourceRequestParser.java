package io.sponges.bot.client.protocol.parser;

import io.sponges.bot.client.event.events.ResourceRequestEvent;
import io.sponges.bot.client.event.framework.EventBus;
import org.json.JSONObject;

public final class ResourceRequestParser extends MessageParser {

    private final EventBus eventBus;

    protected ResourceRequestParser(EventBus eventBus) {
        super("RESOURCE_REQUEST");
        this.eventBus = eventBus;
    }

    @Override
    public void parse(long time, JSONObject content) {
        ResourceRequestEvent.ResourceType type = ResourceRequestEvent.ResourceType
                .valueOf(content.getString("resource").toUpperCase());
        String requestId = content.getString("id");
        String network = content.getString("network");
        ResourceRequestEvent event;
        switch (type) {
            case NETWORK: {
                event = new ResourceRequestEvent(requestId, network);
                break;
            }
            case CHANNEL: {
                event = new ResourceRequestEvent(type, requestId, network, content.getString("channel"));
                break;
            }
            case USER: {
                event = new ResourceRequestEvent(type, requestId, network, content.getString("user"));
                break;
            }
            default:
                System.err.println("Invalid resource request type \"" + type + "\"!");
                return;
        }
        eventBus.post(event);
    }
}
