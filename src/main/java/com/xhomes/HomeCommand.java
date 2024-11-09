package com.xhomes;

import org.bukkit.Bukkit;
import org.bukkit.Location; 
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;

public class HomeCommand implements CommandExecutor {
    private final HomeManager homeManager;

    public HomeCommand(HomeManager homeManager) {
        this.homeManager = homeManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can view their homes.");
            return true;
        }

        Player player = (Player) sender;

        // Check if an optional home name is provided
        if (args.length == 1) {
            String homeName = args[0];
            Location homeLocation = homeManager.getHomes(player.getName()).get(homeName);
            if (homeLocation != null) {
                player.teleport(homeLocation);
                player.sendMessage("Teleported to home: " + homeName);
            } else {
                player.sendMessage("Home '" + homeName + "' does not exist.");
            }
            return true;
        }

        // If no home name is provided, show the home selection inventory
        Inventory inv = Bukkit.createInventory(null, 9, "Select Home");

        Map<String, Location> homes = homeManager.getHomes(player.getName());

        int slot = 0;
        for (String homeName : homes.keySet()) {
            if (slot >= 8) break;  
            ItemStack bedItem = new ItemStack(Material.BLUE_BED);
            ItemMeta meta = bedItem.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(homeName);
                meta.setLore(List.of("Click to teleport to " + homeName));
                bedItem.setItemMeta(meta);
            }
            inv.setItem(slot++, bedItem);  
        }

        inv.setItem(8, new ItemStack(Material.BARRIER));

        player.openInventory(inv);
        return true;
    }
}