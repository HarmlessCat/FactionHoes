package net.invasionpvp.harmlesscat.files.faction.itemsConfig;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import net.invasionpvp.harmlesscat.FactionHandHoes;
import net.invasionpvp.harmlesscat.files.JsonConfig;
import net.invasionpvp.harmlesscat.files.JsonCostConfig;
import net.invasionpvp.harmlesscat.files.faction.JsonFactionUpgrade;
import net.invasionpvp.harmlesscat.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class EssenceItem {

    public int index;
    public String title;
    public String[] lore;
    public String itemType;

    public EssenceItem() {
        index = 28;
        title = "";
        lore = new String[]{};
        itemType = "DIRT";
    }

    public int getIndex() {
        return index;
    }
    public String getTitle() { return title; }
    public String[] getLore() { return lore; }
    public String getItemType() { return itemType; }

    public Material getEssenceMaterial() {
        if (Material.getMaterial(getClassEssenceItem().getItemType()) != null) {
            return Material.getMaterial(getClassEssenceItem().getItemType());
        }
        return Material.DIRT;
    }

    public File essenceJsonFile;

    public void createEssenceJson() {
        if (FactionHandHoes.getHandHoes().getConfigFolder().exists()) {
            essenceJsonFile = getEssenceJson();
            if (!essenceJsonFile.exists()) {
                try {
                    essenceJsonFile.createNewFile();
                    EssenceItem essenceItem = new EssenceItem();
                    PrintWriter pw = new PrintWriter(essenceJsonFile, "UTF-8");
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


    public EssenceItem getClassEssenceItem() {
        essenceJsonFile = getEssenceJson();
        if (essenceJsonFile.exists()) {
            try {
                String stringJson = new String(Files.readAllBytes(essenceJsonFile.toPath()), StandardCharsets.UTF_8);
                return Utils.gson.fromJson(stringJson, EssenceItem.class);
            } catch (IOException ex) {
                return new EssenceItem();
            }
        }
        return new EssenceItem();
    }

    public String getEssenceCost(int level) {
        if (level != FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getEssenceMax()) {
            return Utils.col("&f"+Utils.getCost(FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getEssenceEssenceCost()[level]
                    *FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getEssenceCostMultiplier())+ " &dEssence");
        } else {
            return "&c&lMAX LEVEL";
        }
    }

    public String getTokenCost(int level) {
        if (level != FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getEssenceMax()) {
            if (FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getEssenceTokenCost()[level] != 0) {
                if (level != FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getEssenceMax()) {
                    return Utils.col("&f"+FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getEssenceTokenCost()[level] + " &dTokens");
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

    public File getEssenceJson() {
        return new File(FactionHandHoes.getHandHoes().getConfigFolder(),"essence.json");
    }

    public boolean upgrade(Player player, int amount, boolean max, boolean force) {
        JsonCostConfig cost = FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig();
        JsonConfig jsonMessageConfig = FactionHandHoes.getHandHoes().getJsonConfig().getClassMessageConfig();
        JsonFactionUpgrade factionUpgrade =
                FactionHandHoes.getHandHoes().getJsonFactionUpgrade().getClassFactionUpgrade(MPlayer.get(player).getFaction().getId());
        if (factionUpgrade.getEssenceFactionLVL() < FactionHandHoes.getHandHoes().getJsonCostConfig().getEssenceMax()) {
            if (!force) {
                if (factionUpgrade.getEssenceBalance() >= cost.getEssenceEssenceCost()[factionUpgrade.getEssenceFactionLVL()]
                        *FactionHandHoes.getHandHoes().getJsonCostConfig().getEssenceCostMultiplier()) {
                    if (factionUpgrade.getTokenBalance() >= cost.getEssenceTokenCost()[factionUpgrade.getEssenceFactionLVL()]) {
                        FactionHandHoes.getHandHoes().getJsonFactionUpgrade().playerBuy(
                                MPlayer.get(player).getFaction().getId(),
                                cost.getEssenceEssenceCost()[factionUpgrade.getEssenceFactionLVL()]
                                        *FactionHandHoes.getHandHoes().getJsonCostConfig().getEssenceCostMultiplier(),
                                cost.getEssenceTokenCost()[factionUpgrade.getEssenceFactionLVL()]);

                        FactionHandHoes.getHandHoes().getJsonFactionUpgrade().upgradeEssence(
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
                FactionHandHoes.getHandHoes().getJsonFactionUpgrade().upgradeEssence(
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
