package com.hakan.inventoryapi.inventory;

import com.hakan.inventoryapi.InventoryAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class HInventory implements InventoryHolder {

    private final InventoryManager inventoryManager;
    private final Inventory bukkitInventory;
    private final String title;
    private final HashMap<Integer, ClickableItem> clickableItems = new HashMap<>();
    private String id;
    private boolean closable;
    private boolean clickable;

    public HInventory(InventoryAPI inventoryAPI, String title, InventoryType inventoryType, int size, String id, boolean closable, boolean clickable) {
        this.inventoryManager = inventoryAPI.getInventoryManager();

        this.id = id;
        this.closable = closable;
        this.clickable = clickable;
        this.title = title;
        this.bukkitInventory = inventoryType.equals(InventoryType.CHEST) ? Bukkit.createInventory(this, size * 9, title) : Bukkit.createInventory(this, inventoryType, title);
    }

    public void open(Player player) {
        player.openInventory(this.bukkitInventory);
        this.inventoryManager.setPlayerInventory(player, this);
    }

    public void close(Player player) {
        this.closable = true;
        player.closeInventory();
    }

    public void guiFill(ClickableItem clickableItem) {
        for (int slot = 0; slot < this.getSize() * 9; slot++) {
            if (getItem(slot) != null) continue;
            this.setItem(slot, clickableItem);
        }
    }

    public void guiFill(ItemStack itemStack) {
        this.guiFill(ClickableItem.empty(itemStack));
    }

    public void guiAir() {
        this.guiFill(new ItemStack(Material.AIR));
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSize() {
        return this.bukkitInventory.getSize() / 9;
    }

    public String getTitle() {
        return this.title;
    }

    public InventoryType getInventoryType() {
        return this.bukkitInventory.getType();
    }

    public boolean isClosable() {
        return this.closable;
    }

    public void setClosable(boolean closeable) {
        this.closable = closeable;
    }

    public boolean isClickable() {
        return this.clickable;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    public ClickableItem getItem(int slot) {
        return this.clickableItems.getOrDefault(slot, null);
    }

    public void setItem(int slot, ClickableItem clickableItem) {
        if (clickableItem.getItem() == null || clickableItem.getItem().getType().equals(Material.AIR)) {
            this.clickableItems.put(slot, ClickableItem.empty(new ItemStack(Material.AIR)));
            this.bukkitInventory.setItem(slot, clickableItem.getItem());
            return;
        }
        this.clickableItems.put(slot, clickableItem);
        this.bukkitInventory.setItem(slot, clickableItem.getItem());
    }

    public Pagination getPagination() {
        return new Pagination(this);
    }

    @Override
    public Inventory getInventory() {
        return this.bukkitInventory;
    }

    @Override
    public HInventory clone() {
        try {
            return (HInventory) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}