package com.doa.RMultiSpawn;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Commands implements CommandExecutor {
    // Reference to the main class
    private final Main main;

    // Constructor to pass the main class reference
    public Commands(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Check if the sender is a player
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be in-game to execute this command!");
            return false;
        }

        Player player = (Player) sender;

        // Handle /spawn command
        if (cmd.getName().equalsIgnoreCase("spawn")) {
            if (args.length < 1) {
                // Handle /spawn command without arguments
                if (!main.perms.has(player, "RMultiSpawn.bypass")) {
                	sender.sendMessage("Teleporting in 3s.");
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            main.spawnPlayer(player);
                        }
                    }.runTaskLater(main, 60L);
                    return true;
                } else {
                    main.spawnPlayer(player);
                    return true;
                }
            } else {
            	if (!main.getConfig().contains(args[0])) {
            		sender.sendMessage("The specified spawn does not exist.");
            		return true;
            	}
                if (!main.perms.has(player, "RMultiSpawn.bypass")) {
                    player.sendMessage("You must have the RMultiSpawn.bypass permission to select a specific spawn.");
                    return true;
                }
                
                String spawnName = args[0];
                Location spawnLocation = main.getConfig().getLocation(spawnName);
                if (spawnLocation != null) {
                    player.teleport(spawnLocation);
                    return true;
                } else {
                    player.sendMessage("Exception occured. Please try again later, if the error persists please reach out to your servers administration.");
                    return true;
                }
            }
        }

        // Handle /setspawn command
        if (cmd.getName().equalsIgnoreCase("setspawn")) {
            if (args.length < 1) {
                sender.sendMessage("You must include a spawn name");
                return false;
            }
            String spawnName = args[0];
            main.getConfig().set(spawnName, player.getLocation());
            main.saveConfig();
            return true;
        }

        // Handle /delspawn command
        if (cmd.getName().equalsIgnoreCase("delspawn")) {
            if (args.length < 1) {
                sender.sendMessage("You must specify the name of the spawn to delete.");
                return false;
            }
            String spawnName = args[0];
            if (main.getConfig().contains(spawnName)) {
                main.getConfig().set(spawnName, null);
                main.saveConfig();
                return true;
            } else {
                sender.sendMessage("The specified spawn does not exist.");
                return true;
            }
        }

        return true;
    }
}

