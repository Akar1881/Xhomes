package com.xhomes;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Xhomes - A home management plugin for Minecraft.
 *
 * This plugin is licensed under the GNU General Public License v3.
 * You may not remove this comment or modify the licensing terms.
 *
 * Copyright (C) [2024]  [Akar1881]
 *
 * This is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software. If not, see <https://www.gnu.org/licenses/>.
 */
public class Xhomes extends JavaPlugin {
    private HomeManager homeManager;

    @Override
    public void onEnable() {
        homeManager = new HomeManager(this);
        getCommand("sethome").setExecutor(new SetHomeCommand(homeManager, this));
        getCommand("home").setExecutor(new HomeCommand(homeManager));
        getCommand("delhome").setExecutor(new DelHomeCommand(homeManager));
        getServer().getPluginManager().registerEvents(new HomeListener(homeManager), this);
        getLogger().info("Xhomes has been enabled!");
    }

    public HomeManager getHomeManager() {
        return homeManager;
    }
}
