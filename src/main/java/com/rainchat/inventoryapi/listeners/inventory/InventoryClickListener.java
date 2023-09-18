package com.rainchat.inventoryapi.listeners.inventory;

import com.rainchat.inventoryapi.InventoryAPI;
import com.rainchat.inventoryapi.customevents.HInventoryClickEvent;
import com.rainchat.inventoryapi.inventory.ClickableItem;
import com.rainchat.inventoryapi.inventory.HInventory;
import com.rainchat.inventoryapi.listeners.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

public class InventoryClickListener extends ListenerAdapter {

    public InventoryClickListener(InventoryAPI inventoryAPI) {
        super(inventoryAPI);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClick().equals(ClickType.UNKNOWN) || event.getClickedInventory() == null) {
            event.setCancelled(true);
            return;
        }

        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            HInventory hInventory = this.inventoryManager.getPlayerInventory(player);

            if (hInventory == null) {
                return;
            } else if (!hInventory.isClickable() && event.getClickedInventory().equals(player.getInventory())) {
                event.setCancelled(true);
                return;
            } else if (hInventory.isClickable() && event.getClickedInventory().equals(player.getInventory())) {
                return;
            }

            HInventoryClickEvent hInventoryClickEvent = new HInventoryClickEvent(player, hInventory, event);
            Bukkit.getPluginManager().callEvent(hInventoryClickEvent);
            if (hInventoryClickEvent.isCancelled()) {
                event.setCancelled(true);
                return;
            }

            ClickableItem clickableItem = hInventory.getItem(event.getSlot());
            if (clickableItem != null) {
                event.setCancelled(true);
                Consumer<InventoryClickEvent> clickEventConsumer = clickableItem.getClick();
                if (clickEventConsumer != null) {
                    clickEventConsumer.accept(event);
                }
            }
        }
    }
}
