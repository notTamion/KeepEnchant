package de.tamion;

import de.tamion.listeners.InventoryListeners;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class KeepEnchant extends JavaPlugin {

    private static KeepEnchant plugin;
    @Override
    public void onEnable() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        plugin = this;

        pluginManager.registerEvents(new InventoryListeners(), this);

        Metrics metrics = new Metrics(this, 19000);
    }

    public static KeepEnchant getPlugin() {
        return plugin;
    }
}
