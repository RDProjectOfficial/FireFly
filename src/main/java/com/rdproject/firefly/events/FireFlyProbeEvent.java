/*
 * Copyright (c) 2024 RDProject
 * Project name: FireFly
 * All rights reserved.
 */

package com.rdproject.firefly.events;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Event Class
 *
 * @author RDProject on 16.08.2024
 */
public class FireFlyProbeEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    @Getter private final Player player;
    private boolean cancelled;

    public FireFlyProbeEvent(Player player) {
        this.player = player;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

}