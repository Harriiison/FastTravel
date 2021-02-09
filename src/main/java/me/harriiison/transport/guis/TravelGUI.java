package me.harriiison.transport.guis;

import me.harriiison.transport.WizryTransport;
import me.harriiison.transport.base.TransportMethod;
import me.harriiison.transport.base.WarpLocation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.tags.ItemTagType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TravelGUI {

    private WizryTransport plugin;
    public TravelGUI(WizryTransport instance) {
        this.plugin = instance;
    }

    public int resizeInventory(Collection<?> collection) {
        int resize = 9;
        int size = collection.size();

        if (size > 9) resize = 18;
        if (size > 18) resize = 27;
        if (size > 27) resize = 36;
        if (size > 36) resize = 45;
        if (size > 45) resize = 54;

        return resize;
    }

    public Inventory createTravelGUI(Player player, String transportName, List<WarpLocation> destinations) {
        Inventory inv = Bukkit.createInventory(player, resizeInventory(destinations), transportName);

        ItemStack barrier = new ItemStack(Material.BARRIER);
        ItemMeta bMeta = barrier.getItemMeta();

        if (destinations.size() == 0) {
            bMeta.setDisplayName(ChatColor.RED + "No Unlocked Locations");

            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.GOLD + "Explore to discover more locations!");
            bMeta.setLore(lore);
        } else {
            bMeta.setDisplayName(ChatColor.RED + "Close");
        }

        barrier.setItemMeta(bMeta);
        inv.addItem(barrier);

        destinations.forEach(location -> {
            ItemStack locItem = new ItemStack(Material.COMPASS, 1);
            ItemMeta locMeta = locItem.getItemMeta();

            locMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', location.getName()));
            locMeta.setUnbreakable(true);
            locMeta.addEnchant(Enchantment.DURABILITY, 1, true);
            locMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);

            // Add location tag (1.13.2)
            NamespacedKey key = new NamespacedKey(plugin, "transport");
            locMeta.getCustomTagContainer().setCustomTag(key, ItemTagType.STRING, location.getID());

            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.GREEN + "Click to travel to this location.");
            locMeta.setLore(lore);

            locItem.setItemMeta(locMeta);

            inv.addItem(locItem);
        });
        
        return inv;
    }

    public void openTravelGUI(Player player, Inventory inv) {
        player.openInventory(inv);
    }
}
