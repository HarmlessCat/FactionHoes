package net.invasionpvp.harmlesscat.gui.upgradegui;

import de.tr7zw.nbtapi.NBTItem;
import me.clip.placeholderapi.PlaceholderAPI;
import net.invasionpvp.harmlesscat.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Objects;

public class MakeItems {

    public String title = "null";
    public String[] lore = new String[] {"null"};
    public Material material = Material.DIRT;

    public String getTitle() { return title; }
    public String[] getLore() { return lore; }
    public Material getMaterial() { return material; }

    public ItemStack getItem(MakeItems getItems, Player player) {
        ItemStack val = new ItemStack(Objects.requireNonNull(getItems.getMaterial()), 1);
        ItemMeta valMeta = val.getItemMeta();
        valMeta.setDisplayName(PlaceholderAPI.setPlaceholders(player, getItems.getTitle()));
        valMeta.setLore(PlaceholderAPI.setPlaceholders(player, Arrays.asList(Utils.col(getItems.getLore()))));
        val.setItemMeta(valMeta);
        NBTItem nbt = new NBTItem(val);
        nbt.setBoolean("guiitem",true);
        return nbt.getItem();
    }
}
