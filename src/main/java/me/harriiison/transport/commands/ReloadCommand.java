package me.harriiison.transport.commands;

import me.harriiison.transport.WizryTransport;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

    private WizryTransport plugin;
    public ReloadCommand(WizryTransport instance) {
        this.plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("transport")) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload") && sender.hasPermission("wizry.admin")) {
                    plugin.reloadConfig();
                    plugin.getTransportManager().loadTransports();
                    plugin.getLocationManager().loadLocations();
                    sender.sendMessage(ChatColor.RED + "The Wizry Transport config has been reloaded.");
                }
            }
        }
        return true;
    }
}
