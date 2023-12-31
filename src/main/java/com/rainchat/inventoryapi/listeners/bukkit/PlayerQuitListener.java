package com.rainchat.inventoryapi.listeners.bukkit;

import com.rainchat.inventoryapi.InventoryAPI;
import com.rainchat.inventoryapi.inventory.HInventory;
import com.rainchat.inventoryapi.listeners.ListenerAdapter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener extends ListenerAdapter {

    public PlayerQuitListener(InventoryAPI inventoryAPI) {
        super(inventoryAPI);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        HInventory hInventory = this.inventoryManager.getPlayerInventory(player);

        if (hInventory != null) {
            this.inventoryManager.getPlayerInventoryMap().remove(player.getName());
        }
    }
}
