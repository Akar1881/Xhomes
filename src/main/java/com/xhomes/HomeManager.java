package com.xhomes;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class HomeManager {
    private final Xhomes plugin;
    private final Map<String, Map<String, Location>> playerHomes = new HashMap<>();
    private final Map<String, Long> teleportCooldowns = new ConcurrentHashMap<>();
    private File homesFile;
    private FileConfiguration homesConfig;
    private static final long COOLDOWN_SECONDS = 30; // Configurable cooldown

    public HomeManager(Xhomes plugin) {
        this.plugin = plugin;
        this.homesFile = new File(plugin.getDataFolder(), "playerhomes.yml");
        createBackup(); // Create backup on startup
        loadHomes();
    }

    private void createBackup() {
        if (!homesFile.exists()) return;
        
        try {
            File backupFile = new File(plugin.getDataFolder(), "playerhomes.yml.backup");
            Files.copy(homesFile.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            plugin.getLogger().warning("Failed to create backup of homes file!");
        }
    }

    public void loadHomes() {
        playerHomes.clear();
        
        if (!homesFile.exists()) {
            try {
                homesFile.getParentFile().mkdirs();
                homesFile.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().severe("Could not create homes file!");
                e.printStackTrace();
                return;
            }
        }

        this.homesConfig = YamlConfiguration.loadConfiguration(homesFile);

        for (String playerName : homesConfig.getKeys(false)) {
            if (homesConfig.getConfigurationSection(playerName) == null) continue;
            
            Map<String, Location> homes = new HashMap<>();
            for (String homeName : homesConfig.getConfigurationSection(playerName).getKeys(false)) {
                String locString = homesConfig.getString(playerName + "." + homeName);
                if (locString == null) continue;

                try {
                    String[] locParts = locString.split(",");
                    if (locParts.length != 6) continue; // Updated for yaw and pitch

                    if (plugin.getServer().getWorld(locParts[0]) == null) continue;

                    Location location = new Location(
                        plugin.getServer().getWorld(locParts[0]),
                        Double.parseDouble(locParts[1]),
                        Double.parseDouble(locParts[2]),
                        Double.parseDouble(locParts[3]),
                        Float.parseFloat(locParts[4]), // yaw
                        Float.parseFloat(locParts[5])  // pitch
                    );
                    homes.put(homeName, location);
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    plugin.getLogger().warning("Invalid home location for player " + playerName + ", home " + homeName);
                }
            }
            if (!homes.isEmpty()) {
                playerHomes.put(playerName, homes);
            }
        }
    }

    public void saveHomes() {
        createBackup(); // Create backup before saving
        
        for (String key : homesConfig.getKeys(false)) {
            homesConfig.set(key, null);
        }

        for (Map.Entry<String, Map<String, Location>> playerEntry : playerHomes.entrySet()) {
            String playerName = playerEntry.getKey();
            Map<String, Location> homes = playerEntry.getValue();
            
            for (Map.Entry<String, Location> homeEntry : homes.entrySet()) {
                String homeName = homeEntry.getKey();
                Location loc = homeEntry.getValue();
                
                if (loc.getWorld() == null) continue;
                
                String locString = String.format("%s,%.2f,%.2f,%.2f,%.2f,%.2f",
                    loc.getWorld().getName(),
                    loc.getX(),
                    loc.getY(),
                    loc.getZ(),
                    loc.getYaw(),
                    loc.getPitch()
                );
                homesConfig.set(playerName + "." + homeName, locString);
            }
        }

        try {
            homesConfig.save(homesFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to save homes file!");
            e.printStackTrace();
        }
    }

    public boolean canTeleport(Player player) {
        Long lastTeleport = teleportCooldowns.get(player.getName());
        if (lastTeleport == null) return true;
        
        long secondsLeft = COOLDOWN_SECONDS - 
            TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - lastTeleport);
        
        if (secondsLeft <= 0) {
            teleportCooldowns.remove(player.getName());
            return true;
        }
        
        player.sendMessage(String.format("You must wait %d seconds before teleporting again!", secondsLeft));
        return false;
    }

    public void setTeleportCooldown(Player player) {
        teleportCooldowns.put(player.getName(), System.currentTimeMillis());
    }

    public void addHome(String playerName, String homeName, Location location) {
        playerHomes.computeIfAbsent(playerName, k -> new HashMap<>()).put(homeName, location);
        saveHomes();
    }

    public void removeHome(String playerName, String homeName) {
        Map<String, Location> homes = playerHomes.get(playerName);
        if (homes != null) {
            homes.remove(homeName);
            if (homes.isEmpty()) {
                playerHomes.remove(playerName);
            }
            saveHomes();
        }
    }

    public Map<String, Location> getHomes(String playerName) {
        return playerHomes.getOrDefault(playerName, new HashMap<>());
    }

    public Xhomes getPlugin() {
        return plugin;
    }
}
