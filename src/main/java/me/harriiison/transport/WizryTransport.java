package me.harriiison.transport;

import me.harriiison.transport.base.LocationManager;
import me.harriiison.transport.base.TransportManager;
import me.harriiison.transport.commands.ReloadCommand;
import me.harriiison.transport.listeners.InventoryListener;
import me.harriiison.transport.listeners.NPCInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class WizryTransport extends JavaPlugin {

    private LocationManager locationManager;
    private TransportManager transportManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        // Load Transports
        transportManager = new TransportManager(this);
        transportManager.loadTransports();

        // Load Locations
        locationManager = new LocationManager(this);
        locationManager.loadLocations();

        // Load Command
        getCommand("transport").setExecutor(new ReloadCommand(this));

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
}
