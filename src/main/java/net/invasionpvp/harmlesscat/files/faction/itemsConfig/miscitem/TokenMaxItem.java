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

public class TokenMaxItem {

    public String title;
    public String[] lore;
    public String itemType;

    public TokenMaxItem() {
        title = "";
        lore = new String[]{};
        itemType = "DIRT";
    }


    public String getTitle() {
        return title;
    }

    public String[] getLore() {
        return lore;

    }

    public Material getItemType() { return Material.matchMaterial(itemType); }


    public File tokenMaxItemJsonFile;

    public void createMoneyItemJson() {
        if (FactionHandHoes.getHandHoes().getConfigFolder().exists()) {
            tokenMaxItemJsonFile = getTokenMaxItemJson();
            if (!tokenMaxItemJsonFile.exists()) {
                try {
                    tokenMaxItemJsonFile.createNewFile();
                    TokenMaxItem essenceItem = new TokenMaxItem();
                    PrintWriter pw = new PrintWriter(tokenMaxItemJsonFile, "UTF-8");
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

    public void givePlayer(Player player, int amount) {
        player.getInventory().addItem(getTokenMaxItemClass().getMoneyItemStack(amount));
        player.updateInventory();
    }

    public ItemStack getMoneyItemStack(Integer amount) {
        ItemStack booster = new ItemStack(Objects.requireNonNull(getItemType()), amount);
        ItemMeta boosterMeta = booster.getItemMeta();
        boosterMeta.setDisplayName(Utils.col(getTitle()));
        boosterMeta.setLore(Arrays.asList(Utils.col(getLore())));
        booster.setItemMeta(boosterMeta);
        NBTItem nbt = new NBTItem(booster);
        nbt.setBoolean("token",true);
        nbt.setString("tokenitem","true");
        nbt.setString("id","&4%2");
        nbt.setString("tokentype", "max");
        return nbt.getItem();
    }

    public TokenMaxItem getTokenMaxItemClass() {
        tokenMaxItemJsonFile = getTokenMaxItemJson();
        if (tokenMaxItemJsonFile.exists()) {
            try {
                String stringJson = new String(Files.readAllBytes(tokenMaxItemJsonFile.toPath()), StandardCharsets.UTF_8);
                return Utils.gson.fromJson(stringJson, TokenMaxItem.class);
            } catch (IOException ex) {
                return new TokenMaxItem();
            }
        }
        return new TokenMaxItem();
    }

    public File getTokenMaxItemJson() {
        return new File(FactionHandHoes.getHandHoes().getConfigFolder(),"tokenmaxitem.json");
    }

}
