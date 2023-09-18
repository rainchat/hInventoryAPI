package com.rainchat.inventoryapi.listeners.bukkit;

import com.rainchat.inventoryapi.InventoryAPI;
import com.rainchat.inventoryapi.inventory.HInventory;
import com.rainchat.inventoryapi.listeners.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.PluginDisableEvent;

import java.util.Map;

public class PluginDisableListener extends ListenerAdapter {

    public PluginDisableListener(InventoryAPI inventoryAPI) {
        super(inventoryAPI);
    }

    @EventHandler
    public void onPluginDisable(PluginDisableEvent event) {
        if (event.getPlugin().equals(plugin)) {
            if (this.inventoryManager.getPlayerInventoryMap() == null || this.inventoryManager.getPlayerInventoryMap().isEmpty()) {
                return;
            }

            for (Map.Entry<String, HInventory> entry : this.inventoryManager.getPlayerInventoryMap().entrySet()) {
                Player player = Bukkit.getPlayerExact(entry.getKey());
                HInventory hInventory = entry.getValue();
                if (player == null) {
                    return;
                }
                if (hInventory != null && this.inventoryManager.getPlayerInventory(player) != null) {
                    hInventory.close(player);
                }
            }
        }
    }
}