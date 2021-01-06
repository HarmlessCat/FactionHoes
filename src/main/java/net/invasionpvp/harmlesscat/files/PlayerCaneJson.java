package net.invasionpvp.harmlesscat.files;

import net.invasionpvp.harmlesscat.FactionHandHoes;
import net.invasionpvp.harmlesscat.files.faction.JsonFactionUpgrade;
import net.invasionpvp.harmlesscat.files.faction.itemsConfig.SaturationItem;
import net.invasionpvp.harmlesscat.utils.Utils;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class PlayerCaneJson {

    public int amt = 0;
    public int getAmt() { return amt; }

    private File caneFile;

    public void createPlayerCaneJson(Player player) {
        if (FactionHandHoes.getHandHoes().getConfigFolder().exists()) {
            caneFile = getCaneFile(player.getUniqueId().toString());
            if (!caneFile.exists()) {
                try {
                    caneFile.createNewFile();
                    PlayerCaneJson playerCaneJson = new PlayerCaneJson();
                    PrintWriter pw = new PrintWriter(caneFile, "UTF-8");
                    String jsonString = Utils.gson.toJson(playerCaneJson);
                    pw.print(jsonString);
                    pw.flush();
                    pw.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public PlayerCaneJson getPlayerCaneJson(Player player) {
        caneFile = getCaneFile(player.getUniqueId().toString());
        if (caneFile.exists()) {
            try {
                String stringJson = new String(Files.readAllBytes(caneFile.toPath()), StandardCharsets.UTF_8);
                return Utils.gson.fromJson(stringJson, PlayerCaneJson.class);
            } catch (IOException ex) {
                return new PlayerCaneJson();
            }
        }
        return new PlayerCaneJson();
    }

    public PlayerCaneJson getPlayerCaneJson(String uuid) {
        caneFile = getCaneFile(uuid);
        if (caneFile.exists()) {
            try {
                String stringJson = new String(Files.readAllBytes(caneFile.toPath()), StandardCharsets.UTF_8);
                return Utils.gson.fromJson(stringJson, PlayerCaneJson.class);
            } catch (IOException ex) {
                return new PlayerCaneJson();
            }
        }
        return new PlayerCaneJson();
    }

    public void addAmt(Player player, int increase) {
        caneFile = getCaneFile(player.getUniqueId().toString());
        if (caneFile.exists()) {
            try {
                PlayerCaneJson upgrades = getPlayerCaneJson(player);
                upgrades.amt = upgrades.getAmt()+increase;
                PrintWriter pw = new PrintWriter(caneFile, "UTF-8");
                String jsonString = Utils.gson.toJson(upgrades);
                pw.print(jsonString);
                pw.flush();
                pw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            createPlayerCaneJson(player);
            addAmt(player, increase);
        }
    }

    public File getCaneFile(String string) {
        return new File(FactionHandHoes.handHoes.getPlayerDataFolder(), string+".json");
    }
}
