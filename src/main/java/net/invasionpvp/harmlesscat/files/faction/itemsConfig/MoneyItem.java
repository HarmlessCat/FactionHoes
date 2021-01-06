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

public class MoneyItem {

    public int index;
    public String title;
    public String[] lore;
    public String itemType;

    public MoneyItem() {
        index = 37;
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

    public Material getMoneyMaterial() {
        if (Material.getMaterial(getClassMoneyItem().getItemType()) != null) {
            return Material.getMaterial(getClassMoneyItem().getItemType());
        }
        return Material.DIRT;
    }

    public File hasteJsonFile;

    public void createMoneyJson() {
        if (FactionHandHoes.getHandHoes().getConfigFolder().exists()) {
            hasteJsonFile = getMoneyJson();
            if (!hasteJsonFile.exists()) {
                try {
                    hasteJsonFile.createNewFile();
                    MoneyItem speed = new MoneyItem();
                    PrintWriter pw = new PrintWriter(hasteJsonFile, "UTF-8");
                    String jsonString = Utils.gson.toJson(speed);
                    pw.print(jsonString);
                    pw.flush();
                    pw.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public MoneyItem getClassMoneyItem() {
        hasteJsonFile = getMoneyJson();
        if (hasteJsonFile.exists()) {
            try {
                String stringJson = new String(Files.readAllBytes(hasteJsonFile.toPath()), StandardCharsets.UTF_8);
                return Utils.gson.fromJson(stringJson, MoneyItem.class);
            } catch (IOException ex) {
                return new MoneyItem();
            }
        }
        return new MoneyItem();
    }

    public String getEssenceCost(int level) {
        if (level != FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getMoneyMax()) {
            return Utils.col("&f"+Utils.getCost(FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getMoneyEssenceCost()[level]
                    *FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getEssenceCostMultiplier())+ " &dEssence");
        } else {
            return "&c&lMAX LEVEL";
        }
    }

    public String getTokenCost(int level) {
        if (level != FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getMoneyMax()) {
            if (FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getMoneyTokenCost()[level] != 0) {
                if (level != FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getMoneyMax()) {
                    return Utils.col("&f"+FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getClassCostConfig().getMoneyTokenCost()[level] + " &dTokens");
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

    public File getMoneyJson() {
        return new File(FactionHandHoes.getHandHoes().getConfigFolder(),"money.json");
    }

    public boolean upgrade(Player player, int amount, boolean max, boolean force) {
        JsonCostConfig cost = FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig();
        JsonConfig jsonMessageConfig = FactionHandHoes.getHandHoes().getJsonConfig().getClassMessageConfig();
        JsonFactionUpgrade factionUpgrade =
                FactionHandHoes.getHandHoes().getJsonFactionUpgrade().getClassFactionUpgrade(MPlayer.get(player).getFaction().getId());
        if (factionUpgrade.getMoneyFactionLVL() < FactionHandHoes.getHandHoes().getJsonCostConfig().getMoneyMax()) {
            if (!force) {
                if (factionUpgrade.getEssenceBalance() >= cost.getMoneyEssenceCost()[factionUpgrade.getMoneyFactionLVL()]
                        *FactionHandHoes.getHandHoes().getJsonCostConfig().getEssenceCostMultiplier()) {
                    if (factionUpgrade.getTokenBalance() >= cost.getMoneyTokenCost()[factionUpgrade.getMoneyFactionLVL()]) {
                        FactionHandHoes.getHandHoes().getJsonFactionUpgrade().playerBuy(
                                MPlayer.get(player).getFaction().getId(),
                                cost.getMoneyEssenceCost()[factionUpgrade.getMoneyFactionLVL()]
                                        *FactionHandHoes.getHandHoes().getJsonCostConfig().getEssenceCostMultiplier(),
                                cost.getMoneyTokenCost()[factionUpgrade.getMoneyFactionLVL()]);

                        FactionHandHoes.getHandHoes().getJsonFactionUpgrade().upgradeMoney(
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
                    FactionHandHoes.getHandHoes().getJsonFactionUpgrade().upgradeMoney(
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
