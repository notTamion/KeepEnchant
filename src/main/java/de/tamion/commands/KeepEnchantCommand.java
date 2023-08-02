package de.tamion.commands;

import de.tamion.KeepEnchant;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class KeepEnchantCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        FileConfiguration config = KeepEnchant.getPlugin().getConfig();
        switch (args.length) {
            case 0:
                if(!(sender instanceof Player)) {
                    sender.sendMessage("You must be a player to execute this command!");
                    return false;
                }
                Player p = (Player) sender;
                if(!p.hasPermission("keepenchant.toggle")) {
                    p.sendMessage("You aren't allowed to execute this command!");
                    return false;
                }
                boolean newstate = false;
                if(config.contains("player." + p.getName())) {
                    newstate = !config.getBoolean("player." + p.getName());
                } else {
                    newstate = !config.getBoolean("default");
                }
                config.set("player." + p.getName(), newstate);
                KeepEnchant.getPlugin().saveConfig();
                if(newstate) {
                    p.sendMessage("You will now keep your enchantments when using the grindstone");
                } else {
                    p.sendMessage("You will no longer keep your enchantments when using the grindstone");
                }
                return false;
            case 1:
                switch (args[0].toLowerCase()) {
                    case "toggledefault":
                        if(!sender.hasPermission("keepenchant.toggledefault")) {
                            sender.sendMessage("You aren't allowed to execute this command!");
                            return false;
                        }
                        newstate = !config.getBoolean("default");
                        config.set("default", newstate);
                        KeepEnchant.getPlugin().saveConfig();
                        if(newstate) {
                            sender.sendMessage("The default is now set to keep enchantments when using the grindstone");
                        } else {
                            sender.sendMessage("The default is now set to not keep enchantments when using the grindstone");
                        }
                        return false;
                    default:
                        Player t = Bukkit.getPlayer(args[0]);
                        if(t == null) {
                            sender.sendMessage("Invalid arguments");
                            return false;
                        }
                        if(!sender.hasPermission("keepenchant.toggleothers")) {
                            sender.sendMessage("You aren't allowed to execute this command!");
                            return false;
                        }
                        newstate = false;
                        if(config.contains("player." + t.getName())) {
                            newstate = !config.getBoolean("player." + t.getName());
                        } else {
                            newstate = !config.getBoolean("default");
                        }
                        config.set("player." + t.getName(), newstate);
                        KeepEnchant.getPlugin().saveConfig();
                        if(newstate) {
                            t.sendMessage("You will now keep your enchantments when using the grindstone");
                            sender.sendMessage(t.getName() + " will now keep their enchantments when using the grindstone");
                        } else {
                            t.sendMessage("You will no longer keep your enchantments when using the grindstone");
                            sender.sendMessage(t.getName() + " will no longer keep their enchantments when using the grindstone");
                        }
                }
        }
        return false;
    }
}
