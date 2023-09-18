package com.rainchat.inventoryapi.listeners;

import com.rainchat.inventoryapi.InventoryAPI;
import com.rainchat.inventoryapi.inventory.InventoryManager;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class ListenerAdapter implements Listener {

    protected final Plugin plugin;
    protected final InventoryManager inventoryManager;

    public ListenerAdapter(InventoryAPI inventoryAPI) {
        this.plugin = inventoryAPI.getPlugin();
        this.inventoryManager = inventoryAPI.getInventoryManager();
    }
}