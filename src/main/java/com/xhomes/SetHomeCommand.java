package com.xhomes;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

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
        if (player.hasPermission("xhomes.hometier4")) {
            return plugin.getConfig().getInt("home_tiers.xhomes.hometier4.maxhomes");
        }
        if (player.hasPermission("xhomes.hometier3")) {
            return plugin.getConfig().getInt("home_tiers.xhomes.hometier3.maxhomes");
        }
        if (player.hasPermission("xhomes.hometier2")) {
            return plugin.getConfig().getInt("home_tiers.xhomes.hometier2.maxhomes");
        }
        if (player.hasPermission("xhomes.hometier1")) {
            return plugin.getConfig().getInt("home_tiers.xhomes.hometier1.maxhomes");
        }
        return -1; // No permission found
    }
}