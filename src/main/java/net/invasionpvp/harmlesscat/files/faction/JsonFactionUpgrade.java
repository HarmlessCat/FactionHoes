package net.invasionpvp.harmlesscat.files.faction;

import com.massivecraft.factions.entity.Faction;
import net.invasionpvp.harmlesscat.FactionHandHoes;
import net.invasionpvp.harmlesscat.files.JsonCostConfig;
import net.invasionpvp.harmlesscat.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

public class JsonFactionUpgrade {

    public String name = null;
    public int speedFactionLVL = 0;
    public int hasteFactionLVL = 0;
    public int saturationFactionLVL = 0;
    public int essenceFactionLVL = 0;
    public int moneyFactionLVL = 0;
    public int keyFactionLVL = 0;
    public int spawnerFactionLVL = 0;
    public int tokenFactionLVL = 0;

    public double tokenBalance = 0;
    public double essenceBalance = 0;

    public int getEssenceFactionLVL() {
        return essenceFactionLVL;
    }

    public int getHasteFactionLVL() {
        return hasteFactionLVL;
    }

    public int getKeyFactionLVL() { return keyFactionLVL; }

    public int getMoneyFactionLVL() {
        return moneyFactionLVL;
    }

    public int getSaturationFactionLVL() {
        return saturationFactionLVL;
    }

    public int getSpeedFactionLVL() {
        return speedFactionLVL;
    }

    public int getSpawnerFactionLVL() {
        return spawnerFactionLVL;
    }

    public int getTokenFactionLVL() {
        return tokenFactionLVL;
    }

    public double getEssenceBalance() {
        return essenceBalance;
    }

    public double getTokenBalance() {
        return tokenBalance;
    }

    private File jsonFactionUpgrade;

