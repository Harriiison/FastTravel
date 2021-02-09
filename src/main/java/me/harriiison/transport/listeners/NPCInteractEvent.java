package me.harriiison.transport.listeners;

import me.harriiison.transport.WizryTransport;
import me.harriiison.transport.base.TransportMethod;
import me.harriiison.transport.base.WarpLocation;
import me.harriiison.transport.guis.TravelGUI;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class NPCInteractEvent implements Listener {

    private WizryTransport plugin;

    public NPCInteractEvent(WizryTransport instance) {
        this.plugin = instance;
    }

    @EventHandler
    public void onNPCInteract(NPCRightClickEvent event) {
        NPC npc = event.getNPC();
        WarpLocation location = plugin.getLocationManager().fetchLocationFromNPCID(npc.getId());

        if (location == null) return;

        Player player = event.getClicker();
        List<WarpLocation> destinations = new ArrayList<>();

        location.getAllowedLocations().forEach(loc -> {
            WarpLocation destination = plugin.getLocationManager().getLocation(loc);
            if (player.hasPermission(destination.getPermission()) || player.hasPermission("wizry.admin")) {
                destinations.add(destination);
            }
        });

        TransportMethod transport = plugin.getTransportManager().getTransport(location.getTransport());
        if (transport == null) return;

        TravelGUI gui = new TravelGUI(plugin);
        gui.openTravelGUI(player, gui.createTravelGUI(player, transport.getName(), destinations));
    }
}
