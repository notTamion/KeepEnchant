package de.tamion.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.Objects;

public class InventoryListeners implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        try {
            if (!e.getInventory().getType().equals(InventoryType.GRINDSTONE) || e.getCurrentItem() == null || e.getCurrentItem().getType().isAir() || !Objects.equals(e.getCurrentItem(), e.getView().getItem(2))) {
                return;
            }
            Player p = (Player) e.getWhoClicked();
            ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
            EnchantmentStorageMeta bookmeta = (EnchantmentStorageMeta) book.getItemMeta();
            int originalitemslot = 0;
            if (e.getView().getItem(0).getType().isAir()) {
                originalitemslot = 1;
            }
            e.getView().getItem(originalitemslot).getEnchantments().forEach((enchantment, integer) -> {
                bookmeta.addStoredEnchant(enchantment, integer, true);
            });
            book.setItemMeta(bookmeta);
            if (p.getInventory().firstEmpty() == -1) {
                p.getWorld().dropItem(p.getLocation(), book);
                return;
            }
            p.getInventory().addItem(book);
        } catch (Exception ignored) {}
    }
}
