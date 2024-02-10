package com.doa.RMultiSpawn;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
    // Reference to the main class
    private final Main main;

    // Constructor to pass the main class reference
    public Commands(Main main) {
        this.main = main;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Main myPlugin = main;

        // Get player and player location
        Player player = (Player) sender;
        Location loc = player.getLocation();

        // Check if the sender is a player
        if (!(sender instanceof Player)) {
            Bukkit.getLogger().info("You must be in-game to execute this command!");
            return false;
        }

        // Handle /spawn command
        if (cmd.getName().equalsIgnoreCase("spawn")) {
            myPlugin.SpawnControl(player);
            return true;
        }

        // Handle /setspawn command
        if (cmd.getName().equalsIgnoreCase("setspawn")) {
            // Check if spawn name is provided
            if (args.length < 1) {
                sender.sendMessage("You must include a spawn name");
                return false;
            }
            String spawnName = args[0];

            // Save spawn location to configuration
            myPlugin.getConfig().set(spawnName, loc);
            myPlugin.saveConfig();
            return true;
        }

        // Handle /delspawn command
        if (cmd.getName().equalsIgnoreCase("delspawn")) {
            // Check if spawn name is provided
            if (args.length < 1) {
                sender.sendMessage("You must specify the name of the spawn to delete");
                return false;
            }
            String spawnName = args[0];

            // Check if spawn exists and delete it
            if (myPlugin.getConfig().contains(spawnName)) {
                myPlugin.getConfig().set(spawnName, null);
                myPlugin.saveConfig();
                return true;
            } else {
                sender.sendMessage("This is not a correct spawn name, please check your command and try again!");
                return false;
            }
        }

        return true;
    }
}
