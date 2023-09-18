package com.rainchat.inventoryapi;

import com.rainchat.inventoryapi.inventory.InventoryManager;
import com.rainchat.inventoryapi.listeners.bukkit.PlayerQuitListener;
import com.rainchat.inventoryapi.listeners.bukkit.PluginDisableListener;
import com.rainchat.inventoryapi.listeners.inventory.InventoryClickListener;
import com.rainchat.inventoryapi.listeners.inventory.InventoryCloseListener;
import com.rainchat.inventoryapi.listeners.inventory.InventoryOpenListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class InventoryAPI {

    private static boolean isFirst = true;
    private static InventoryAPI instance;

    private final Plugin plugin;
    private final InventoryManager inventoryManager;

    private InventoryAPI(Plugin plugin) {
        InventoryAPI.instance = this;

        this.plugin = plugin;
        this.inventoryManager = new InventoryManager(plugin);

        if (isFirst) {
            isFirst = false;

            PluginManager pm = Bukkit.getPluginManager();
            pm.registerEvents(new PlayerQuitListener(this), plugin);
            pm.registerEvents(new PluginDisableListener(this), plugin);
            pm.registerEvents(new InventoryClickListener(this), plugin);
            pm.registerEvents(new InventoryCloseListener(this), plugin);
            pm.registerEvents(new InventoryOpenListener(this), plugin);
        }
    }

    public static InventoryAPI getInstance(Plugin plugin) {
        return isFirst ? new InventoryAPI(plugin) : InventoryAPI.instance;
    }

    public Plugin getPlugin() {
        return this.plugin;
    }

    public InventoryManager getInventoryManager() {
        return this.inventoryManager;
    }

    public InventoryCreator getInventoryCreator() {
        return new InventoryCreator(this);
    }
}