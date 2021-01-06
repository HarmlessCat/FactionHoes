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

public class SpawnerItem {

    public int index;
    public String title;
    public String[] lore;
    public String itemType;

    public SpawnerItem() {
        index = 25;
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

    public Material getSpawnerMaterial() {
        if (Material.getMaterial(getClassSpawnerItem().getItemType()) != null) {
            return Material.getMaterial(getClassSpawnerItem().getItemType());
        }
        return Material.DIRT;
    }

    private File spawnerJsonFile;

    public void createSpawnerJson() {
        if (FactionHandHoes.getHandHoes().getConfigFolder().exists()) {
            spawnerJsonFile = getSpawnerJson();
            if (!spawnerJsonFile.exists()) {
                try {
                    spawnerJsonFile.createNewFile();
                    SpawnerItem spawnerItem = new SpawnerItem();
                    PrintWriter pw = new PrintWriter(spawnerJsonFile, "UTF-8");
                    String jsonString = Utils.gson.toJson(spawnerItem);
                    pw.print(jsonString);
                    pw.flush();
                    pw.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public SpawnerItem getClassSpawnerItem() {
        spawnerJsonFile = getSpawnerJson();
        if (spawnerJsonFile.exists()) {
            try {
                String stringJson = new String(Files.readAllBytes(spawnerJsonFile.toPath()), StandardCharsets.UTF_8);
                return Utils.gson.fromJson(stringJson, SpawnerItem.class);
            } catch (IOException ex) {
                return new SpawnerItem();
            }
        }
        return new SpawnerItem();
    }

    public String getEssenceCost(int level) {
        if (level != FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getSpawnerMax()) {
            return Utils.col("&f"+Utils.getCost(FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getClassCostConfig().getSpawnerEssenceCost()[level]
                    *FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getEssenceCostMultiplier())+ " &dEssence");
        } else {
            return "&c&lMAX LEVEL";
        }
    }

    public String getTokenCost(int level) {
        if (level != FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getSpawnerMax()) {
            if (FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getSpawnerTokenCost()[level] != 0) {
                if (level != FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getSpawnerMax()) {
                    return Utils.col("&f"+FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getSpawnerTokenCost()[level] + " &dTokens");
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

    public File getSpawnerJson() {
        return new File(FactionHandHoes.getHandHoes().getConfigFolder(),"spawner.json");
    }

    public boolean upgrade(Player player, int amount, boolean max, boolean force) {
        JsonCostConfig cost = FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig();
        JsonConfig jsonMessageConfig = FactionHandHoes.getHandHoes().getJsonConfig().getClassMessageConfig();
        JsonFactionUpgrade factionUpgrade =
                FactionHandHoes.getHandHoes().getJsonFactionUpgrade().getClassFactionUpgrade(MPlayer.get(player).getFaction().getId());
        if (factionUpgrade.getSpawnerFactionLVL() < FactionHandHoes.getHandHoes().getJsonCostConfig().getSpawnerMax()) {
            if (!force) {
                if (factionUpgrade.getEssenceBalance() >= cost.getSpawnerEssenceCost()[factionUpgrade.getSpawnerFactionLVL()]
                        *FactionHandHoes.getHandHoes().getJsonCostConfig().getEssenceCostMultiplier()) {
                    if (factionUpgrade.getTokenBalance() >= cost.getSpawnerTokenCost()[factionUpgrade.getSpawnerFactionLVL()]) {
                        FactionHandHoes.getHandHoes().getJsonFactionUpgrade().playerBuy(
                                MPlayer.get(player).getFaction().getId(),
                                cost.getSpawnerEssenceCost()[factionUpgrade.getSpawnerFactionLVL()]
                                        *FactionHandHoes.getHandHoes().getJsonCostConfig().getEssenceCostMultiplier(),
                                cost.getSpawnerTokenCost()[factionUpgrade.getSpawnerFactionLVL()]);

                        FactionHandHoes.getHandHoes().getJsonFactionUpgrade().upgradeSpawner(
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
                FactionHandHoes.getHandHoes().getJsonFactionUpgrade().upgradeSpawner(
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
