/*
 * Copyright (c) 2024 RDProject
 * Project name: FireFly
 * All rights reserved.
 */

package com.rdproject.firefly.api;

import com.rdproject.firefly.commands.FlyCommand;
import org.bukkit.entity.Player;

/**
 * API Class
 *
 * @author RDProject on 11.08.2024
 */
public class APIImplementation implements FireFlyAPI {

    @Override
    public boolean isPlayerInFlyMode(Player player) {
        return FlyCommand.FLY_PLAYERS.contains(player);
    }

}