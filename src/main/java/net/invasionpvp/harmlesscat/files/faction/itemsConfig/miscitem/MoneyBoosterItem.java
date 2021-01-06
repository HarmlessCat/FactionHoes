package net.invasionpvp.harmlesscat.files.faction.itemsConfig.miscitem;

import com.cryptomorin.xseries.XMaterial;
import de.tr7zw.nbtapi.NBTItem;
import net.invasionpvp.harmlesscat.FactionHandHoes;
import net.invasionpvp.harmlesscat.utils.Utils;
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

public class MoneyBoosterItem {

    public String title;
    public String[] lore;
    public String itemType;

    public MoneyBoosterItem() {
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


    public File moneyItemJsonFile;

    public void createMoneyItemJson() {
        if (FactionHandHoes.getHandHoes().getConfigFolder().exists()) {
            moneyItemJsonFile = getMoneyItemJson();
            if (!moneyItemJsonFile.exists()) {
                try {
                    moneyItemJsonFile.createNewFile();
                    MoneyBoosterItem essenceItem = new MoneyBoosterItem();
                    PrintWriter pw = new PrintWriter(moneyItemJsonFile, "UTF-8");
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
        player.getInventory().addItem(getMoneyItemClass().getMoneyItemStack(amt,multiplier,length));
        player.updateInventory();
    }

    public ItemStack getMoneyItemStack(Integer amount, Double multiplier, Integer length) {
        ItemStack booster = new ItemStack(getMoneyItemClass().getItemType(), amount);
        ItemMeta boosterMeta = booster.getItemMeta();
        boosterMeta.setDisplayName(Utils.col(getTitle(multiplier,length)));
        boosterMeta.setLore(Arrays.asList(Utils.col(getLore(multiplier,length))));
        booster.setItemMeta(boosterMeta);
        NBTItem nbt = new NBTItem(booster);
        nbt.setString("id","&4%2");
        nbt.setBoolean("isbooster",true);
        nbt.setInteger("length",length);
        nbt.setDouble("multiplier",multiplier);
        nbt.setString("booster","money");
        return nbt.getItem();
    }

    public MoneyBoosterItem getMoneyItemClass() {
        moneyItemJsonFile = getMoneyItemJson();
        if (moneyItemJsonFile.exists()) {
            try {
                String stringJson = new String(Files.readAllBytes(moneyItemJsonFile.toPath()), StandardCharsets.UTF_8);
                return Utils.gson.fromJson(stringJson, MoneyBoosterItem.class);
            } catch (IOException ex) {
                return new MoneyBoosterItem();
            }
        }
        return new MoneyBoosterItem();
    }

    public File getMoneyItemJson() {
        return new File(FactionHandHoes.getHandHoes().getConfigFolder(),"moneybooster.json");
    }

}
