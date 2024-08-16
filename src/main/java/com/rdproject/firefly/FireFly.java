/*
 * Copyright (c) 2024 RDProject
 * Project name: FireFly
 * All rights reserved.
 */

package com.rdproject.firefly;

import com.rdproject.firefly.api.*;
import com.rdproject.firefly.commands.FlyCommand;
import com.rdproject.firefly.listeners.*;
import lombok.Getter;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

/**
 * Main Class
 *
 * @author RDProject on 11.08.2024
 */
public class FireFly extends JavaPlugin {

    @Getter private static FireFly instance;
    @Getter private static FireFlyAPI api;

    private Metrics metrics;

    @Override
    public void onEnable() {
        instance = this;
        api = new APIImplementation();
        metrics = new Metrics(this, 23037);

        PluginManager pluginManager = Bukkit.getPluginManager();
        if (pluginManager.isPluginEnabled("AdvancedMLGRush") && getConfig().getBoolean("settings.use-advancedmlgrush-api")) {
            pluginManager.registerEvents(new FireFlyProbeListener(), this);
            pluginManager.registerEvents(new PlayerChangedWorldListener(), this);
        }
        pluginManager.registerEvents(new PlayerQuitListener(), this);
        saveDefaultConfig();
        this.getCommand("fly").setExecutor(new FlyCommand());

        checkUpdates();
        this.getLogger().info("Plugin is enabled!");
    }

    @Override
    public void onDisable() {
        FlyCommand.FLY_PLAYERS.clear();
        metrics.shutdown();
    }

    private void checkUpdates() {
        if (!getConfig().getBoolean("settings.check-updates")) return;
        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            try (InputStream is = new URL("https://api.spigotmc.org/legacy/update.php?resource=118932/~").openStream(); Scanner scanner = new Scanner(is)) {
                if (scanner.hasNext())
                    if (getDescription().getVersion().equals(scanner.next())) {
                        getLogger().info("There is not a new update available.");
                    } else {
                        getLogger().info("There is a new update available.");
                    }
            } catch (IOException exception) {
                getLogger().info("Unable to check for updates: " + exception.getMessage());
            }
        });
    }

}