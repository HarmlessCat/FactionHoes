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

public class SaturationItem {

    public int index;
    public String title;
    public String[] lore;
    public String itemType;

    public SaturationItem() {
        index = 34;
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

    public Material getSaturationMaterial() {
        if (Material.getMaterial(getClassSaturationItem().getItemType()) != null) {
            return Material.getMaterial(getClassSaturationItem().getItemType());
        }
        return Material.DIRT;
    }

    private File saturationJsonFile;

    public void createSaturationJson() {
        if (FactionHandHoes.getHandHoes().getConfigFolder().exists()) {
            saturationJsonFile = getSaturationJson();
            if (!saturationJsonFile.exists()) {
                try {
                    saturationJsonFile.createNewFile();
                    SaturationItem saturationItem = new SaturationItem();
                    PrintWriter pw = new PrintWriter(saturationJsonFile, "UTF-8");
                    String jsonString = Utils.gson.toJson(saturationItem);
                    pw.print(jsonString);
                    pw.flush();
                    pw.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public SaturationItem getClassSaturationItem() {
        saturationJsonFile = getSaturationJson();
        if (saturationJsonFile.exists()) {
            try {
                String stringJson = new String(Files.readAllBytes(saturationJsonFile.toPath()), StandardCharsets.UTF_8);
                return Utils.gson.fromJson(stringJson, SaturationItem.class);
            } catch (IOException ex) {
                return new SaturationItem();
            }
        }
        return new SaturationItem();
    }

    public String getEssenceCost(int level) {
        if (level != FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getSaturationMax()) {
            return Utils.col("&f"+Utils.getCost(FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getSaturationEssenceCost()[level]
                    *FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getEssenceCostMultiplier())+ " &dEssence");
        } else {
            return "&c&lMAX LEVEL";
        }
    }

    public String getTokenCost(int level) {
        if (level != FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getSaturationMax()) {
            if (FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getSaturationTokenCost()[level] != 0) {
                if (level != FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getSaturationMax()) {
                    return Utils.col("&f"+FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getSaturationTokenCost()[level] + " &dTokens");
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

    public File getSaturationJson() { return new File(FactionHandHoes.getHandHoes().getConfigFolder(),"saturation.json"); }

    public boolean upgrade(Player player, int amount, boolean max, boolean force) {
        JsonCostConfig cost = FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig();
        JsonConfig jsonMessageConfig = FactionHandHoes.getHandHoes().getJsonConfig().getClassMessageConfig();
        JsonFactionUpgrade factionUpgrade =
                FactionHandHoes.getHandHoes().getJsonFactionUpgrade().getClassFactionUpgrade(MPlayer.get(player).getFaction().getId());
        if (factionUpgrade.getSaturationFactionLVL() < FactionHandHoes.getHandHoes().getJsonCostConfig().getSaturationMax()) {
            if (!force) {
                if (factionUpgrade.getEssenceBalance() >= cost.getSaturationEssenceCost()[factionUpgrade.getSaturationFactionLVL()]
                        *FactionHandHoes.getHandHoes().getJsonCostConfig().getEssenceCostMultiplier()) {
                    if (factionUpgrade.getTokenBalance() >= cost.getSaturationTokenCost()[factionUpgrade.getSaturationFactionLVL()]) {
                        FactionHandHoes.getHandHoes().getJsonFactionUpgrade().playerBuy(
                                MPlayer.get(player).getFaction().getId(),
                                cost.getSaturationEssenceCost()[factionUpgrade.getSaturationFactionLVL()]
                                        *FactionHandHoes.getHandHoes().getJsonCostConfig().getEssenceCostMultiplier(),
                                cost.getSaturationTokenCost()[factionUpgrade.getSaturationFactionLVL()]);

                        FactionHandHoes.getHandHoes().getJsonFactionUpgrade().upgradeSaturation(
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
                FactionHandHoes.getHandHoes().getJsonFactionUpgrade().upgradeSaturation(
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
