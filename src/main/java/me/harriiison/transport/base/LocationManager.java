package me.harriiison.transport.base;

import me.harriiison.transport.WizryTransport;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class LocationManager {

    private WizryTransport plugin;
    private List<WarpLocation> locations;

    public LocationManager(WizryTransport instance) {
        this.plugin = instance;
    }

    public List<WarpLocation> getLocations() {
        return locations;
    }

    public void loadLocations() {
        if (locations == null) locations = new ArrayList<>();
        if (locations.size() > 0) locations.clear();

        plugin.getConfig().getConfigurationSection("locations").getKeys(false).forEach(key -> {
            String name = plugin.getConfig().getString("locations." + key + ".name");

            // Location Formatting
            World world = Bukkit.getWorld(plugin.getConfig().getString("locations." + key + ".world"));
            double xVal = plugin.getConfig().getDouble("locations." + key + ".x");
            double yVal = plugin.getConfig().getDouble("locations." + key + ".y");
            double zVal = plugin.getConfig().getDouble("locations." + key + ".z");

            Location location = new Location(world, xVal, yVal, zVal);

            int npcID = plugin.getConfig().getInt("locations." + key + ".npc");
            String transport = plugin.getConfig().getString("locations." + key + ".transport");
            int time = plugin.getConfig().getInt("locations." + key + ".time");
            String permission = plugin.getConfig().getString("locations." + key + ".permission");
            List<String> allowedLocations = plugin.getConfig().getStringList("locations." + key + ".allowedLocations");
            List<String> dialogue = plugin.getConfig().getStringList("locations." + key + ".dialogue");

            WarpLocation loc = new WarpLocation(plugin, key, name, location, npcID, transport, time, permission, allowedLocations, dialogue);
            locations.add(loc);

            System.out.println("Loaded location: " + loc.getName());
        });
    }

    public WarpLocation getLocation(String id) {
        for (WarpLocation loc : locations) {
            if (loc.getID().equalsIgnoreCase(id)) return loc;
        }

        return null;
    }

    public WarpLocation fetchLocationFromNPCID(int id) {
        for (WarpLocation loc : locations) {
            if (loc.getNpcID() == id) return loc;
        }

        return null;
    }
}
