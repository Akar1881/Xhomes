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
import org.bukkit.scheduler.BukkitRunnable; // Import this class

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
                startTeleportCountdown(player, homeLocation);
            } else {
                player.sendMessage("Home '" + homeName + "' does not exist.");
            }
            return true;
        }

        // If no home name is provided, show the home selection inventory
        Inventory inv = Bukkit.createInventory(null, 27, "Select Home");

        Map<String, Location> homes = homeManager.getHomes(player.getName());

        int slot = 9;
        for (String homeName : homes.keySet()) {
            if (slot >= 18) break;
            ItemStack bedItem = new ItemStack(Material.BLUE_BED);
            ItemMeta meta = bedItem.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(homeName);
                meta.setLore(List.of("Click to teleport to " + homeName));
                bedItem.setItemMeta(meta);
            }
            inv.setItem(slot++, bedItem);
        }

        inv.setItem(26, new ItemStack(Material.BARRIER));

        player.openInventory(inv);
        return true;
    }

    private void startTeleportCountdown(Player player, Location homeLocation) {
        player.sendMessage("Teleporting to home in 5 seconds, don't move!");

        new BukkitRunnable() {
            int countdown = 5;
            Location initialLocation = player.getLocation(); // Store initial location

            @Override
            public void run() {
                if (!player.isOnline()) {
                    cancel();
                    return;
                }

                // Check if the player has moved
                if (hasPlayerMoved(player, initialLocation)) {
                    player.sendMessage("Teleportation canceled because you moved!");
                    cancel();
                    return;
                }

                if (countdown <= 0) {
                    player.teleport(homeLocation);
                    player.sendMessage("Teleported to home: " + homeLocation.getWorld().getName());
                    cancel();
                    return;
                }

                // Display countdown message in the center of the screen
                player.sendTitle("", "Teleporting in " + countdown + " seconds", 0, 20, 0);
                countdown--;
            }
        }.runTaskTimer(homeManager.getPlugin(), 0, 20); // Use homeManager.getPlugin()
    }

    // Helper method to check if a player has moved significantly (walking, not just rotating)
    private boolean hasPlayerMoved(Player player, Location initialLocation) {
        Location currentLocation = player.getLocation();
        return initialLocation.getX() != currentLocation.getX() || 
               initialLocation.getY() != currentLocation.getY() || 
               initialLocation.getZ() != currentLocation.getZ();
    }
}