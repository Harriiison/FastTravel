package me.harriiison.fasttravel.listeners;

import me.harriiison.fasttravel.FastTravel;
import me.harriiison.fasttravel.base.TransportMethod;
import me.harriiison.fasttravel.base.WarpLocation;
import me.harriiison.fasttravel.guis.TravelGUI;
import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class NPCInteractEvent implements Listener {

    private FastTravel plugin;

    public NPCInteractEvent(FastTravel instance) {
        this.plugin = instance;
    }

    @EventHandler
    public void onNPCRightClick(NPCRightClickEvent event) {
        openTravelOptionsGUI(event.getNPC(), event.getClicker());
    }

    @EventHandler
    public void onNPCLeftClick(NPCLeftClickEvent event) {
        openTravelOptionsGUI(event.getNPC(), event.getClicker());
    }

    private void openTravelOptionsGUI(NPC npc, Player player) {
        WarpLocation location = plugin.getLocationManager().fetchLocationFromNPCID(npc.getId());
        if (location == null) return;

        List<WarpLocation> destinations = new ArrayList<>();
        location.getAllowedLocations().forEach(loc -> {
            WarpLocation destination = plugin.getLocationManager().getLocation(loc);
            // TODO error here that is null? is the destination null?
            if (player.hasPermission(destination.getPermission()) || player.hasPermission("fasttravel.admin")) {
                destinations.add(destination);
            }
        });

        TransportMethod transport = plugin.getTransportManager().getTransport(location.getTransport());
        if (transport == null) return;

        TravelGUI gui = new TravelGUI(plugin);
        gui.openTravelGUI(player, gui.createTravelGUI(player, transport.getName(), destinations));
    }
}
