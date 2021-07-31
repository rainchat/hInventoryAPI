package com.hakan.inventoryapi;

import com.hakan.inventoryapi.inventory.HInventory;
import com.hakan.inventoryapi.inventory.InventoryManager;
import com.hakan.inventoryapi.listeners.bukkit.PlayerQuitListener;
import com.hakan.inventoryapi.listeners.bukkit.PluginDisableListener;
import com.hakan.inventoryapi.listeners.inventory.InventoryClickListener;
import com.hakan.inventoryapi.listeners.inventory.InventoryCloseListener;
import com.hakan.inventoryapi.listeners.inventory.InventoryOpenListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryType;
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

    public static class InventoryCreator {

        private final InventoryAPI inventoryAPI;

        private String title = "";
        private InventoryType inventoryType = InventoryType.CHEST;
        private int size = 6;
        private boolean closable = true;
        private String id = "";
        private boolean clickable = false;

        private InventoryCreator(InventoryAPI inventoryAPI) {
            this.inventoryAPI = inventoryAPI;
        }

        public InventoryCreator setTitle(String title) {
            this.title = title;
            return this;
        }

        public InventoryCreator setSize(int size) {
            this.size = size;
            return this;
        }

        public InventoryCreator setId(String id) {
            this.id = id;
            return this;
        }

        public InventoryCreator setClosable(boolean closable) {
            this.closable = closable;
            return this;
        }

        public InventoryCreator setInventoryType(InventoryType inventoryType) {
            this.inventoryType = inventoryType;
            return this;
        }

        public InventoryCreator setClickable(boolean clickable) {
            this.clickable = clickable;
            return this;
        }

        public HInventory create() {
            return new HInventory(this.inventoryAPI, ChatColor.translateAlternateColorCodes('&', this.title), this.inventoryType, this.size, this.id, this.closable, this.clickable);
        }
    }
}