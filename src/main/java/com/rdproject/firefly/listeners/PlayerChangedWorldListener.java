/*
 * Copyright (c) 2024 RDProject
 * Project name: FireFly
 * All rights reserved.
 */

package com.rdproject.firefly.listeners;

import com.rdproject.firefly.FireFly;
import com.skillplugins.advancedmlgrush.MLGRush;
import com.skillplugins.advancedmlgrush.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

/**
 * Listener Class
 *
 * @author RDProject on 16.08.2024
 */
public class PlayerChangedWorldListener implements Listener {

    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        Bukkit.getScheduler().runTaskLater(FireFly.getInstance(), () -> {
            Player player = event.getPlayer();
            if (!player.isOnline()) return;
            GameState gameState = MLGRush.getApi().getGameState(player);
            if (gameState == GameState.INGAME) {
                player.setAllowFlight(false);
            } else if (gameState != GameState.SPECTATOR && FireFly.FLY_PLAYERS.contains(player)) {
                player.setAllowFlight(true);
            }
        }, 1);
    }

}
