package com.doa.RMultiSpawn;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    Main plugin = this;

    @Override
    public void onEnable() {
        // Save default configuration file if it doesn't exist
        plugin.saveDefaultConfig();
        
        // Registering commands and event listener
        this.getCommand("spawn").setExecutor(new Commands(this));
        this.getCommand("setspawn").setExecutor(new Commands(this));
        this.getCommand("delspawn").setExecutor(new Commands(this));
        Bukkit.getPluginManager().registerEvents(new OnJoin(this), this);
    }
    
    // Method to handle player spawning
    public void SpawnControl(Player player) {
        Location teleport;
        Random rand = new Random();
        
        // Get all keys from the config
        Set<String> configKeys = plugin.getConfig().getKeys(false);
        
        // Convert the set of keys to an ArrayList for easy manipulation
        ArrayList<String> configPaths = new ArrayList<>(configKeys);
        
        // Generate a random index to select a spawn location
        int maxSum = configPaths.size();
        int intKey = rand.nextInt(maxSum);
        String stringKey = configPaths.get(intKey);
        
        // Get the location associated with the selected key
        teleport = plugin.getConfig().getLocation(stringKey);
        
        // Teleport the player to the selected location
        player.teleport(teleport);
        
        // Clear the list of paths
        configPaths.clear();
    }
}
