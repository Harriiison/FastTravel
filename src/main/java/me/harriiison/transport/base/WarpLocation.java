package me.harriiison.transport.base;

import me.harriiison.transport.WizryTransport;
import org.bukkit.Location;

import java.util.List;

public class WarpLocation {

    private String id;
    private String name;
    private Location location;
    private int npcID;
    private String transport;
    private int time;
    private String permission;
    private List<String> allowedLocations;
    private List<String> dialogue;

    public WarpLocation(WizryTransport plugin, String id, String name, Location location, int npcID, String transport, int time, String permission, List<String> allowedLocations, List<String> dialogue) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.npcID = npcID;
        this.transport = transport;
        this.time = time;
        this.permission = permission;
        this.allowedLocations = allowedLocations;
        this.dialogue = dialogue;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
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

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public List<String> getAllowedLocations() {
        return allowedLocations;
    }

    public void setAllowedLocations(List<String> allowedLocations) {
        this.allowedLocations = allowedLocations;
    }

    public List<String> getDialogue() {
        return dialogue;
    }

    public void setDialogue(List<String> dialogue) {
        this.dialogue = dialogue;
    }
}
