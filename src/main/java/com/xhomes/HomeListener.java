package com.xhomes;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class HomeListener implements Listener {
    private final HomeManager homeManager;

    public HomeListener(HomeManager homeManager) {
        this.homeManager = homeManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals("Select Home")) {
            return; // Only handle clicks in the homes inventory
        }

        event.setCancelled(true); // Prevent default behavior

        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();

        if (slot == 26) {
            player.closeInventory(); // Close GUI if barrier is clicked
            return;
        }

        if (slot < 9 || slot >= 18) return; // Only handle slots 0-7

        String homeName = event.getCurrentItem().getItemMeta().getDisplayName();
        Location homeLocation = homeManager.getHomes(player.getName()).get(homeName);

        if (homeLocation != null) {
            startTeleportCountdown(player, homeLocation);
        } else {
            player.sendMessage(ChatColor.RED + "Home '" + homeName + "' does not exist.");
        }

        player.closeInventory(); // Close inventory after selecting
    }

    private void startTeleportCountdown(Player player, Location homeLocation) {
        player.sendMessage(ChatColor.YELLOW + "Teleporting in 5 seconds, don't move!");

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
                    player.sendMessage(ChatColor.RED + "Teleportation canceled because you moved!");
                    cancel();
                    return;
                }

                if (countdown <= 0) {
                    player.teleport(homeLocation);
                    player.sendMessage(ChatColor.GREEN + "Teleported to home: " + homeLocation.getWorld().getName());
                    cancel();
                    return;
                }

                // Display countdown message in the center of the screen
                player.sendTitle("", ChatColor.YELLOW + "Teleporting in " + countdown + " seconds", 0, 20, 0);
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