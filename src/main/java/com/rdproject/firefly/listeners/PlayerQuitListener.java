/*
 * Copyright (c) 2024 RDProject
 * Project name: FireFly
 * All rights reserved.
 */

package com.rdproject.firefly.listeners;

import com.rdproject.firefly.commands.FlyCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Listener Class
 *
 * @author RDProject on 16.08.2024
 */
public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        FlyCommand.FLY_PLAYERS.remove(event.getPlayer());
    }

}