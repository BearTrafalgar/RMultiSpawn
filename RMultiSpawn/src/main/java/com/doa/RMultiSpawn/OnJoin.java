package com.doa.RMultiSpawn;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnJoin implements Listener {
    // Reference to the main class
    private final Main main;

    // Constructor to pass the main class reference
    public OnJoin(Main main) {
        this.main = main;
    }

    // Event handler for player join event
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Get the player who joined
        Player player = event.getPlayer();
        
        // Check if the player is joining for the first time
        if (!player.hasPlayedBefore()) {
            // If it's the first time, spawn the player
            main.SpawnControl(player);
        }
    }
}
