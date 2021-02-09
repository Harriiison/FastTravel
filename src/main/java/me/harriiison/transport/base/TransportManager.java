package me.harriiison.transport.base;

import me.harriiison.transport.WizryTransport;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class TransportManager {

    private WizryTransport plugin;
    private List<TransportMethod> transports;

    public TransportManager(WizryTransport instance) {
        this.plugin = instance;
    }

    public List<TransportMethod> getTransports() {
        return transports;
    }

    public void loadTransports() {
        if (transports == null) transports = new ArrayList<>();
        if (transports.size() > 0) transports.clear();

        plugin.getConfig().getConfigurationSection("transports").getKeys(false).forEach(key -> {
            String name = plugin.getConfig().getString("transports." + key + ".name");

            // Location Formatting
            World world = Bukkit.getWorld(plugin.getConfig().getString("transports." + key + ".world", ""));
            double xVal = plugin.getConfig().getDouble("transports." + key + ".x", Double.NaN);
            double yVal = plugin.getConfig().getDouble("transports." + key + ".y", Double.NaN);
            double zVal = plugin.getConfig().getDouble("transports." + key + ".z", Double.NaN);

            Location location;
            if (world == null || Double.isNaN(xVal) || Double.isNaN(yVal) ||Double.isNaN(zVal)) {
                 location = null;
            } else {
                location = new Location(world, xVal, yVal, zVal);
            }

            int npcID = plugin.getConfig().getInt("transports." + key + ".npc", -1);

            TransportMethod transport = new TransportMethod(key, name, location, npcID);
            transports.add(transport);

            System.out.println("Loaded Transport Method: " + transport.getName());
        });
    }

    public TransportMethod getTransport(String id) {
        for (TransportMethod transport : transports) {
            if (transport.getId().equalsIgnoreCase(id)) return transport;
        }

        return null;
    }

}
