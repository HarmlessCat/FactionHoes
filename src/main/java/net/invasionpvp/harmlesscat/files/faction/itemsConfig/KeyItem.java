package net.invasionpvp.harmlesscat.files.faction.itemsConfig;

import com.massivecraft.factions.entity.MPlayer;
import net.invasionpvp.harmlesscat.FactionHandHoes;
import net.invasionpvp.harmlesscat.files.JsonConfig;
import net.invasionpvp.harmlesscat.files.JsonCostConfig;
import net.invasionpvp.harmlesscat.files.faction.JsonFactionUpgrade;
import net.invasionpvp.harmlesscat.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class KeyItem {

    public int index;
    public String title;
    public String[] lore;
    public String itemType;

    public KeyItem() {
        index = 43;
        title = "";
        lore = new String[]{};
        itemType = "DIRT";
    }

    public String getTitle() { return title; }
    public String[] getLore() { return lore; }
    public String getItemType() { return itemType; }
    public int getIndex() {
        return index;
    }

    public Material getKeyMaterial() {
        if (Material.getMaterial(getClassKeyItem().getItemType()) != null) {
            return Material.getMaterial(getClassKeyItem().getItemType());
        }
        return Material.DIRT;
    }

    public File hasteJsonFile;

    public void createKeyJson() {
        if (FactionHandHoes.getHandHoes().getConfigFolder().exists()) {
            hasteJsonFile = getKeyJson();
            if (!hasteJsonFile.exists()) {
                try {
                    hasteJsonFile.createNewFile();
                    KeyItem keyItem = new KeyItem();
                    PrintWriter pw = new PrintWriter(hasteJsonFile, "UTF-8");
                    String jsonString = Utils.gson.toJson(keyItem);
                    pw.print(jsonString);
                    pw.flush();
                    pw.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public KeyItem getClassKeyItem() {
        hasteJsonFile = getKeyJson();
        if (hasteJsonFile.exists()) {
            try {
                String stringJson = new String(Files.readAllBytes(hasteJsonFile.toPath()), StandardCharsets.UTF_8);
                return Utils.gson.fromJson(stringJson, KeyItem.class);
            } catch (IOException ex) {
                return new KeyItem();
            }
        }
        return new KeyItem();
    }

    public String getEssenceCost(int level) {
        if (level != FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getKeyMax()) {
            return Utils.col("&f"+Utils.getCost(FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getKeyEssenceCost()[level]
                    *FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getEssenceCostMultiplier())+ " &dEssence");
        } else {
            return "&c&lMAX LEVEL";
        }
    }

    public String getTokenCost(int level) {
        if (level != FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getKeyMax()) {
            if (FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getKeyTokenCost()[level] != 0) {
                if (level != FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getKeyMax()) {
                    return Utils.col("&f"+FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getKeyTokenCost()[level] + " &dTokens");
                } else {
                    return "";
                }
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    public File getKeyJson() {
        return new File(FactionHandHoes.getHandHoes().getConfigFolder(),"key.json");
    }

    public boolean upgrade(Player player, int amount, boolean max, boolean force) {
        JsonCostConfig cost = FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig();
        JsonConfig jsonMessageConfig = FactionHandHoes.getHandHoes().getJsonConfig().getClassMessageConfig();
        JsonFactionUpgrade factionUpgrade =
                FactionHandHoes.getHandHoes().getJsonFactionUpgrade().getClassFactionUpgrade(MPlayer.get(player).getFaction().getId());
        if (factionUpgrade.getKeyFactionLVL() < FactionHandHoes.getHandHoes().getJsonCostConfig().getKeyMax()) {
            if (!force) {
                if (factionUpgrade.getEssenceBalance() >= cost.getKeyEssenceCost()[factionUpgrade.getKeyFactionLVL()]
                        *FactionHandHoes.getHandHoes().getJsonCostConfig().getEssenceCostMultiplier()) {
                    if (factionUpgrade.getTokenBalance() >= cost.getKeyTokenCost()[factionUpgrade.getKeyFactionLVL()]) {
                        FactionHandHoes.getHandHoes().getJsonFactionUpgrade().playerBuy(
                                MPlayer.get(player).getFaction().getId(),
                                cost.getKeyEssenceCost()[factionUpgrade.getKeyFactionLVL()]
                                        *FactionHandHoes.getHandHoes().getJsonCostConfig().getEssenceCostMultiplier(),
                                cost.getKeyTokenCost()[factionUpgrade.getKeyFactionLVL()]);

                        FactionHandHoes.getHandHoes().getJsonFactionUpgrade().upgradeKey(
                                MPlayer.get(player).getFaction().getId(),
                                amount,
                                max);
                        return true;
                    } else {
                        Utils.sendFMessage(player,Utils.col(jsonMessageConfig.getNoMoneyMessage()));
                    }
                } else {
                    Utils.sendFMessage(player,jsonMessageConfig.getNoMoneyMessage());
                }
            } else {
                FactionHandHoes.getHandHoes().getJsonFactionUpgrade().upgradeKey(
                        MPlayer.get(player).getFaction().getId(),
                        amount,
                        max);
            }
        } else {
            Utils.sendFMessage(player,jsonMessageConfig.getMaxLvlReachedMessage());
        }
        return false;
    }
}
