package me.harriiison.fasttravel.listeners;

import me.harriiison.fasttravel.FastTravel;
import me.harriiison.fasttravel.base.TransportMethod;
import me.harriiison.fasttravel.base.WarpLocation;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class InventoryListener implements Listener {

    private FastTravel plugin;
    public InventoryListener(FastTravel instance) {
        this.plugin = instance;
    }

    @EventHandler
    public void onGUIClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack locItem = event.getCurrentItem();

        if (locItem == null) {
            return;
        }

        // Check if it is a transport GUI by searching if the inventory name is a transport method
        TransportMethod transport = null;
        for (TransportMethod tMethod : plugin.getTransportManager().getTransports()) {
            if (event.getView().getTitle().contains(tMethod.getName())) {
                transport = tMethod;
                break;
            }
        }

        if (transport == null) return;
        event.setCancelled(true);

        if (locItem.getType().equals(Material.BARRIER)) {
            event.setCancelled(true);
            if (!locItem.hasItemMeta()) return;

            if (locItem.getItemMeta().getDisplayName().contains("Close")) {
                player.closeInventory();
                player.updateInventory();
                return;
            }
        }

        if (!locItem.getType().equals(Material.COMPASS)) return;

        if (!locItem.hasItemMeta()) return;
        ItemMeta locMeta = locItem.getItemMeta();

        // Fetch location tab
        NamespacedKey key = new NamespacedKey(plugin, "locationID");
        PersistentDataContainer tagContainer = locMeta.getPersistentDataContainer();;

        // Not a valid location item
        if (!tagContainer.has(key, PersistentDataType.STRING)) {
            return;
        }
        String locationID = tagContainer.get(key, PersistentDataType.STRING);

        WarpLocation location =  plugin.getLocationManager().getLocation(locationID);
        if (location == null) return;

        player.closeInventory();
        player.updateInventory();

        // Check if it is instant travel or an invalid in-transit location or invalid in-transit NPC
        if (location.getTime() == 0 || transport.getLocation() == null || transport.getNpcID() == -1) {
            teleportPlayer(player, location.getLocation());
        } else {
            Location transitLocation = transport.getLocation();
            int travelTime = location.getTime();
            String npcName = CitizensAPI.getNPCRegistry().getById(transport.getNpcID()).getName();
            Location finalLocation = location.getLocation();

            // Teleport to Build
            player.teleport(transitLocation);

            // Play dialogue
            Random rand = new Random();
            String npcDialogue = location.getDialogue().get(rand.nextInt(location.getDialogue().size()));

            player.sendMessage(npcName + ChatColor.DARK_GREEN + ": " + ChatColor.RESET + npcDialogue);

            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                teleportPlayer(player, finalLocation);
            }, travelTime * 20);
        }
    }

    private void teleportPlayer(Player player, Location location) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getPrefix() + "&eTravelling..."));
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 1));
        player.teleport(location);
        player.playSound(location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.75f);
    }
}
