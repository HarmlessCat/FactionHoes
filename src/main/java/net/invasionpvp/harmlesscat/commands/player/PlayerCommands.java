package net.invasionpvp.harmlesscat.commands.player;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.entity.MPlayerColl;
import de.tr7zw.nbtapi.NBTItem;
import net.invasionpvp.harmlesscat.FactionHandHoes;
import net.invasionpvp.harmlesscat.files.JsonConfig;
import net.invasionpvp.harmlesscat.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.rmi.CORBA.Util;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

public class PlayerCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player pSender = (Player) sender;
            if (MPlayer.get(pSender).getFaction() != null) {
                if (!MPlayer.get(pSender).getFaction().getId().equals(Factions.ID_NONE)) {
                    //if (has perms)
                    if (args.length == 1) {
                        switch (args[0].toLowerCase()) {
                            case "upgrade":
                                FactionHandHoes.getHandHoes().getFactionUpgradeGui().openGui(pSender);
                                break;
                            case "shop":

                                break;
                            default:
                                Utils.sendFMessage(pSender, FactionHandHoes.getHandHoes().getJsonConfig().getClassMessageConfig().getErrorMessage());
                                break;
                        }
                    } else if (args.length == 0) {
                        if (hasHoe(pSender)) {
                            removeHoe(pSender);
                            FactionHandHoes.getHandHoes().getCaneListener().resetPlayer(pSender.getPlayer());
                        } else {
                            if (hasAvaliableSlot(pSender)) {
                                addHoe(pSender);
                                enableHoeClass(pSender);
                            } else {
                                Utils.sendFMessage(pSender,FactionHandHoes.getHandHoes().getJsonConfig().getClassMessageConfig().getInventoryFullMessage());
                            }
                        }
                        //FactionHandHoes.getHandHoes().getCaneListener().toggleCaneMode(pSender,null);
                    } else {
                        Utils.sendFMessage(pSender,FactionHandHoes.getHandHoes().getJsonConfig().getClassMessageConfig().getErrorMessage());
                    }
                }  else {
                    Utils.sendFMessage(pSender,FactionHandHoes.getHandHoes().getJsonConfig().getClassMessageConfig().getNoFactionFound());
                }
            } else {
                Utils.sendFMessage(pSender,FactionHandHoes.getHandHoes().getJsonConfig().getClassMessageConfig().getNoFactionFound());
            }
        }
        return false;
    }

    public void enableHoeClass(Player player) {
        if (player.getItemInHand() != null) {
            if (player.getItemInHand().getType() != null) {
                if (player.getItemInHand().getType() == Material.DIAMOND_HOE) {
                    NBTItem hoeNbt = new NBTItem(player.getItemInHand());
                    if (hoeNbt.getString("id").equals("&4%2")) {
                        if (hoeNbt.getBoolean("hoe")) {
                            FactionHandHoes.getHandHoes().getCaneMode().putFaction(MPlayer.get(player).getFaction());
                            FactionHandHoes.getHandHoes().getCaneListener().putPlayer(player);
                            FactionHandHoes.getHandHoes().getCaneListener().toggleCaneMode(player, true);
                        }
                    }
                }
            }
        }
    }

    public boolean hasAvaliableSlot(Player player){
        Inventory inv = player.getInventory();
        boolean check = false;
        for (ItemStack item: inv.getContents()) {
            if(item == null) {
                check = true;
                break;
            }
        }

        return check;
    }

    public void caneTop(Player sender) {
        TreeMap<String,Integer> playerCaneAmt = new TreeMap<>();
        for (OfflinePlayer player : Bukkit.getServer().getOfflinePlayers()) {
            if (player.hasPlayedBefore()) {
                File playerFile = new File(FactionHandHoes.handHoes.getPlayerDataFolder(), player.getUniqueId().toString()+".json");
                if (playerFile.exists()) {
                    playerCaneAmt.put(player.getName(), FactionHandHoes.getHandHoes().getPlayerCaneJson().getPlayerCaneJson(player.getUniqueId().toString()).getAmt());
                }
            }
        }
        Map<String, Integer> sorted = playerCaneAmt
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
        sender.sendMessage(Utils.col(FactionHandHoes.getHandHoes().getJsonConfig().getClassMessageConfig().getCaneTopBase()));
        int size = 10;
        int nr = 0;
        for (String string : sorted.keySet()) {
            nr++;
            if (nr < size) {
                String base = FactionHandHoes.getHandHoes().getJsonConfig().getClassMessageConfig().getCaneTopLayout();
                base = base.replace("%player%",string);
                base = base.replace("%caneamt%",sorted.get(string).toString());
                base = base.replace("%nr%",nr+"");
                sender.sendMessage(Utils.col(base));
            } else {
                break;
            }
        }
    }

    public boolean hasHoe(Player player) {
        for (int i = 0; i < player.getInventory().getSize(); i++) {
            if (player.getInventory().getItem(i) != null) {
                if (player.getInventory().getItem(i).getType() != Material.AIR) {
                    if (player.getInventory().getItem(i).getType() == Material.DIAMOND_HOE) {
                        NBTItem hoeNbt = new NBTItem(player.getInventory().getItem(i));
                        if (hoeNbt.getString("id").equals("&4%2")) {
                            if (hoeNbt.getBoolean("hoe")) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public void removeHoe(Player player) {
        if (player != null) {
            if (player.getItemInHand() != null) {
                if (player.getItemInHand().getType() == Material.DIAMOND_HOE) {
                    NBTItem hoeNbt = new NBTItem(player.getItemInHand());
                    if (hoeNbt.getString("id").equals("&4%2")) {
                        if (hoeNbt.getBoolean("hoe")) {
                            FactionHandHoes.getHandHoes().getCaneListener().toggleCaneMode(player, false);
                        }
                    }
                }
            }
            for (int i = 0; i < player.getInventory().getSize(); i++) {
                if (player.getInventory().getItem(i) != null) {
                    if (player.getInventory().getItem(i).getType() != Material.AIR) {
                        if (player.getInventory().getItem(i).getType() == Material.DIAMOND_HOE) {
                            NBTItem hoeNbt = new NBTItem(player.getInventory().getItem(i));
                            if (hoeNbt.getString("id").equals("&4%2")) {
                                if (hoeNbt.getBoolean("hoe")) {
                                    player.getInventory().setItem(i, new ItemStack(Material.AIR));
                                    player.updateInventory();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void addHoe(Player player) {
        player.getInventory().addItem(
                FactionHandHoes.getHandHoes().getHarvesterHoe().getClassHoeItem().getFactionHoe(1, MPlayer.get(player).getFaction()));
        player.updateInventory();
    }
}
