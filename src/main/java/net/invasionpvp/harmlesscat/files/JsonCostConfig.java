package net.invasionpvp.harmlesscat.files;

import net.invasionpvp.harmlesscat.FactionHandHoes;
import net.invasionpvp.harmlesscat.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class JsonCostConfig {

    public final String[] info = new String[]{
            "Cost of upgrades and upgrade values, for money and essence the values mean x*lvl value so for example lvl 1",
            "essenceMultiLVL would give 1.0*x and lvl 2 would give1.1*x, The AMOUNT OF LEVELS HAS TO BE ONE HIGHER THEN THE COST OF GIVE UPGRADE",
            "FOR EXAMPLE essenceEssenceCost and essenceTokenCost has 6 VALUES EACH THE LEVELMUTLI OF THAT UPGRADE HAS TO HAVE 7 values or default ",
            "values will be used otherwise"};


    public final int sellPrice;
    public final int essenceCostMultiplier;

    public final int[] essenceEssenceCost;
    public final int[] essenceTokenCost;

    public final int[] speedEssenceCost;
    public final int[] speedTokenCost;


    public final int[] tokenEssenceCost;
    public final int[] tokenTokenCost;

    public final int[] hasteEssenceCost;
    public final int[] hasteTokenCost;

    public final int[] keyEssenceCost;
    public final int[] keyTokenCost;

    public final int[] moneyEssenceCost;
    public final int[] moneyTokenCost;

    public final int[] saturationEssenceCost;
    public final int[] saturationTokenCost;

    public final int[] spawnerEssenceCost;
    public final int[] spawnerTokenCost;

    public final double[] essenceMultiLVL;
    public final double[] moneyMultiLVL;
    public final double[] tokenChance;

    public final double[] spawnerChanceLVL;
    public final double[] keyChanceLVL;

    public JsonCostConfig() {
        sellPrice = 15;
        essenceCostMultiplier = 10000;
        essenceEssenceCost = new int[] {0};
        essenceTokenCost = new int[] {0};
        speedEssenceCost = new int[] {0};
        speedTokenCost  = new int[] {0};
        tokenEssenceCost = new int[] {0};
        tokenTokenCost = new int[] {0};
        hasteEssenceCost = new int[] {0};
        hasteTokenCost = new int[] {0};
        keyEssenceCost = new int[] {0};
        keyTokenCost = new int[] {0};
        moneyEssenceCost = new int[] {0};
        moneyTokenCost = new int[] {0};
        saturationEssenceCost = new int[] {   2};
        saturationTokenCost = new int[] {0};
        spawnerEssenceCost = new int[] {0};
        spawnerTokenCost = new int[] {0};
        essenceMultiLVL = new double[] {0};
        moneyMultiLVL = new double[] {0};
        tokenChance = new double[] {0};
        spawnerChanceLVL = new double[] {0};
        keyChanceLVL = new double[] {0};
    }

    public int getSellPrice() { return sellPrice; }
    public int getEssenceCostMultiplier() { return essenceCostMultiplier; }

    public int[] getEssenceEssenceCost() { return essenceEssenceCost; }
    public int[] getEssenceTokenCost() { return essenceTokenCost; }

    public int[] getSpeedEssenceCost() { return speedEssenceCost; }
    public int[] getSpeedTokenCost() { return speedTokenCost; }

    public int[] getTokenEssenceCost() { return tokenEssenceCost; }
    public int[] getTokenTokenCost() { return tokenTokenCost; }

    public int[] getHasteEssenceCost() { return hasteEssenceCost; }
    public int[] getHasteTokenCost() { return hasteTokenCost; }

    public int[] getKeyEssenceCost() { return keyEssenceCost; }
    public int[] getKeyTokenCost() { return keyTokenCost; }

    public int[] getMoneyEssenceCost() { return moneyEssenceCost; }
    public int[] getMoneyTokenCost() { return moneyTokenCost; }

    public int[] getSaturationEssenceCost() { return saturationEssenceCost; }
    public int[] getSaturationTokenCost() { return saturationTokenCost; }

    public int[] getSpawnerEssenceCost() { return spawnerEssenceCost; }
    public int[] getSpawnerTokenCost() { return spawnerTokenCost; }

    public double[] getEssenceMultiLVL() { return essenceMultiLVL; }
    public double[] getMoneyMultiLVL() { return moneyMultiLVL; }

    public double[] getTokenChance() { return tokenChance; }
    public double[] getSpawnerChanceLVL() { return spawnerChanceLVL; }
    public double[] getKeyChanceLVL() { return keyChanceLVL; }

    public Integer getSpeedMax() {
        return getClassCostConfig().getSpeedTokenCost().length;
    }
    public Integer getTokenMax() {
        return getClassCostConfig().getTokenTokenCost().length;
    }
    public Integer getSpawnerMax() {
        return getClassCostConfig().getSpawnerTokenCost().length;
    }
    public Integer getEssenceMax() {
        return getClassCostConfig().getEssenceTokenCost().length;
    }
    public Integer getHasteMax() {
        return getClassCostConfig().getHasteTokenCost().length;
    }
    public Integer getKeyMax() {
        return getClassCostConfig().getKeyTokenCost().length;
    }
    public Integer getSaturationMax() {
        return getClassCostConfig().getSaturationTokenCost().length;
    }
    public Integer getMoneyMax() {
        return getClassCostConfig().getMoneyTokenCost().length;
    }


    private File jsonCostConfigFile;

    public void createJsonCostConfig() {
        if (FactionHandHoes.getHandHoes().getConfigFolder().exists()) {
            jsonCostConfigFile = getJsonCostConfigFile();
            if (!jsonCostConfigFile.exists()) {
                try {
                    jsonCostConfigFile.createNewFile();
                    JsonCostConfig jsonCostConfig = new JsonCostConfig();
                    PrintWriter pw = new PrintWriter(jsonCostConfigFile, "UTF-8");
                    String jsonString = Utils.gson.toJson(jsonCostConfig);
                    pw.print(jsonString);
                    pw.flush();
                    pw.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public JsonCostConfig getClassCostConfig() {
        if (FactionHandHoes.getHandHoes().getConfigFolder().exists()) {
            jsonCostConfigFile = getJsonCostConfigFile();
            if (jsonCostConfigFile.exists()) {
                try {
                    String configJson = new String(Files.readAllBytes(jsonCostConfigFile.toPath()), StandardCharsets.UTF_8);
                    return Utils.gson.fromJson(configJson, JsonCostConfig.class);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return new JsonCostConfig();
    }
    public File getJsonCostConfigFile() {
        return new File(FactionHandHoes.getHandHoes().getConfigFolder(),"costconfig.json");
    }
}
