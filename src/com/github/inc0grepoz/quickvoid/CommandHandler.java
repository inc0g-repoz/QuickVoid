package com.github.inc0grepoz.quickvoid;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class CommandHandler implements CommandExecutor, TabCompleter {

    private final QuickVoid plugin;

    public CommandHandler(QuickVoid plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command,
            String label, String[] args) {
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("create")) {
                sender.sendMessage("Creating a void world named '" + args[1] + "'...");
                sender.sendMessage(plugin.createVoidWorld(args[1])
                        ? "A world has been successfully created"
                        : ChatColor.RED + "Failed to create a void world");
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command,
            String label, String[] args) {
        if (args.length == 1) {
            List<String> list = new ArrayList<>();
            list.add("create");
            return list;
        } else {
            return null;
        }
    }

}
