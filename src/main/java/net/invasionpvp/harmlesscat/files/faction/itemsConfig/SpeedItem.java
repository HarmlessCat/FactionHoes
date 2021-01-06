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

public class SpeedItem {

    public int index;
    public String title;
    public String[] lore;
    public String itemType;

    public SpeedItem() {
        index = 10;
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

    public Material getSpeedMaterial() {
        if (Material.getMaterial(getClassSpeedItem().getItemType()) != null) {
            return Material.getMaterial(getClassSpeedItem().getItemType());
        }
        return Material.DIRT;
    }

    private File speedJsonFile;

    public void createSpeedJson() {
        if (FactionHandHoes.getHandHoes().getConfigFolder().exists()) {
            speedJsonFile = getSpeedJson();
            if (!speedJsonFile.exists()) {
                try {
                    speedJsonFile.createNewFile();
                    SpeedItem speed = new SpeedItem();
                    PrintWriter pw = new PrintWriter(speedJsonFile, "UTF-8");
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

    public SpeedItem getClassSpeedItem() {
        speedJsonFile = getSpeedJson();
        if (speedJsonFile.exists()) {
            try {
                String stringJson = new String(Files.readAllBytes(speedJsonFile.toPath()), StandardCharsets.UTF_8);
                return Utils.gson.fromJson(stringJson, SpeedItem.class);
            } catch (IOException ex) {
                return new SpeedItem();
            }
        }
        return new SpeedItem();
    }

    public String getEssenceCost(int level) {
        if (level != FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getClassCostConfig().getSpeedMax()) {
            return Utils.col("&f"+Utils.getCost(FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getSpeedEssenceCost()[level]
                    *FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getEssenceCostMultiplier())+ " &dEssence");
        } else {
            return "&c&lMAX LEVEL";
        }
    }

    public String getTokenCost(int level) {
        if (level != FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getClassCostConfig().getSpeedMax()) {
            if (FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getSpeedTokenCost()[level] != 0) {
                return Utils.col("&f"+FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getSpeedTokenCost()[level] + " &dTokens");
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    public File getSpeedJson() {
        return new File(FactionHandHoes.getHandHoes().getConfigFolder(),"speed.json");
    }

    public boolean upgrade(Player player, int amount, boolean max, boolean force) {
        JsonCostConfig cost = FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig();
        JsonConfig jsonMessageConfig = FactionHandHoes.getHandHoes().getJsonConfig().getClassMessageConfig();
        JsonFactionUpgrade factionUpgrade =
                FactionHandHoes.getHandHoes().getJsonFactionUpgrade().getClassFactionUpgrade(MPlayer.get(player).getFaction().getId());
        if (factionUpgrade.getSpeedFactionLVL() < FactionHandHoes.getHandHoes().getJsonCostConfig().getSpeedMax()) {
            if (!force) {
                if (factionUpgrade.getEssenceBalance() >= cost.getSpeedEssenceCost()[factionUpgrade.getSpeedFactionLVL()]
                        *FactionHandHoes.getHandHoes().getJsonCostConfig().getEssenceCostMultiplier()) {
                    if (factionUpgrade.getTokenBalance() >= cost.getSpeedTokenCost()[factionUpgrade.getSpeedFactionLVL()]) {
                        FactionHandHoes.getHandHoes().getJsonFactionUpgrade().playerBuy(
                                MPlayer.get(player).getFaction().getId(),
                                cost.getSpeedEssenceCost()[factionUpgrade.getSpeedFactionLVL()]
                                        *FactionHandHoes.getHandHoes().getJsonCostConfig().getEssenceCostMultiplier(),
                                cost.getSpeedTokenCost()[factionUpgrade.getSpeedFactionLVL()]);

                        FactionHandHoes.getHandHoes().getJsonFactionUpgrade().upgradeSpeed(
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
                FactionHandHoes.getHandHoes().getJsonFactionUpgrade().upgradeSpeed(
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
