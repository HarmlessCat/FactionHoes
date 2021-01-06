package net.invasionpvp.harmlesscat.cane;

import com.massivecraft.factions.entity.Faction;
import de.tr7zw.nbtapi.NBTItem;
import net.invasionpvp.harmlesscat.FactionHandHoes;
import net.invasionpvp.harmlesscat.files.faction.itemsConfig.FactionBeacon;
import net.invasionpvp.harmlesscat.files.faction.itemsConfig.KeyItem;
import net.invasionpvp.harmlesscat.utils.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class HarvesterHoe {

    public String title;
    public String[] lore;
    public String itemType;

    public HarvesterHoe() {
        title = "&d&lHARVESTER HOE (&a&l%factionowner%&d&l)";
        lore = new String[]{
                "&7This is a &dHarvester Hoe&7. Break cane",
                "&7Break cane with it to automatically sell",
                "&7any cane broken. Upgrades can be bought",
                "&7faction wide to increase production!",
                "",
                "&d&l&nINFO",
                " &7&l»&r &fUpgrade using &d/hoe upgrade&f or",
                " &7&l»&r &fby right clicking the hoe",
                "&7This harvester hoe is owned by &a&l%factionowner%"
        };
        itemType = "DIAMOND_HOE";
    }

    public String getTitle() { return title; }
    public String[] getLore() { return lore; }
    public String getItemType() { return itemType; }

    public Material getItemMaterial() { return Material.getMaterial(getItemType()); }

    public File harvesterHoeFile;

    public ItemStack getFactionHoe(Integer amount, Faction faction) {
        ItemStack hoe = new ItemStack(Objects.requireNonNull(getItemMaterial()), amount);
        ItemMeta hoeMeta = hoe.getItemMeta();
        hoeMeta.setDisplayName(Utils.col(getTitle().replace("%factionowner%",faction.getName())));
        String[] lore = getLore();
        ArrayList<String> finalLore = new ArrayList<>();
        for (String string : lore) {
            finalLore.add(string.replace("%factionowner%",faction.getName()));
        }
        hoeMeta.setLore(Arrays.asList(Utils.col(finalLore.toArray(new String[0]))));
        hoe.setItemMeta(hoeMeta);
        NBTItem nbt = new NBTItem(hoe);
        nbt.setBoolean("hoe",true);
        nbt.setString("id","&4%2");
        nbt.setString("owner", faction.getLeader().getName());
        return nbt.getItem();
    }

    public void createFactionItemJson() {
        if (FactionHandHoes.getHandHoes().getConfigFolder().exists()) {
            harvesterHoeFile = getHarvesterHoeFile();
            if (!harvesterHoeFile.exists()) {
                try {
                    harvesterHoeFile.createNewFile();
                    HarvesterHoe factionBeacon = new HarvesterHoe();
                    PrintWriter pw = new PrintWriter(harvesterHoeFile, "UTF-8");
                    String jsonString = Utils.gson.toJson(factionBeacon);
                    pw.print(jsonString);
                    pw.flush();
                    pw.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public HarvesterHoe getClassHoeItem() {
        harvesterHoeFile = getHarvesterHoeFile();
        if (harvesterHoeFile.exists()) {
            try {
                String stringJson = new String(Files.readAllBytes(harvesterHoeFile.toPath()), StandardCharsets.UTF_8);
                return Utils.gson.fromJson(stringJson, HarvesterHoe.class);
            } catch (IOException ex) {
                return new HarvesterHoe();
            }
        }
        return new HarvesterHoe();
    }

    public File getHarvesterHoeFile() {
        return new File(FactionHandHoes.getHandHoes().getConfigFolder(),"harversterhoe.json");
    }
}
