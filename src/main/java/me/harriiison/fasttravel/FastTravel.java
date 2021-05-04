package me.harriiison.fasttravel;

import me.harriiison.fasttravel.base.LocationManager;
import me.harriiison.fasttravel.base.TransportManager;
import me.harriiison.fasttravel.commands.ReloadCommand;
import me.harriiison.fasttravel.listeners.InventoryListener;
import me.harriiison.fasttravel.listeners.NPCInteractEvent;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class FastTravel extends JavaPlugin {

    private LocationManager locationManager;
    private TransportManager transportManager;

    private String prefix;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        // Load Prefix
        setPrefix();

        // Load Transports
        transportManager = new TransportManager(this);
        transportManager.loadTransports();

        // Load Locations
        locationManager = new LocationManager(this);
        locationManager.loadLocations();

        // Load Command
        getCommand("fasttravel").setExecutor(new ReloadCommand(this));

        // Load Listeners
        getServer().getPluginManager().registerEvents(new NPCInteractEvent(this), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(this), this);
    }

    public LocationManager getLocationManager() {
        return locationManager;
    }

    public TransportManager getTransportManager() {
        return transportManager;
    }

    private void setPrefix() {
        prefix = ChatColor.translateAlternateColorCodes('&', getConfig().getString("chatPrefix", "&f[&2Fast Travel&f] "));
    }

    public String getPrefix() {
        return prefix;
    }
}
