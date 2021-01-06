package net.invasionpvp.harmlesscat.listeners;

import com.cryptomorin.xseries.XMaterial;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import de.tr7zw.nbtapi.NBTItem;
import net.invasionpvp.harmlesscat.FactionHandHoes;
import net.invasionpvp.harmlesscat.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

public class PlayerListeners implements Listener {

    @EventHandler
    public  void onDeath(PlayerDeathEvent event) {
        if (!event.getKeepInventory()) {
            if (event.getEntity() != null) {
                for (ItemStack itemStack : event.getDrops().toArray(new ItemStack[0])) {
                    if (itemStack != null) {
                        if (itemStack.getType() != Material.AIR) {
                            NBTItem nbt = new NBTItem(itemStack);
                            if (nbt.hasNBTData()) {
                                if (nbt.getString("id").equals("&4%2")) {
                                    if (nbt.getBoolean("hoe")) {
                                        event.getDrops().remove(itemStack);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!FactionHandHoes.getHandHoes().getPlayerCaneJson().getCaneFile(event.getPlayer().getUniqueId().toString()).exists()) {
            FactionHandHoes.getHandHoes().getPlayerCaneJson().createPlayerCaneJson(event.getPlayer());
        }
        if (MPlayer.get(event.getPlayer()).getFaction() != null) {
            if (!MPlayer.get(event.getPlayer()).getFaction().getId().equals(Factions.ID_NONE)) {
                if (!FactionHandHoes.getHandHoes().getJsonFactionUpgrade().getJsonUpgradeFile(MPlayer.get(event.getPlayer()).getFaction().getId()).exists()) {
                    FactionHandHoes.getHandHoes().getJsonFactionUpgrade().createJsonFactionUpgrade(MPlayer.get(event.getPlayer()).getFaction().getId());
                }
                FactionHandHoes.getHandHoes().getCaneMode().putFaction(MPlayer.get(event.getPlayer()).getFaction());
                FactionHandHoes.getHandHoes().getCaneListener().putPlayer(event.getPlayer());
            }
        }
    }

    @EventHandler
    public void onHoeHeldEvent(PlayerItemHeldEvent event) {
        if (event.getPlayer().getInventory().getItem(event.getNewSlot()) != null) {
            if (event.getPlayer().getInventory().getItem(event.getNewSlot()).getType() == Material.DIAMOND_HOE) {
                NBTItem nbtItem = new NBTItem(event.getPlayer().getInventory().getItem(event.getNewSlot()));
                if (nbtItem.hasNBTData()) {
                    if (nbtItem.getString("id").equals("&4%2")) {
                        if (nbtItem.getBoolean("hoe")) {
                            FactionHandHoes.getHandHoes().getCaneMode().putFaction(MPlayer.get(event.getPlayer()).getFaction());
                            FactionHandHoes.getHandHoes().getCaneListener().putPlayer(event.getPlayer());
                            FactionHandHoes.getHandHoes().getCaneListener().toggleCaneMode(event.getPlayer(), true);
                        }
                    }
                }
            } else {
                if (event.getPlayer().getInventory().getItem(event.getPreviousSlot()) != null) {
                    if (event.getPlayer().getInventory().getItem(event.getPreviousSlot()).getType() == Material.DIAMOND_HOE) {
                        NBTItem nbtItem = new NBTItem(event.getPlayer().getInventory().getItem(event.getPreviousSlot()));
                        if (nbtItem.hasNBTData()) {
                            if (nbtItem.getString("id").equals("&4%2")) {
                                if (nbtItem.getBoolean("hoe")) {
                                    if (FactionHandHoes.getHandHoes().getCaneListener().getCaneBroken().get(event.getPlayer().getUniqueId()) != null) {
                                        if (FactionHandHoes.getHandHoes().getCaneListener().getPlayerRunnable().get(event.getPlayer().getUniqueId()).getTaskId() != null) {
                                            Bukkit.getScheduler().cancelTask(FactionHandHoes.getHandHoes().getCaneListener().getPlayerRunnable().get(event.getPlayer().getUniqueId()).getTaskId());
                                        }
                                        FactionHandHoes.getHandHoes().getCaneListener().toggleCaneMode(event.getPlayer(), false);
                                        event.getPlayer().sendTitle(Utils.col(
                                                FactionHandHoes.getHandHoes().getJsonConfig().getClassMessageConfig().getCaneBroadcastMessage().replace("%cane%",FactionHandHoes.getHandHoes().getCaneListener().getCaneBroken().get(event.getPlayer().getUniqueId()).toString())),
                                                "");
                                    }
                                    FactionHandHoes.getHandHoes().getSellClass().sellCane(event.getPlayer());
                                    FactionHandHoes.getHandHoes().getCaneListener().resetPlayer(event.getPlayer());
                                }
                            }
                        }
                    }
                }
            }
        } else {
            if (event.getPlayer().getInventory().getItem(event.getPreviousSlot()) != null) {
                if (event.getPlayer().getInventory().getItem(event.getPreviousSlot()).getType() == Material.DIAMOND_HOE) {
                    NBTItem nbtItem = new NBTItem(event.getPlayer().getInventory().getItem(event.getPreviousSlot()));
                    if (nbtItem.hasNBTData()) {
                        if (nbtItem.getString("id").equals("&4%2")) {
                            if (nbtItem.getBoolean("hoe")) {
                                if (FactionHandHoes.getHandHoes().getCaneListener().getCaneBroken().get(event.getPlayer().getUniqueId()) != null) {
                                    if (FactionHandHoes.getHandHoes().getCaneListener().getPlayerRunnable().get(event.getPlayer().getUniqueId()).getTaskId() != null) {
                                        Bukkit.getScheduler().cancelTask(FactionHandHoes.getHandHoes().getCaneListener().getPlayerRunnable().get(event.getPlayer().getUniqueId()).getTaskId());
                                    }
                                    FactionHandHoes.getHandHoes().getCaneListener().toggleCaneMode(event.getPlayer(), false);
                                    event.getPlayer().sendTitle(Utils.col(
                                            FactionHandHoes.getHandHoes().getJsonConfig().getClassMessageConfig().getCaneBroadcastMessage().replace("%cane%",FactionHandHoes.getHandHoes().getCaneListener().getCaneBroken().get(event.getPlayer().getUniqueId()).toString())),
                                            "");
                                }
                                FactionHandHoes.getHandHoes().getSellClass().sellCane(event.getPlayer());
                                FactionHandHoes.getHandHoes().getCaneListener().resetPlayer(event.getPlayer());
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void playerOpenInventory(InventoryOpenEvent event) {
        FactionHandHoes.getHandHoes().getCaneListener().toggleCaneMode((Player) event.getPlayer(), false);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        FactionHandHoes.getHandHoes().getCaneListener().removePlayer(event.getPlayer());
        Player player = event.getPlayer();
        if (MPlayer.get(player).getFaction() != null) {
            if (!MPlayer.get(player).getFaction().getId().equals(Factions.ID_NONE)) {
                FactionHandHoes.getHandHoes().getCaneListener().toggleCaneMode(player,false);
            }
        }
        FactionHandHoes.getHandHoes().getPlayerCommands().removeHoe(event.getPlayer());
    }


    @EventHandler
    public void onRightClickEvent(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR) {
            if (MPlayer.get(event.getPlayer()) != null) {
                if (MPlayer.get(event.getPlayer()).getFaction() != null) {
                    if (!MPlayer.get(event.getPlayer()).getFaction().getId().equals(Factions.ID_NONE)) {
                        if (event.getPlayer().getItemInHand() != null) {
                            if (event.getPlayer().getItemInHand().getType() != null) {
                                if (event.getPlayer().getItemInHand().getType() != Material.AIR) {
                                    NBTItem item = new NBTItem(event.getPlayer().getItemInHand());
                                    if (item.getString("id").equalsIgnoreCase("&4%2")) {
                                        if (item.getBoolean("hoe")) {
                                            FactionHandHoes.getHandHoes().getFactionUpgradeGui().openGui(event.getPlayer());
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

    @EventHandler
    public void onDropEvent(PlayerDropItemEvent event) {
        if (event.getItemDrop() != null) {
            if (event.getItemDrop().getItemStack() != null) {
                if (event.getItemDrop().getItemStack().getType() != null) {
                    if (event.getItemDrop().getItemStack().getType() != Material.AIR) {
                        if (event.getItemDrop().getItemStack().getType() == Material.DIAMOND_HOE) {
                            NBTItem nbtItem = new NBTItem(event.getItemDrop().getItemStack());
                            if (nbtItem.getString("id").equalsIgnoreCase("&4%2")) {
                                if (nbtItem.getBoolean("hoe")) {
                                    event.setCancelled(true);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerRemoveHoe(InventoryClickEvent event) {
        if (event.getCurrentItem() != null) {
            if (event.getCurrentItem().getType() != Material.AIR) {
                NBTItem item = new NBTItem(event.getCurrentItem());
                if (item.getString("id").equals("&4%2")) {
                    if (item.getBoolean("hoe")) {
                        if (event.getView().getType() != InventoryType.CRAFTING ) {
                            event.setCancelled(true);
                        }
                    }
                }
            } else {
                for (int i = 0; i < event.getWhoClicked().getInventory().getSize(); i++) {
                    if (event.getWhoClicked().getInventory().getItem(i) != null) {
                        NBTItem item = new NBTItem(event.getWhoClicked().getInventory().getItem(i));
                        if (item.getString("id").equals("&4%2")) {
                            if (item.getBoolean("hoe")) {
                                if (event.getClick() == ClickType.NUMBER_KEY) {
                                    event.setCancelled(true);
                                }
                            }
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < event.getWhoClicked().getInventory().getSize(); i++) {
                if (event.getWhoClicked().getInventory().getItem(i) != null) {
                    NBTItem item = new NBTItem(event.getWhoClicked().getInventory().getItem(i));
                    if (item.getString("id").equals("&4%2")) {
                        if (item.getBoolean("hoe")) {
                            if (event.getClick() == ClickType.NUMBER_KEY) {
                                event.setCancelled(true);
                            }
                        }
                    }
                }
            }
        }
    }
}
