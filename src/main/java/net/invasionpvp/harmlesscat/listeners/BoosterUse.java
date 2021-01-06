package net.invasionpvp.harmlesscat.listeners;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import de.tr7zw.nbtapi.NBTItem;
import net.invasionpvp.harmlesscat.FactionHandHoes;
import net.invasionpvp.harmlesscat.boosters.EssenceBooster;
import net.invasionpvp.harmlesscat.boosters.MoneyBooster;
import net.invasionpvp.harmlesscat.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class BoosterUse implements Listener {

    @EventHandler
    public void onUse(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getAction() != Action.RIGHT_CLICK_AIR) {
            if (!MPlayer.get(event.getPlayer()).getFaction().getId().equals(Factions.ID_NONE)) {
                if (event.getItem() != null) {
                    if (event.getPlayer().getItemInHand() != null) {
                        if (event.getPlayer().getItemInHand().getType() != Material.AIR) {
                            NBTItem nbtItem = new NBTItem(event.getItem());
                            if (nbtItem.hasNBTData()) {
                                if (nbtItem.getString("id").equals("&4%2")) {
                                    if (nbtItem.getBoolean("isbooster")) {
                                        if (nbtItem.getString("booster").equals("essence")) {
                                            if (!FactionHandHoes.getHandHoes().getSellClass().boosterEEnabled(MPlayer.get(event.getPlayer()).getFaction()) ||
                                                    FactionHandHoes.getHandHoes().getSellClass().boosterEEnabled(MPlayer.get(event.getPlayer()).getFaction()) == null) {
                                                setBoosterTask(MPlayer.get(
                                                        event.getPlayer()).getFaction(),
                                                        "essence",
                                                        nbtItem.getDouble("multiplier"),
                                                        nbtItem.getInteger("length"));
                                                removeBooster(event.getPlayer(),"essence");
                                            } else {
                                                Utils.sendFMessage(event.getPlayer(), FactionHandHoes.getHandHoes().getJsonConfig().getClassMessageConfig().getBoosterAlreadyActiveMessage());
                                            }
                                        } else if (nbtItem.getString("booster").equals("money")) {
                                            if (!FactionHandHoes.getHandHoes().getSellClass().boosterMEnabled(MPlayer.get(event.getPlayer()).getFaction()) ||
                                                    FactionHandHoes.getHandHoes().getSellClass().boosterMEnabled(MPlayer.get(event.getPlayer()).getFaction()) == null) {
                                                setBoosterTask(MPlayer.get(
                                                        event.getPlayer()).getFaction(),
                                                        "money",
                                                        nbtItem.getDouble("multiplier"),
                                                        nbtItem.getInteger("length"));
                                                removeBooster(event.getPlayer(),"money");
                                            } else {
                                                Utils.sendFMessage(event.getPlayer(), FactionHandHoes.getHandHoes().getJsonConfig().getClassMessageConfig().getBoosterAlreadyActiveMessage());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void setBoosterTask(Faction faction, String type, Double multiplier, int length) {
        if (type.equals("essence")) {
            EssenceBooster booster = new EssenceBooster();
            booster.enabled = true;
            booster.multiplier = multiplier;
            booster.owner = faction;
            booster.length = length;
            FactionHandHoes.getHandHoes().getSellClass().putEssenceB(faction,booster);
            Bukkit.getScheduler().scheduleSyncDelayedTask(
                    FactionHandHoes.getHandHoes(),
                    FactionHandHoes.getHandHoes().getRunnableTask().setBoosterTask(faction,"essence"),
                    length*20*60);
            for (Player player : faction.getOnlinePlayers()) {
                player.sendMessage(
                        Utils.col(FactionHandHoes.getHandHoes().getJsonConfig().getClassMessageConfig().getBoosterActivatedMessage()[0].replace("%booster%","Essence"))
                );
            }
        } else if (type.equals("money")) {
            MoneyBooster booster = new MoneyBooster();
            booster.enabled = true;
            booster.multiplier = multiplier;
            booster.owner = faction;
            booster.length = length;
            FactionHandHoes.getHandHoes().getSellClass().putMoneyB(faction,booster);
            Bukkit.getScheduler().scheduleSyncDelayedTask(
                    FactionHandHoes.getHandHoes(),
                    FactionHandHoes.getHandHoes().getRunnableTask().setBoosterTask(faction,"money"),
                    length*20*60);
            for (Player player : faction.getOnlinePlayers()) {
                player.sendMessage(
                        Utils.col(FactionHandHoes.getHandHoes().getJsonConfig().getClassMessageConfig().getBoosterActivatedMessage()[0].replace("%booster%","Money"))
                );
            }
        }
    }

    public void removeBooster(Player player, String type) {
        for (int i = 0; i < player.getInventory().getSize();i++) {
            if (player.getInventory().getItem(i) != null) {
                if (player.getInventory().getItem(i).getType() != Material.AIR) {
                    NBTItem nbtItem = new NBTItem(player.getInventory().getItem(i));
                    if (nbtItem.hasNBTData()) {
                        if (nbtItem.getString("id").equals("&4%2")) {
                            if (nbtItem.getBoolean("isbooster")) {
                                if (nbtItem.getString("booster").equalsIgnoreCase(type)) {
                                    int amount = player.getInventory().getItem(i).getAmount();
                                    if (amount > i) {
                                        ItemStack item = player.getInventory().getItem(i);
                                        item.setAmount(amount-1);
                                        player.getInventory().setItem(i, item);
                                    } else {
                                        player.getInventory().setItem(i, new ItemStack(Material.AIR, 0));
                                    }
                                    player.updateInventory();
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void removeToken(Player player, String type) {
        for (int i = 0; i < player.getInventory().getSize();i++) {
            if (player.getInventory().getItem(i) != null) {
                if (player.getInventory().getItem(i).getType() != Material.AIR) {
                    NBTItem nbtItem = new NBTItem(player.getInventory().getItem(i));
                    if (nbtItem.hasNBTData()) {
                        if (nbtItem.getString("id").equals("&4%2")) {
                            if (type != null) {
                                //max, one
                                if (nbtItem.getString("tokentype").equals("max")) {
                                    int amount = player.getInventory().getItem(i).getAmount();
                                    if (amount > i) {
                                        ItemStack item = player.getInventory().getItem(i);
                                        item.setAmount(amount-1);
                                        player.getInventory().setItem(i, item);
                                    } else {
                                        player.getInventory().setItem(i, new ItemStack(Material.AIR, 0));
                                    }
                                    player.updateInventory();
                                    break;
                                } else if (nbtItem.getString("tokentype").equals("one")) {
                                    int amount = player.getInventory().getItem(i).getAmount();
                                    if (amount > i) {
                                        ItemStack item = player.getInventory().getItem(i);
                                        item.setAmount(amount-1);
                                        player.getInventory().setItem(i, item);

                                    } else {
                                        player.getInventory().setItem(i, new ItemStack(Material.AIR, 0));
                                    }
                                    player.updateInventory();
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
