package de.tamion.listeners;

import de.tamion.KeepEnchant;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

public class InventoryListeners implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        FileConfiguration config = KeepEnchant.getPlugin().getConfig();
        Player p = (Player) e.getWhoClicked();

        if(!config.contains("player." + p.getName())) {
            if(!config.getBoolean("default")) {
                return;
            }
        } else if(!config.getBoolean("player." + p.getName())) {
            return;
        }
        try {
            if (!e.getInventory().getType().equals(InventoryType.GRINDSTONE) || e.getCurrentItem() == null || e.getCurrentItem().getType().isAir() || e.getSlot() != 2) {
                return;
            }
            if(config.getBoolean("requirebook")) {
                if(!p.getInventory().contains(Material.BOOK)) {
                    return;
                }
                ItemStack books = p.getInventory().getItem(p.getInventory().first(Material.BOOK));
                books.setAmount(books.getAmount()-1);
            }
            ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
            EnchantmentStorageMeta bookmeta = (EnchantmentStorageMeta) book.getItemMeta();
            if (!e.getView().getItem(0).getType().isAir()) {
                e.getView().getItem(0).getEnchantments().forEach((enchantment, integer) -> {
                    bookmeta.addStoredEnchant(enchantment, integer, true);
                });
            }
            if (!e.getView().getItem(1).getType().isAir()) {
                e.getView().getItem(1).getEnchantments().forEach((enchantment, integer) -> {
                    bookmeta.addStoredEnchant(enchantment, integer, true);
                });
            }
            if(bookmeta.getStoredEnchants().isEmpty()) {
                return;
            }
            book.setItemMeta(bookmeta);
            if (p.getInventory().firstEmpty() == -1) {
                if(!e.isShiftClick()) {
                    p.getWorld().dropItem(p.getLocation(), book);
                }
                return;
            }
            Inventory playinv = Bukkit.createInventory(null,  4*9);
            playinv.setContents(p.getInventory().getStorageContents());
            playinv.addItem(book);
            if(playinv.firstEmpty() == -1 && e.isShiftClick()) {
                p.getWorld().dropItem(p.getLocation(), book);
                return;
            }
            p.getInventory().addItem(book);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
