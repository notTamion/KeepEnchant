package de.tamion.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class KeepEnchantCompleter implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        List<String> completes = new ArrayList<>();
        switch (args.length) {
            case 1:
                if(sender.hasPermission("keepenchant.toggle")) {
                    completes.add("toggle");
                }
                break;
            case 2:
                if(sender.hasPermission("keepenchant.toggle.default")) {
                    completes.add("default");
                }
                if(sender.hasPermission("keepenchant.toggle.requirebook")) {
                    completes.add("requirebook");
                }
                break;
        }
        return completes;
    }
}
