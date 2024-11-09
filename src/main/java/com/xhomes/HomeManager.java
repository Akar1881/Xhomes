package com.xhomes;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HomeManager {
    private final Xhomes plugin;
    private final Map<String, Map<String, Location>> playerHomes = new HashMap<>();
    private File homesFile;
    private FileConfiguration homesConfig;

    public HomeManager(Xhomes plugin) {
        this.plugin = plugin;
        this.homesFile = new File(plugin.getDataFolder(), "playerhomes.yml");
        this.homesConfig = YamlConfiguration.loadConfiguration(homesFile);
        loadHomes();
    }

    public void loadHomes() {
        for (String playerName : homesConfig.getKeys(false)) {
            Map<String, Location> homes = new HashMap<>();
            for (String homeName : homesConfig.getConfigurationSection(playerName).getKeys(false)) {
                String locString = homesConfig.getString(playerName + "." + homeName);
                String[] locParts = locString.split(",");
                Location location = new Location(plugin.getServer().getWorld(locParts[0]), 
                        Double.parseDouble(locParts[1]), Double.parseDouble(locParts[2]), Double.parseDouble(locParts[3]));
                homes.put(homeName, location);
            }
            playerHomes.put(playerName, homes);
        }
    }

    public void saveHomes() {
        for (String playerName : playerHomes.keySet()) {
            Map<String, Location> homes = playerHomes.get(playerName);
            for (String homeName : homes.keySet()) {
                Location loc = homes.get(homeName);
                String locString = loc.getWorld().getName() + "," + loc.getX() + "," + loc.getY() + "," + loc.getZ();
                homesConfig.set(playerName + "." + homeName, locString);
            }
        }
        try {
            homesConfig.save(homesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addHome(String playerName, String homeName, Location location) {
        playerHomes.computeIfAbsent(playerName, k -> new HashMap<>()).put(homeName, location);
        saveHomes();
    }

    public void removeHome(String playerName, String homeName) {
        Map<String, Location> homes = playerHomes.get(playerName);
        if (homes != null) {
            homes.remove(homeName);
            saveHomes();
        }
    }

    public Map<String, Location> getHomes(String playerName) {
        return playerHomes.getOrDefault(playerName, new HashMap<>());
    }

    public Xhomes getPlugin() {
        return plugin;  // Method to retrieve the main plugin instance
    }
}