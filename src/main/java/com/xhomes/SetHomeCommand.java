package com.xhomes;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SetHomeCommand implements CommandExecutor {
    private final HomeManager homeManager;
    private final Xhomes plugin;

    public SetHomeCommand(HomeManager homeManager, Xhomes plugin) {
        this.homeManager = homeManager;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can set homes.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 1) {
            player.sendMessage("Usage: /sethome [homename]");
            return true;
        }

        String homeName = args[0];
        Location location = player.getLocation();
        String playerName = player.getName();

        // Get the player's home names
        List<String> playerHomes = new ArrayList<>(homeManager.getHomes(playerName).keySet());

        // Get the maximum homes limit from the config
        int maxHomes = getMaxHomesForPlayer(player);
        if (maxHomes == -1) {
            player.sendMessage("You do not have permission to set homes.");
            return true;
        }

        // Check if the home name already exists
        if (playerHomes.contains(homeName)) {
            player.sendMessage("A home with the name '" + homeName + "' already exists. Please choose a different name.");
            return true;
        }

        // Check if the player has reached their max home limit
        if (playerHomes.size() >= maxHomes) {
            player.sendMessage("You have reached your home limit of " + maxHomes + " homes.");
            return true;
        }

        // Add the home
        homeManager.addHome(playerName, homeName, location);
        player.sendMessage("Home '" + homeName + "' set at your current location.");
        return true;
    }

    private int getMaxHomesForPlayer(Player player) {
        for (String permission : plugin.getConfig().getConfigurationSection("home_tiers").getKeys(false)) {
            if (player.hasPermission(permission)) {
                return plugin.getConfig().getInt("home_tiers." + permission + ".maxhomes");
            }
        }
        return -1; // No valid permission found
    }
}