    public void createJsonFactionUpgrade(String factionId) {
        if (FactionHandHoes.getHandHoes().getConfigFolder().exists()) {
            jsonFactionUpgrade = getJsonUpgradeFile(factionId);
            if (!jsonFactionUpgrade.exists()) {
                try {
                    jsonFactionUpgrade.createNewFile();
                    JsonFactionUpgrade jsonFactionUpgradeClass = new JsonFactionUpgrade();
                    PrintWriter pw = new PrintWriter(jsonFactionUpgrade, "UTF-8");
                    String jsonString = Utils.gson.toJson(jsonFactionUpgradeClass);
                    pw.print(jsonString);
                    pw.flush();
                    pw.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    public JsonFactionUpgrade getClassFactionUpgrade(String factionId) {
        jsonFactionUpgrade = getJsonUpgradeFile(factionId);
        if (jsonFactionUpgrade.exists()) {
            try {
                String stringJson = new String(Files.readAllBytes(jsonFactionUpgrade.toPath()));
                return Utils.gson.fromJson(stringJson, JsonFactionUpgrade.class);
            } catch (IOException ex) {
                return new JsonFactionUpgrade();
            }
        }
        return new JsonFactionUpgrade();
    }

    public void playerBuy(String factionId, int essencePrice, int tokenPrice) {
        jsonFactionUpgrade = getJsonUpgradeFile(factionId);
        if (jsonFactionUpgrade.exists()) {
            try {
                JsonFactionUpgrade upgrades = getClassFactionUpgrade(factionId);
                upgrades.essenceBalance = upgrades.getEssenceBalance() - essencePrice;
                upgrades.tokenBalance = upgrades.getTokenBalance() - tokenPrice;
                PrintWriter pw = new PrintWriter(jsonFactionUpgrade, "UTF-8");
                String jsonString = Utils.gson.toJson(upgrades);
                pw.print(jsonString);
                pw.flush();
                pw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void upgradeEssence(String factionId, Integer increase, boolean max) {
        jsonFactionUpgrade = getJsonUpgradeFile(factionId);
        if (jsonFactionUpgrade.exists()) {
            try {
                JsonFactionUpgrade upgrades = getClassFactionUpgrade(factionId);
                if (!max) {
                    upgrades.essenceFactionLVL = upgrades.getEssenceFactionLVL()+increase;
                } else {
                    upgrades.essenceFactionLVL = FactionHandHoes.getHandHoes().getJsonCostConfig().getEssenceMax();
                }
                PrintWriter pw = new PrintWriter(jsonFactionUpgrade, "UTF-8");
                String jsonString = Utils.gson.toJson(upgrades);
                pw.print(jsonString);
                pw.flush();
                pw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void upgradeHaste(String factionId, Integer increase, boolean max) {
        jsonFactionUpgrade = getJsonUpgradeFile(factionId);
        if (jsonFactionUpgrade.exists()) {
            try {
                JsonFactionUpgrade upgrades = getClassFactionUpgrade(factionId);
                if (!max) {
                    upgrades.hasteFactionLVL = upgrades.getHasteFactionLVL()+increase;
                } else {
                    upgrades.hasteFactionLVL = FactionHandHoes.getHandHoes().getJsonCostConfig().getHasteMax();
                }
                PrintWriter pw = new PrintWriter(jsonFactionUpgrade, "UTF-8");
                String jsonString = Utils.gson.toJson(upgrades);
                pw.print(jsonString);
                pw.flush();
                pw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void upgradeKey(String factionId, Integer increase, boolean max) {
        jsonFactionUpgrade = getJsonUpgradeFile(factionId);
        if (jsonFactionUpgrade.exists()) {
            try {
                JsonFactionUpgrade upgrades = getClassFactionUpgrade(factionId);
                if (!max) {
                    upgrades.keyFactionLVL = upgrades.getKeyFactionLVL()+increase;
                } else {
                    upgrades.keyFactionLVL = FactionHandHoes.getHandHoes().getJsonCostConfig().getKeyMax();
                }
                PrintWriter pw = new PrintWriter(jsonFactionUpgrade, "UTF-8");
                String jsonString = Utils.gson.toJson(upgrades);
                pw.print(jsonString);
                pw.flush();
                pw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void upgradeMoney(String factionId, Integer increase, boolean max) {
        jsonFactionUpgrade = getJsonUpgradeFile(factionId);
        if (jsonFactionUpgrade.exists()) {
            try {
                JsonFactionUpgrade upgrades = getClassFactionUpgrade(factionId);
                if (!max) {
                    upgrades.moneyFactionLVL = upgrades.getMoneyFactionLVL()+increase;
                } else {
                    upgrades.moneyFactionLVL = FactionHandHoes.getHandHoes().getJsonCostConfig().getMoneyMax();
                }
                PrintWriter pw = new PrintWriter(jsonFactionUpgrade, "UTF-8");
                String jsonString = Utils.gson.toJson(upgrades);
                pw.print(jsonString);
                pw.flush();
                pw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void upgradeSaturation(String factionId, Integer increase, boolean max) {
        jsonFactionUpgrade = getJsonUpgradeFile(factionId);
        if (jsonFactionUpgrade.exists()) {
            try {
                JsonFactionUpgrade upgrades = getClassFactionUpgrade(factionId);
                if (!max) {
                    upgrades.saturationFactionLVL = upgrades.getSaturationFactionLVL()+increase;
                } else {
                    upgrades.saturationFactionLVL = FactionHandHoes.getHandHoes().getJsonCostConfig().getSaturationMax();
                }
                PrintWriter pw = new PrintWriter(jsonFactionUpgrade, "UTF-8");
                String jsonString = Utils.gson.toJson(upgrades);
                pw.print(jsonString);
                pw.flush();
                pw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void upgradeSpawner(String factionId, Integer increase, boolean max) {
        jsonFactionUpgrade = getJsonUpgradeFile(factionId);
        if (jsonFactionUpgrade.exists()) {
            try {
                JsonFactionUpgrade upgrades = getClassFactionUpgrade(factionId);
                if (!max) {
                    upgrades.spawnerFactionLVL = upgrades.getSpawnerFactionLVL()+increase;
                } else {
                    upgrades.spawnerFactionLVL = FactionHandHoes.getHandHoes().getJsonCostConfig().getSpawnerMax();
                }
                PrintWriter pw = new PrintWriter(jsonFactionUpgrade, "UTF-8");
                String jsonString = Utils.gson.toJson(upgrades);
                pw.print(jsonString);
                pw.flush();
                pw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void upgradeSpeed(String factionId, Integer increase, boolean max) {
        jsonFactionUpgrade = getJsonUpgradeFile(factionId);
        if (jsonFactionUpgrade.exists()) {
            try {
                JsonFactionUpgrade upgrades = getClassFactionUpgrade(factionId);
                if (!max) {
                    upgrades.speedFactionLVL = upgrades.getSpeedFactionLVL()+increase;
                } else {
                    upgrades.speedFactionLVL = FactionHandHoes.getHandHoes().getJsonCostConfig().getSpeedMax();
                }
                PrintWriter pw = new PrintWriter(jsonFactionUpgrade, "UTF-8");
                String jsonString = Utils.gson.toJson(upgrades);
                pw.print(jsonString);
                pw.flush();
                pw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void upgradeToken(String factionId, Integer increase, boolean max) {
        jsonFactionUpgrade = getJsonUpgradeFile(factionId);
        if (jsonFactionUpgrade.exists()) {
            try {
                JsonFactionUpgrade upgrades = getClassFactionUpgrade(factionId);
                if (!max) {
                    upgrades.tokenFactionLVL = upgrades.getTokenFactionLVL()+increase;
                } else {
                    upgrades.tokenFactionLVL = FactionHandHoes.getHandHoes().getJsonCostConfig().getTokenMax();
                }
                PrintWriter pw = new PrintWriter(jsonFactionUpgrade, "UTF-8");
                String jsonString = Utils.gson.toJson(upgrades);
                pw.print(jsonString);
                pw.flush();
                pw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void addEssence(String factionId, int increase) {
        jsonFactionUpgrade = getJsonUpgradeFile(factionId);
        if (jsonFactionUpgrade.exists()) {
            try {
                JsonFactionUpgrade upgrades = getClassFactionUpgrade(factionId);
                upgrades.essenceBalance = upgrades.getEssenceBalance()+increase;
                PrintWriter pw = new PrintWriter(jsonFactionUpgrade, "UTF-8");
                String jsonString = Utils.gson.toJson(upgrades);
                pw.print(jsonString);
                pw.flush();
                pw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void addTokens(String factionId, int increase) {
        jsonFactionUpgrade = getJsonUpgradeFile(factionId);
        if (jsonFactionUpgrade.exists()) {
            try {
                JsonFactionUpgrade upgrades = getClassFactionUpgrade(factionId);
                upgrades.tokenBalance = upgrades.getTokenBalance()+increase;
                PrintWriter pw = new PrintWriter(jsonFactionUpgrade, "UTF-8");
                String jsonString = Utils.gson.toJson(upgrades);
                pw.print(jsonString);
                pw.flush();
                pw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public File getJsonUpgradeFile(String factionId) {
        return new File(FactionHandHoes.getHandHoes().getFactionDataFolder(),factionId+"upgrade.json");
    }
}
