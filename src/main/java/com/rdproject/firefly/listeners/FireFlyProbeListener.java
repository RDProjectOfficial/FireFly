/*
 * Copyright (c) 2024 RDProject
 * Project name: FireFly
 * All rights reserved.
 */

package com.rdproject.firefly.listeners;

import com.rdproject.firefly.events.FireFlyProbeEvent;
import com.skillplugins.advancedmlgrush.MLGRush;
import com.skillplugins.advancedmlgrush.game.GameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Listener Class
 *
 * @author RDProject on 16.08.2024
 */
public class FireFlyProbeListener implements Listener {

    @EventHandler
    public void onFireFlyProbe(FireFlyProbeEvent event) {
        if (MLGRush.getApi().getGameState(event.getPlayer()) != GameState.LOBBY) {
            event.setCancelled(true);
        }
    }

}