package net.invasionpvp.harmlesscat.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.entity.MPlayer;
import me.clip.placeholderapi.PlaceholderAPI;
import net.invasionpvp.harmlesscat.FactionHandHoes;
import net.invasionpvp.harmlesscat.files.JsonConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class Utils {

    public static Random rand = new Random();

    public static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static String col(String text) {
        return ChatColor.translateAlternateColorCodes('&',text);
    }

    public static String[] col(String[] text) {
        ArrayList<String> colText = new ArrayList<>();
        for (String message : text) {
            colText.add(col(message));
        }
        return colText.toArray(new String[0]);
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException | NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;

    }

    public static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
        } catch(NumberFormatException | NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    public static void sendFMessage(Player sender, String[] message) {
        if (Utils.getBooleanFromString(message[1])) {
            if (Utils.getBooleanFromString(message[2])) {
                if (MPlayer.get(sender).getFaction() != null) {
                    if (!MPlayer.get(sender).getFaction().getId().equals(Factions.ID_NONE)) {
                        for (Player members : MPlayer.get(sender).getFaction().getOnlinePlayers()) {
                            members.sendMessage(PlaceholderAPI.setPlaceholders(sender,message[0]));
                        }
                    } else {
                        sender.sendMessage(PlaceholderAPI.setPlaceholders(sender,message[0]));
                    }
                } else {
                    sender.sendMessage(PlaceholderAPI.setPlaceholders(sender,message[0]));
                }
            } else {
                sender.sendMessage(PlaceholderAPI.setPlaceholders(sender,message[0]));
            }
        }
    }

    public static void sendFMessage(CommandSender sender, String[] message) {
        if (Utils.getBooleanFromString(message[1])) {
            sender.sendMessage(Utils.col(message[0]));
        }
    }

    public static boolean convertToBoolean(String value) {
        boolean returnValue = false;
        if ("1".equalsIgnoreCase(value) || "yes".equalsIgnoreCase(value) ||
                "true".equalsIgnoreCase(value) || "on".equalsIgnoreCase(value))
            returnValue = true;
        return returnValue;
    }

    public static Boolean getBooleanFromString(String bool) {
        return Boolean.valueOf(bool);
    }

    public static void runKey(Player player, int runs) {
        if (runs != 0) {
            for (int i = 0; i < runs; i++) {
                int x = rand.nextInt(100 + 1);
                if (x < 60) {
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "crate give "+player.getName()+" Classic 1");
                } else if (x < 90 && x > 60) {
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "crate give "+player.getName()+" Ancient 1");
                } else if (x < 97 && x > 90) {
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "crate give "+player.getName()+" Invasion 1");
                } else if (x < 100 && x > 97) {
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "crate give "+player.getName()+" Perks 1");
                }
            }
        }
    }

    public static void runSpawner(Player player,int runs) {
        if (runs != 0) {
            for (int i = 0; i < runs; i++) {
                int x = rand.nextInt(100 + 1);
                if (x < 60) {
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "ss give "+player.getName()+" skeleton 1");
                } else if (x < 90 && x > 60) {
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "ss give "+player.getName()+" blaze 1");
                } else if (x < 97 && x > 90) {
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "ss give "+player.getName()+" silverfish 1");
                } else if (x < 100 && x > 97) {
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "ss give "+player.getName()+" witch 1");
                }
            }
        }
    }

    public static String getCost(Integer cost) {
        if (cost != null) {
            if ((cost%1000000)!=cost) {
                return cost/1000000+"&fm";
            }
            if ((cost%1000)!=cost){
                return cost/1000+"&fk";
            }
        } else {
            return null;
        }
        return cost.toString();
    }
}
