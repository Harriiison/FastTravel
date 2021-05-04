package me.harriiison.fasttravel.commands;

import me.harriiison.fasttravel.FastTravel;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {
    private FastTravel plugin;
    public ReloadCommand(FastTravel instance) {
        this.plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload") && sender.hasPermission("fasttravel.admin")) {
                plugin.reloadConfig();
                plugin.getTransportManager().loadTransports();
                plugin.getLocationManager().loadLocations();
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getPrefix() + "&cThe Fast Travel config has been reloaded."));
            }
        }
        return true;
    }
}
