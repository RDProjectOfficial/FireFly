/*
 * Copyright (c) 2024 RDProject
 * Project name: FireFly
 * All rights reserved.
 */

package com.rdproject.firefly.utilities;

import com.rdproject.firefly.FireFly;
import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

import java.util.HashMap;

/**
 * Utility Class
 *
 * @author RDProject on 11.08.2024
 */
@UtilityClass
public class ChatUtil {

    public final HashMap<String, String> COLORED_STRINGS = new HashMap<>();

    public String color(String path) {
        return COLORED_STRINGS.computeIfAbsent(path, it -> ChatColor.translateAlternateColorCodes('&',
                FireFly.getInstance().getConfig().getString("messages." + path)));
    }

    public String colorWithPrefix(String path) {
        return color("prefix") + color(path);
    }

}