package net.invasionpvp.harmlesscat.cane;

import com.massivecraft.factions.entity.Faction;
import net.invasionpvp.harmlesscat.FactionHandHoes;
import net.invasionpvp.harmlesscat.files.JsonCostConfig;
import net.invasionpvp.harmlesscat.files.faction.JsonFactionUpgrade;
import net.invasionpvp.harmlesscat.utils.Utils;

import java.util.HashMap;

public class Cane {

    private final HashMap<Faction, Cane> factionCaneMode = new HashMap<>();

    public void putFaction(Faction faction) {
        factionCaneMode.put(faction,getCaneMode(faction));
    }

    public Cane getLoadedCaneMode(Faction faction) {
        return factionCaneMode.get(faction);
    }

    public boolean inCane;
    public int saturation;
    public int speedLvl;
    public int hasteLvl;
    public double moneyMultiplier;
    public double essenceMultiplier;
    public double tokenChance;
    public double spawnerChance;
    public double keyChance;

    public Cane() {
        inCane = false;
        saturation = 0;
        speedLvl = 0;
        hasteLvl = 0;
        moneyMultiplier = 1;
        essenceMultiplier = 1;
        tokenChance = 0;
        spawnerChance = 0;
        keyChance = 0;
    }

    public boolean isInCane() { return inCane; }
    public int isSaturation() { return saturation; }
    public int getSpeedLvl() { return speedLvl; }
    public int getHasteLvl() { return hasteLvl; }
    public double getMoneyMultiplier() { return moneyMultiplier; }
    public double getEssenceMultiplier() { return essenceMultiplier; }
    public double getTokenChance() { return tokenChance; }
    public double getSpawnerChance() { return spawnerChance; }
    public double getKeyChance() { return keyChance; }

    public Cane getCaneMode(Faction faction) {
        JsonCostConfig costConfig = FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig();
        JsonFactionUpgrade upgrades = FactionHandHoes.getHandHoes().getJsonFactionUpgrade().getClassFactionUpgrade(faction.getId());
        Cane caneMode = new Cane();
        caneMode.saturation = upgrades.getSaturationFactionLVL();
        caneMode.speedLvl = upgrades.getSpeedFactionLVL();
        caneMode.hasteLvl = upgrades.getHasteFactionLVL();
        caneMode.moneyMultiplier = costConfig.getMoneyMultiLVL()[upgrades.getMoneyFactionLVL()];
        caneMode.essenceMultiplier = costConfig.getEssenceMultiLVL()[upgrades.getEssenceFactionLVL()];
        caneMode.tokenChance = costConfig.getTokenChance()[upgrades.getTokenFactionLVL()];
        caneMode.spawnerChance = costConfig.getSpawnerChanceLVL()[upgrades.getSpawnerFactionLVL()];
        caneMode.keyChance = costConfig.getKeyChanceLVL()[upgrades.getKeyFactionLVL()];
        return caneMode;
    }
}
