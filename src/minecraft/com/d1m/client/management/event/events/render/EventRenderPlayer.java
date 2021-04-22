package com.d1m.client.management.event.events.render;

import com.d1m.client.management.event.Event;
import net.minecraft.client.entity.AbstractClientPlayer;

public class EventRenderPlayer extends Event {

    public AbstractClientPlayer entity;

    public EventRenderPlayer(final AbstractClientPlayer entity) {
        this.entity = entity;
    }
}
