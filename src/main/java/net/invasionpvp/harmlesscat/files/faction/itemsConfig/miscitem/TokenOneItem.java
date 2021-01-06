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

public class TokenOneItem {

    public String title;
    public String[] lore;
    public String itemType;

    public TokenOneItem() {
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


    public File tokenOneItemJsonFile;

    public void createTokenOneItemJson() {
        if (FactionHandHoes.getHandHoes().getConfigFolder().exists()) {
            tokenOneItemJsonFile = getTokenOneItemJson();
            if (!tokenOneItemJsonFile.exists()) {
                try {
                    tokenOneItemJsonFile.createNewFile();
                    TokenOneItem essenceItem = new TokenOneItem();
                    PrintWriter pw = new PrintWriter(tokenOneItemJsonFile, "UTF-8");
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
        player.getInventory().addItem(getTokenOneItemClass().getTokenOneItemStack(amount));
        player.updateInventory();
    }

    public ItemStack getTokenOneItemStack(Integer amount) {
        ItemStack booster = new ItemStack(getItemType(), amount);
        ItemMeta boosterMeta = booster.getItemMeta();
        boosterMeta.setDisplayName(Utils.col(getTitle()));
        boosterMeta.setLore(Arrays.asList(Utils.col(getLore())));
        booster.setItemMeta(boosterMeta);
        NBTItem nbt = new NBTItem(booster);
        nbt.setBoolean("token",true);
        nbt.setString("id","&4%2");
        nbt.setString("tokenitem","true");
        nbt.setString("tokentype","one");
        return nbt.getItem();
    }

    public TokenOneItem getTokenOneItemClass() {
        tokenOneItemJsonFile = getTokenOneItemJson();
        if (tokenOneItemJsonFile.exists()) {
            try {
                String stringJson = new String(Files.readAllBytes(tokenOneItemJsonFile.toPath()), StandardCharsets.UTF_8);
                return Utils.gson.fromJson(stringJson, TokenOneItem.class);
            } catch (IOException ex) {
                return new TokenOneItem();
            }
        }
        return new TokenOneItem();
    }

    public File getTokenOneItemJson() {
        return new File(FactionHandHoes.getHandHoes().getConfigFolder(),"tokenoneitem.json");
    }

}
