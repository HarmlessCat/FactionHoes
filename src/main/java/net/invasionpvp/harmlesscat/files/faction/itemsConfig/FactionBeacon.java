package net.invasionpvp.harmlesscat.files.faction.itemsConfig;

import net.invasionpvp.harmlesscat.FactionHandHoes;
import net.invasionpvp.harmlesscat.utils.Utils;
import org.bukkit.Material;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class FactionBeacon {

    public int index;
    public String title;
    public String[] lore;
    public String itemType;

    public FactionBeacon() {
        index = 31;
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

    public Material getMaterial() {
        if (Material.getMaterial(getClassFactionItem().getItemType()) != null) {
            return Material.getMaterial(getClassFactionItem().getItemType());
        }
        return Material.DIRT;
    }

    public File beaconJsonFile;

    public void createFactionItemJson() {
        if (FactionHandHoes.getHandHoes().getConfigFolder().exists()) {
            beaconJsonFile = getFactionItemJson();
            if (!beaconJsonFile.exists()) {
                try {
                    beaconJsonFile.createNewFile();
                    FactionBeacon factionBeacon = new FactionBeacon();
                    PrintWriter pw = new PrintWriter(beaconJsonFile, "UTF-8");
                    String jsonString = Utils.gson.toJson(factionBeacon);
                    pw.print(jsonString);
                    pw.flush();
                    pw.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public FactionBeacon getClassFactionItem() {
        beaconJsonFile = getFactionItemJson();
        if (beaconJsonFile.exists()) {
            try {
                String stringJson = new String(Files.readAllBytes(beaconJsonFile.toPath()), StandardCharsets.UTF_8);
                return Utils.gson.fromJson(stringJson, FactionBeacon.class);
            } catch (IOException ex) {
                return new FactionBeacon();
            }
        }
        return new FactionBeacon();
    }

    public File getFactionItemJson() {
        return new File(FactionHandHoes.getHandHoes().getConfigFolder(),"factionItem.json");
    }
}
