/*
 * Copyright (c) 2024 RDProject
 * Project name: FireFly
 * All rights reserved.
 */

package com.rdproject.firefly.commands;

import com.rdproject.firefly.FireFly;
import com.rdproject.firefly.events.FireFlyProbeEvent;
import com.rdproject.firefly.utilities.ChatUtil;
import com.rdproject.firefly.utilities.CommandUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

/**
 * Command Class
 *
 * @author RDProject on 11.08.2024
 */
public class FlyCommand implements CommandExecutor {

    public static final HashSet<Player> FLY_PLAYERS = new HashSet<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!CommandUtil.consoleSenderSupport(sender) || !CommandUtil.hasPermission(sender, "fly")) return false;
        Player player = (Player) sender;

        if (args.length == 0) {
            if (!isAllowedFly(player)) return true;
            if (!FLY_PLAYERS.add(player)) {
                FLY_PLAYERS.remove(player);
            }
            player.setAllowFlight(!player.getAllowFlight());
            player.sendMessage(ChatUtil.colorWithPrefix(player.getAllowFlight() ? "fly.enable" : "fly.disable"));
            return true;
        }

        switch (args[0]) {
            case "reset" -> {
                if (args.length == 1) {
                    player.setFlySpeed(0.1F);
                    player.sendMessage(ChatUtil.colorWithPrefix("fly.reset"));
                    return true;
                }
                if (!CommandUtil.hasPermission(player, "fly-admin")) return false;
                Player target = Bukkit.getPlayerExact(args[1]);
                if (!CommandUtil.checkIsThereTarget(player, target)) return false;
                target.setFlySpeed(0.1F);
                target.sendMessage(ChatUtil.colorWithPrefix("fly.reset"));
                player.sendMessage(ChatUtil.colorWithPrefix("fly.reset-other").replace("{target}", target.getName()));
            }
            case "speed" -> {
                switch (args.length) {
                    case 2 -> {
                        try {
                            player.setFlySpeed(Float.parseFloat(args[1]) / 10);
                            player.sendMessage(ChatUtil.colorWithPrefix("fly.set-speed")
                                    .replace("{speed}", args[1]));
                        } catch (IllegalArgumentException e) {
                            player.sendMessage(ChatUtil.colorWithPrefix("fly.incorrect-speed")
                                    .replace("{speed}", args[1]));
                        }
                    }
                    case 3 -> {
                        if (!CommandUtil.hasPermission(player, "fly-admin")) return false;
                        Player target = Bukkit.getPlayerExact(args[2]);
                        if (!CommandUtil.checkIsThereTarget(player, target)) return false;
                        try {
                            target.setFlySpeed(Float.parseFloat(args[1]) / 10);
                            target.sendMessage(ChatUtil.colorWithPrefix("fly.set-speed")
                                    .replace("{speed}", args[1]));
                            player.sendMessage(ChatUtil.colorWithPrefix("fly.set-speed-other")
                                    .replace("{speed}", args[1])
                                    .replace("{target}", target.getName()));
                        } catch (IllegalArgumentException e) {
                            player.sendMessage(ChatUtil.colorWithPrefix("fly.incorrect-speed")
                                    .replace("{speed}", args[1]));
                        }
                    }
                    default -> player.sendMessage(ChatUtil.colorWithPrefix("incorrect-arguments"));
                }
            }
            case "reload" -> {
                if (!CommandUtil.hasPermission(player, "fly-admin")) return false;
                ChatUtil.COLORED_STRINGS.clear();
                FireFly.getInstance().reloadConfig();
                player.sendMessage(ChatUtil.colorWithPrefix("config-reloaded"));
            }
            case "help" -> {
                player.sendMessage(ChatUtil.color("fly.help"));
                if (player.hasPermission("firefly.admin")) player.sendMessage(ChatUtil.color("fly.admin-help"));
            }
            default -> {
                if (!CommandUtil.hasPermission(player, "fly-other")) return false;
                Player target = Bukkit.getPlayerExact(args[0]);
                if (!CommandUtil.checkIsThereTarget(player, target)) return false;
                if (!isAllowedFly(player)) return true;
                if (!FLY_PLAYERS.add(target)) {
                    FLY_PLAYERS.remove(target);
                }
                target.setAllowFlight(!target.getAllowFlight());
                target.sendMessage(ChatUtil.colorWithPrefix(target.getAllowFlight() ? "fly.enable" : "fly.disable"));
                player.sendMessage(ChatUtil.colorWithPrefix(target.getAllowFlight() ? "fly.enable-other" : "fly.disable-other")
                        .replace("{target}", target.getName()));
            }
        }
        return false;
    }

    private boolean isAllowedFly(Player player) {
        FireFlyProbeEvent event = new FireFlyProbeEvent(player);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            player.sendMessage(ChatUtil.colorWithPrefix("fly.not-available"));
            return false;
        }
        return true;
    }

}