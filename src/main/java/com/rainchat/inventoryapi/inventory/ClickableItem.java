package com.rainchat.inventoryapi.inventory;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class ClickableItem {

    private ItemStack item;
    private Consumer<InventoryClickEvent> click;

    private ClickableItem(ItemStack item, Consumer<InventoryClickEvent> click) {
        this.item = item;
        this.click = click;
    }

    public ClickableItem() {
    }

    public static ClickableItem of(ItemStack item, Consumer<InventoryClickEvent> click) {
        return new ClickableItem(item, click);
    }

    public static ClickableItem empty(ItemStack item) {
        return of(item, (event -> {
        }));
    }

    public ItemStack getItem() {
        return this.item;
    }

    public void setClick(Consumer<InventoryClickEvent> click) {
        this.click = click;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public Consumer<InventoryClickEvent> getClick() {
        return this.click;
    }
}
