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

public class HasteItem {

    public int index;
    public String title;
    public String[] lore;
    public String itemType;

    public HasteItem() {
        index = 19;
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

    public Material getHasteMaterial() {
        if (Material.getMaterial(getClassHasteItem().getItemType()) != null) {
            return Material.getMaterial(getClassHasteItem().getItemType());
        }
        return Material.DIRT;
    }

    public File hasteJsonFile;

    public void createHasteJson() {
        if (FactionHandHoes.getHandHoes().getConfigFolder().exists()) {
            hasteJsonFile = getHasteJson();
            if (!hasteJsonFile.exists()) {
                try {
                    hasteJsonFile.createNewFile();
                    HasteItem hasteItem = new HasteItem();
                    PrintWriter pw = new PrintWriter(hasteJsonFile, "UTF-8");
                    String jsonString = Utils.gson.toJson(hasteItem);
                    pw.print(jsonString);
                    pw.flush();
                    pw.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public HasteItem getClassHasteItem() {
        hasteJsonFile = getHasteJson();
        if (hasteJsonFile.exists()) {
            try {
                String stringJson = new String(Files.readAllBytes(hasteJsonFile.toPath()), StandardCharsets.UTF_8);
                return Utils.gson.fromJson(stringJson, HasteItem.class);
            } catch (IOException ex) {
                return new HasteItem();
            }
        }
        return new HasteItem();
    }

    public String getEssenceCost(int level) {
        if (level != FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getHasteMax()) {
            return Utils.col("&f"+Utils.getCost(FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getHasteEssenceCost()[level]
                    *FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getEssenceCostMultiplier())+ " &dEssence");
        } else {
            return "&c&lMAX LEVEL";
        }
    }

    public String getTokenCost(int level) {
        if (level != FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getHasteMax()) {
            if (FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getHasteTokenCost()[level] != 0) {
                if (level != FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getHasteMax()) {
                    return Utils.col("&f"+FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getHasteTokenCost()[level] + " &dTokens");
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

    public File getHasteJson() {
        return new File(FactionHandHoes.getHandHoes().getConfigFolder(),"haste.json");
    }

    public boolean upgrade(Player player, int amount, boolean max, boolean force) {
        JsonCostConfig cost = FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig();
        JsonConfig jsonMessageConfig = FactionHandHoes.getHandHoes().getJsonConfig().getClassMessageConfig();
        JsonFactionUpgrade factionUpgrade =
                FactionHandHoes.getHandHoes().getJsonFactionUpgrade().getClassFactionUpgrade(MPlayer.get(player).getFaction().getId());
        if (factionUpgrade.getHasteFactionLVL() < FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getHasteMax()) {
            if (!force) {
                if (factionUpgrade.getEssenceBalance() >= cost.getHasteEssenceCost()[factionUpgrade.getHasteFactionLVL()]
                        *FactionHandHoes.getHandHoes().getJsonCostConfig().getEssenceCostMultiplier()) {
                    if (factionUpgrade.getTokenBalance() >= cost.getHasteTokenCost()[factionUpgrade.getHasteFactionLVL()]) {
                        FactionHandHoes.getHandHoes().getJsonFactionUpgrade().playerBuy(
                                MPlayer.get(player).getFaction().getId(),
                                cost.getHasteEssenceCost()[factionUpgrade.getHasteFactionLVL()]
                                        *FactionHandHoes.getHandHoes().getJsonCostConfig().getEssenceCostMultiplier(),
                                cost.getHasteTokenCost()[factionUpgrade.getHasteFactionLVL()]);

                        FactionHandHoes.getHandHoes().getJsonFactionUpgrade().upgradeHaste(
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
                FactionHandHoes.getHandHoes().getJsonFactionUpgrade().upgradeHaste(
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
