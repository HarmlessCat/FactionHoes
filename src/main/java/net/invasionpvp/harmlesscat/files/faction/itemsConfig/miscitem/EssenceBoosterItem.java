package net.invasionpvp.harmlesscat.files.faction.itemsConfig.miscitem;

import com.cryptomorin.xseries.XMaterial;
import de.tr7zw.nbtapi.NBTItem;
import net.invasionpvp.harmlesscat.FactionHandHoes;
import net.invasionpvp.harmlesscat.files.faction.itemsConfig.EssenceItem;
import net.invasionpvp.harmlesscat.files.faction.itemsConfig.SpeedItem;
import net.invasionpvp.harmlesscat.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
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

public class EssenceBoosterItem {

    public String title;
    public String[] lore;
    public String itemType;

    public EssenceBoosterItem() {
        title = "";
        lore = new String[]{};
        itemType = "DIRT";
    }


    public String getTitle(Double multiplier, Integer length) {
        String thisTitle = title;
        String newThisTitle = thisTitle.replace("%multiplier%",multiplier.toString());
        return newThisTitle.replace("%length%",length.toString());
    }

    public String[] getLore(Double multiplier, Integer length) {
        String[] thisLore = lore;
        ArrayList<String> colText = new ArrayList<>();
        String temp;
        for (String message : thisLore) {
            temp = message.replace("%multiplier%",multiplier.toString());
            colText.add(temp.replace("%length%",length.toString()));
        }
        return colText.toArray(new String[0]);

    }

    public Material getItemType() { return Material.matchMaterial(itemType); }


    public File essenceItemJsonFile;

    public void createEssenceItemJson() {
        if (FactionHandHoes.getHandHoes().getConfigFolder().exists()) {
            essenceItemJsonFile = getEssenceItemJson();
            if (!essenceItemJsonFile.exists()) {
                try {
                    essenceItemJsonFile.createNewFile();
                    EssenceBoosterItem essenceItem = new EssenceBoosterItem();
                    PrintWriter pw = new PrintWriter(essenceItemJsonFile, "UTF-8");
                    String jsonString = Utils.gson.toJson(essenceItem);
                    pw.print(jsonString);
                    pw.flush();
                    pw.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void giveItem(Player player, Integer amt, Double multiplier, Integer length) {
        player.getInventory().addItem(getEssenceItemClass().getEssenceItemStack(amt,multiplier,length));
        player.updateInventory();
    }

    public ItemStack getEssenceItemStack(Integer amount, Double multiplier, Integer length) {
        ItemStack booster = new ItemStack(getEssenceItemClass().getItemType(), amount);
        ItemMeta boosterMeta = booster.getItemMeta();
        boosterMeta.setDisplayName(Utils.col(getTitle(multiplier,length)));
        boosterMeta.setLore(Arrays.asList(Utils.col(getLore(multiplier,length))));
        booster.setItemMeta(boosterMeta);
        NBTItem nbt = new NBTItem(booster);
        nbt.setString("id","&4%2");
        nbt.setBoolean("isbooster",true);
        nbt.setInteger("length",length);
        nbt.setDouble("multiplier",multiplier);
        nbt.setString("booster","essence");
        return nbt.getItem();

    }

    public EssenceBoosterItem getEssenceItemClass() {
        essenceItemJsonFile = getEssenceItemJson();
        if (essenceItemJsonFile.exists()) {
            try {
                String stringJson = new String(Files.readAllBytes(essenceItemJsonFile.toPath()), StandardCharsets.UTF_8);
                return Utils.gson.fromJson(stringJson, EssenceBoosterItem.class);
            } catch (IOException ex) {
                return new EssenceBoosterItem();
            }
        }
        return new EssenceBoosterItem();
    }

    public File getEssenceItemJson() {
        return new File(FactionHandHoes.getHandHoes().getConfigFolder(),"essencebooster.json");
    }
}
