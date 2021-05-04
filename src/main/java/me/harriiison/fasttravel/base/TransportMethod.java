package me.harriiison.fasttravel.base;

import org.bukkit.Location;

public class TransportMethod {

    private String id;
    private String name;
    private Location location;
    private int npcID;

    TransportMethod(String id, String name, Location location, int npcID) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.npcID = npcID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getNpcID() {
        return npcID;
    }

    public void setNpcID(int npcID) {
        this.npcID = npcID;
    }
}
