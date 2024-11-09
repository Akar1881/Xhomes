package com.xhomes;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;

public class DelHomeCommand implements CommandExecutor {
    private final HomeManager homeManager;

    public DelHomeCommand(HomeManager homeManager) {
        this.homeManager = homeManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can delete homes.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 1) {
            player.sendMessage("Usage: /delhome [homename]");
            return true;
        }

        String homeName = args[0];
        homeManager.removeHome(player.getName(), homeName);
        player.sendMessage("Home '" + homeName + "' deleted.");
        return true;
    }
}