/*
 * Copyright (c) 2024 RDProject
 * Project name: FireFly
 * All rights reserved.
 */

package com.rdproject.firefly.utilities;

import lombok.experimental.UtilityClass;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Utility Class
 *
 * @author RDProject on 11.08.2024
 */
@UtilityClass
public class CommandUtil {

    public boolean consoleSenderSupport(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatUtil.color("only-players"));
            return false;
        }
        return true;
    }

    public boolean hasPermission(CommandSender sender, String permission) {
        if (!sender.hasPermission("firefly." + permission)) {
            sender.sendMessage(ChatUtil.color("no-permission").replace("{permission}", "firefly." + permission));
            return false;
        }
        return true;
    }

    public boolean checkIsThereTarget(Player player, Player target) {
        if (target == null || !target.isOnline()) {
            player.sendMessage(ChatUtil.colorWithPrefix("offline-player"));
            return false;
        }
        return true;
    }

    public void sendIncorrectArgumentsMsg(Player player) {
        player.sendMessage(ChatUtil.colorWithPrefix("incorrect-arguments"));
    }

}