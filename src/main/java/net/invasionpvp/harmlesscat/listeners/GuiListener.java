package net.invasionpvp.harmlesscat.listeners;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import de.tr7zw.nbtapi.NBTItem;
import net.invasionpvp.harmlesscat.FactionHandHoes;
import net.invasionpvp.harmlesscat.files.JsonConfig;
import net.invasionpvp.harmlesscat.files.JsonCostConfig;
import net.invasionpvp.harmlesscat.files.faction.JsonFactionUpgrade;
import net.invasionpvp.harmlesscat.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class GuiListener implements Listener {

    @EventHandler
    public void onGuiUse(InventoryClickEvent event) {
        if (event.getView() != null) {
            if (event.getView().getTopInventory().getName().equals(
                    Utils.col(FactionHandHoes.getHandHoes().getJsonConfig().getFactionHoeTitle()
                    ))) {
                MPlayer mPlayer = MPlayer.get(event.getWhoClicked());
                if (event.getCurrentItem() != null) {
                    if (event.getCurrentItem().getType() != Material.AIR) {
                        if (event.getClickedInventory().getName().equals(Utils.col(FactionHandHoes.getHandHoes().getJsonConfig().getFactionHoeTitle()))) {
                            event.setCancelled(true);
                            if (event.getClickedInventory().getType() == InventoryType.CHEST) {
                                if (hasToken((Player) event.getWhoClicked())) {
                                    if (getTokenIndex(mPlayer.getPlayer())!=null) {
                                        NBTItem nbtItem = new NBTItem(event.getWhoClicked().getInventory().getItem(getTokenIndex(mPlayer.getPlayer())));
                                        if (new NBTItem(event.getCurrentItem()).hasNBTData()) {
                                            NBTItem guiItem = new NBTItem(event.getCurrentItem());
                                            if (guiItem.getBoolean("guiitem")) {
                                                JsonConfig jsonMessageConfig = FactionHandHoes.getHandHoes().getJsonConfig().getClassMessageConfig();
                                                JsonFactionUpgrade factionUpgrade =
                                                        FactionHandHoes.getHandHoes().getJsonFactionUpgrade().getClassFactionUpgrade(MPlayer.get(mPlayer.getPlayer()).getFaction().getId());
                                                if (event.getCurrentItem().getType() == FactionHandHoes.getHandHoes().getHasteItem().getClassHasteItem().getHasteMaterial()) {
                                                    if (factionUpgrade.getHasteFactionLVL() < FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getHasteMax()) {
                                                        if (nbtItem.getString("tokentype").equals("one")) {
                                                            FactionHandHoes.getHandHoes().getJsonFactionUpgrade().upgradeHaste(
                                                                    mPlayer.getFaction().getId(),
                                                                    1,
                                                                    false);
                                                            FactionHandHoes.getHandHoes().getBoosterUse().removeToken(mPlayer.getPlayer(),"one");
                                                            upgradeWorked(mPlayer.getFaction(),"Haste");
                                                            mPlayer.getPlayer().closeInventory();
                                                            mPlayer.getPlayer().openInventory(FactionHandHoes.getHandHoes().getFactionUpgradeGui().getUpgradeGui(mPlayer.getPlayer()));
                                                        } else if (nbtItem.getString("tokentype").equals("max")) {
                                                            FactionHandHoes.getHandHoes().getJsonFactionUpgrade().upgradeHaste(
                                                                    mPlayer.getFaction().getId(),
                                                                    null,
                                                                    true);
                                                            FactionHandHoes.getHandHoes().getBoosterUse().removeToken(mPlayer.getPlayer(),"max");
                                                            upgradeWorked(mPlayer.getFaction(),"Haste");
                                                            mPlayer.getPlayer().closeInventory();
                                                            mPlayer.getPlayer().openInventory(FactionHandHoes.getHandHoes().getFactionUpgradeGui().getUpgradeGui(mPlayer.getPlayer()));
                                                        }
                                                    } else {
                                                        Utils.sendFMessage(mPlayer.getPlayer(), jsonMessageConfig.getMaxLvlReachedMessage());
                                                    }
                                                } else if (event.getCurrentItem().getType() == FactionHandHoes.getHandHoes().getTokenItem().getClassTokenItem().getTokenMaterial()) {
                                                    if (factionUpgrade.getTokenFactionLVL() < FactionHandHoes.getHandHoes().getJsonCostConfig().getTokenMax()) {
                                                        if (nbtItem.getString("tokentype").equals("one")) {
                                                            FactionHandHoes.getHandHoes().getJsonFactionUpgrade().upgradeToken(
                                                                    mPlayer.getFaction().getId(),
                                                                    1,
                                                                    false);
                                                            FactionHandHoes.getHandHoes().getBoosterUse().removeToken(mPlayer.getPlayer(),"one");
                                                            upgradeWorked(mPlayer.getFaction(),"Token");
                                                            mPlayer.getPlayer().closeInventory();
                                                            mPlayer.getPlayer().openInventory(FactionHandHoes.getHandHoes().getFactionUpgradeGui().getUpgradeGui(mPlayer.getPlayer()));
                                                        } else if (nbtItem.getString("tokentype").equals("max")) {
                                                            FactionHandHoes.getHandHoes().getJsonFactionUpgrade().upgradeToken(
                                                                    mPlayer.getFaction().getId(),
                                                                    null,
                                                                    true);
                                                            FactionHandHoes.getHandHoes().getBoosterUse().removeToken(mPlayer.getPlayer(),"max");
                                                            upgradeWorked(mPlayer.getFaction(),"Token");
                                                            mPlayer.getPlayer().closeInventory();
                                                            mPlayer.getPlayer().openInventory(FactionHandHoes.getHandHoes().getFactionUpgradeGui().getUpgradeGui(mPlayer.getPlayer()));
                                                        }
                                                    } else {
                                                        Utils.sendFMessage(mPlayer.getPlayer(),jsonMessageConfig.getMaxLvlReachedMessage());
                                                    }
                                                } else if (event.getCurrentItem().getType() == FactionHandHoes.getHandHoes().getMoneyItem().getClassMoneyItem().getMoneyMaterial()) {
                                                    if (factionUpgrade.getMoneyFactionLVL() < FactionHandHoes.getHandHoes().getJsonCostConfig().getMoneyMax()) {
                                                        if (nbtItem.getString("tokentype").equals("one")) {
                                                            FactionHandHoes.getHandHoes().getJsonFactionUpgrade().upgradeMoney(
                                                                    mPlayer.getFaction().getId(),
                                                                    1,
                                                                    false);
                                                            FactionHandHoes.getHandHoes().getBoosterUse().removeToken(mPlayer.getPlayer(),"one");
                                                            upgradeWorked(mPlayer.getFaction(),"Money");
                                                            mPlayer.getPlayer().closeInventory();
                                                            mPlayer.getPlayer().openInventory(FactionHandHoes.getHandHoes().getFactionUpgradeGui().getUpgradeGui(mPlayer.getPlayer()));
                                                        } else if (nbtItem.getString("tokentype").equals("max")) {
                                                            FactionHandHoes.getHandHoes().getJsonFactionUpgrade().upgradeMoney(
                                                                    mPlayer.getFaction().getId(),
                                                                    null,
                                                                    true);
                                                            FactionHandHoes.getHandHoes().getBoosterUse().removeToken(mPlayer.getPlayer(),"max");
                                                            upgradeWorked(mPlayer.getFaction(),"Money");
                                                            mPlayer.getPlayer().closeInventory();
                                                            mPlayer.getPlayer().openInventory(FactionHandHoes.getHandHoes().getFactionUpgradeGui().getUpgradeGui(mPlayer.getPlayer()));
                                                        }
                                                    } else {
                                                        Utils.sendFMessage(mPlayer.getPlayer(),jsonMessageConfig.getMaxLvlReachedMessage());
                                                    }
                                                } else if (event.getCurrentItem().getType() == FactionHandHoes.getHandHoes().getSpeedItem().getClassSpeedItem().getSpeedMaterial()) {
                                                    if (factionUpgrade.getSpeedFactionLVL() < FactionHandHoes.getHandHoes().getJsonCostConfig().getSpeedMax()) {
                                                        if (nbtItem.getString("tokentype").equals("one")) {
                                                            FactionHandHoes.getHandHoes().getJsonFactionUpgrade().upgradeSpeed(
                                                                    mPlayer.getFaction().getId(),
                                                                    1,
                                                                    false);
                                                            FactionHandHoes.getHandHoes().getBoosterUse().removeToken(mPlayer.getPlayer(),"one");
                                                            upgradeWorked(mPlayer.getFaction(),"Speed");
                                                            mPlayer.getPlayer().closeInventory();
                                                            mPlayer.getPlayer().openInventory(FactionHandHoes.getHandHoes().getFactionUpgradeGui().getUpgradeGui(mPlayer.getPlayer()));
                                                        } else if (nbtItem.getString("tokentype").equals("max")) {
                                                            FactionHandHoes.getHandHoes().getJsonFactionUpgrade().upgradeSpeed(
                                                                    mPlayer.getFaction().getId(),
                                                                    null,
                                                                    true);
                                                            FactionHandHoes.getHandHoes().getBoosterUse().removeToken(mPlayer.getPlayer(),"max");
                                                            upgradeWorked(mPlayer.getFaction(),"Speed");
                                                            mPlayer.getPlayer().closeInventory();
                                                            mPlayer.getPlayer().openInventory(FactionHandHoes.getHandHoes().getFactionUpgradeGui().getUpgradeGui(mPlayer.getPlayer()));
                                                        }
                                                    } else {
                                                        Utils.sendFMessage(mPlayer.getPlayer(),jsonMessageConfig.getMaxLvlReachedMessage());
                                                    }
                                                } else if (event.getCurrentItem().getType() == FactionHandHoes.getHandHoes().getSpawnerItem().getClassSpawnerItem().getSpawnerMaterial()) {
                                                    if (factionUpgrade.getSpawnerFactionLVL() < FactionHandHoes.getHandHoes().getJsonCostConfig().getSpawnerMax()) {
                                                        if (nbtItem.getString("tokentype").equals("one")) {
                                                            FactionHandHoes.getHandHoes().getJsonFactionUpgrade().upgradeSpawner(
                                                                    mPlayer.getFaction().getId(),
                                                                    1,
                                                                    false);
                                                            FactionHandHoes.getHandHoes().getBoosterUse().removeToken(mPlayer.getPlayer(),"one");
                                                            upgradeWorked(mPlayer.getFaction(),"Spawner");
                                                            mPlayer.getPlayer().closeInventory();
                                                            mPlayer.getPlayer().openInventory(FactionHandHoes.getHandHoes().getFactionUpgradeGui().getUpgradeGui(mPlayer.getPlayer()));
                                                        } else if (nbtItem.getString("tokentype").equals("max")) {
                                                            FactionHandHoes.getHandHoes().getJsonFactionUpgrade().upgradeSpawner(
                                                                    mPlayer.getFaction().getId(),
                                                                    null,
                                                                    true);
                                                            FactionHandHoes.getHandHoes().getBoosterUse().removeToken(mPlayer.getPlayer(),"max");
                                                            upgradeWorked(mPlayer.getFaction(),"Spawner");
                                                            mPlayer.getPlayer().closeInventory();
                                                            mPlayer.getPlayer().openInventory(FactionHandHoes.getHandHoes().getFactionUpgradeGui().getUpgradeGui(mPlayer.getPlayer()));
                                                        }
                                                    } else {
                                                        Utils.sendFMessage(mPlayer.getPlayer(),jsonMessageConfig.getMaxLvlReachedMessage());
                                                    }
                                                } else if (event.getCurrentItem().getType() == FactionHandHoes.getHandHoes().getKeyItem().getClassKeyItem().getKeyMaterial()) {
                                                    if (factionUpgrade.getKeyFactionLVL() < FactionHandHoes.getHandHoes().getJsonCostConfig().getKeyMax()) {
                                                        if (nbtItem.getString("tokentype").equals("one")) {
                                                            FactionHandHoes.getHandHoes().getJsonFactionUpgrade().upgradeKey(
                                                                    mPlayer.getFaction().getId(),
                                                                    1,
                                                                    false);
                                                            FactionHandHoes.getHandHoes().getBoosterUse().removeToken(mPlayer.getPlayer(),"one");
                                                            upgradeWorked(mPlayer.getFaction(),"Key");
                                                            mPlayer.getPlayer().closeInventory();
                                                            mPlayer.getPlayer().openInventory(FactionHandHoes.getHandHoes().getFactionUpgradeGui().getUpgradeGui(mPlayer.getPlayer()));
                                                        } else if (nbtItem.getString("tokentype").equals("max")) {
                                                            FactionHandHoes.getHandHoes().getJsonFactionUpgrade().upgradeKey(
                                                                    mPlayer.getFaction().getId(),
                                                                    null,
                                                                    true);
                                                            FactionHandHoes.getHandHoes().getBoosterUse().removeToken(mPlayer.getPlayer(),"max");
                                                            upgradeWorked(mPlayer.getFaction(),"Key");
                                                            mPlayer.getPlayer().closeInventory();
                                                            mPlayer.getPlayer().openInventory(FactionHandHoes.getHandHoes().getFactionUpgradeGui().getUpgradeGui(mPlayer.getPlayer()));
                                                        }
                                                    } else {
                                                        Utils.sendFMessage(mPlayer.getPlayer(),jsonMessageConfig.getMaxLvlReachedMessage());
                                                    }
                                                } else if (event.getCurrentItem().getType() == FactionHandHoes.getHandHoes().getEssenceItem().getClassEssenceItem().getEssenceMaterial()) {
                                                    if (factionUpgrade.getEssenceFactionLVL() < FactionHandHoes.getHandHoes().getJsonCostConfig().getEssenceMax()) {
                                                        if (nbtItem.getString("tokentype").equals("one")) {
                                                            FactionHandHoes.getHandHoes().getJsonFactionUpgrade().upgradeEssence(
                                                                    mPlayer.getFaction().getId(),
                                                                    1,
                                                                    false);
                                                            FactionHandHoes.getHandHoes().getBoosterUse().removeToken(mPlayer.getPlayer(),"one");
                                                            upgradeWorked(mPlayer.getFaction(),"Essence");
                                                            mPlayer.getPlayer().closeInventory();
                                                            mPlayer.getPlayer().openInventory(FactionHandHoes.getHandHoes().getFactionUpgradeGui().getUpgradeGui(mPlayer.getPlayer()));
                                                        } else if (nbtItem.getString("tokentype").equals("max")) {
                                                            FactionHandHoes.getHandHoes().getJsonFactionUpgrade().upgradeEssence(
                                                                    mPlayer.getFaction().getId(),
                                                                    null,
                                                                    true);
                                                            FactionHandHoes.getHandHoes().getBoosterUse().removeToken(mPlayer.getPlayer(),"max");
                                                            upgradeWorked(mPlayer.getFaction(),"Essence");
                                                            mPlayer.getPlayer().closeInventory();
                                                            mPlayer.getPlayer().openInventory(FactionHandHoes.getHandHoes().getFactionUpgradeGui().getUpgradeGui(mPlayer.getPlayer()));
                                                        }
                                                    } else {
                                                        Utils.sendFMessage(mPlayer.getPlayer(),jsonMessageConfig.getMaxLvlReachedMessage());
                                                    }
                                                }  else if (event.getCurrentItem().getType() == FactionHandHoes.getHandHoes().getSaturationItem().getClassSaturationItem().getSaturationMaterial()) {
                                                    if (factionUpgrade.getSaturationFactionLVL() < FactionHandHoes.getHandHoes().getJsonCostConfig().getSaturationMax()) {
                                                        if (nbtItem.getString("tokentype").equals("one")) {
                                                            FactionHandHoes.getHandHoes().getJsonFactionUpgrade().upgradeSaturation(
                                                                    mPlayer.getFaction().getId(),
                                                                    1,
                                                                    false);
                                                            FactionHandHoes.getHandHoes().getBoosterUse().removeToken(mPlayer.getPlayer(),"one");
                                                            upgradeWorked(mPlayer.getFaction(),"Saturation");
                                                            mPlayer.getPlayer().closeInventory();
                                                            mPlayer.getPlayer().openInventory(FactionHandHoes.getHandHoes().getFactionUpgradeGui().getUpgradeGui(mPlayer.getPlayer()));
                                                        } else if (nbtItem.getString("tokentype").equals("max")) {
                                                            FactionHandHoes.getHandHoes().getJsonFactionUpgrade().upgradeSaturation(
                                                                    mPlayer.getFaction().getId(),
                                                                    null,
                                                                    true);
                                                            FactionHandHoes.getHandHoes().getBoosterUse().removeToken(mPlayer.getPlayer(),"max");
                                                            upgradeWorked(mPlayer.getFaction(),"Saturation");
                                                            mPlayer.getPlayer().closeInventory();
                                                            mPlayer.getPlayer().openInventory(FactionHandHoes.getHandHoes().getFactionUpgradeGui().getUpgradeGui(mPlayer.getPlayer()));
                                                        }
                                                    } else {
                                                        Utils.sendFMessage(mPlayer.getPlayer(),jsonMessageConfig.getMaxLvlReachedMessage());
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    if (new NBTItem(event.getCurrentItem()).hasNBTData()) {
                                        NBTItem nbtItem = new NBTItem(event.getCurrentItem());
                                        if (nbtItem.getBoolean("guiitem")) {
                                            if (event.getCurrentItem().getType() == FactionHandHoes.getHandHoes().getHasteItem().getClassHasteItem().getHasteMaterial()) {
                                                if (FactionHandHoes.getHandHoes().getHasteItem().upgrade(mPlayer.getPlayer(), 1, false, false)) {
                                                    upgradeWorked(mPlayer.getFaction(),"Haste");
                                                    mPlayer.getPlayer().closeInventory();
                                                    mPlayer.getPlayer().openInventory(FactionHandHoes.getHandHoes().getFactionUpgradeGui().getUpgradeGui(mPlayer.getPlayer()));
                                                }
                                            } else if (event.getCurrentItem().getType() == FactionHandHoes.getHandHoes().getTokenItem().getClassTokenItem().getTokenMaterial()) {
                                                if (FactionHandHoes.getHandHoes().getTokenItem().upgrade(mPlayer.getPlayer(), 1, false, false)) {
                                                    upgradeWorked(mPlayer.getFaction(),"Token");
                                                    mPlayer.getPlayer().closeInventory();
                                                    mPlayer.getPlayer().openInventory(FactionHandHoes.getHandHoes().getFactionUpgradeGui().getUpgradeGui(mPlayer.getPlayer()));
                                                }

                                            } else if (event.getCurrentItem().getType() == FactionHandHoes.getHandHoes().getMoneyItem().getClassMoneyItem().getMoneyMaterial()) {
                                                if (FactionHandHoes.getHandHoes().getMoneyItem().upgrade(mPlayer.getPlayer(), 1, false, false)) {
                                                    upgradeWorked(mPlayer.getFaction(),"Money");
                                                    mPlayer.getPlayer().closeInventory();
                                                    mPlayer.getPlayer().openInventory(FactionHandHoes.getHandHoes().getFactionUpgradeGui().getUpgradeGui(mPlayer.getPlayer()));
                                                }

                                            } else if (event.getCurrentItem().getType() == FactionHandHoes.getHandHoes().getEssenceItem().getClassEssenceItem().getEssenceMaterial()) {
                                                if (FactionHandHoes.getHandHoes().getEssenceItem().upgrade(mPlayer.getPlayer(), 1, false, false)) {
                                                    upgradeWorked(mPlayer.getFaction(),"Essence");
                                                    mPlayer.getPlayer().closeInventory();
                                                    mPlayer.getPlayer().openInventory(FactionHandHoes.getHandHoes().getFactionUpgradeGui().getUpgradeGui(mPlayer.getPlayer()));
                                                }

                                            } else if (event.getCurrentItem().getType() == FactionHandHoes.getHandHoes().getSpeedItem().getClassSpeedItem().getSpeedMaterial()) {
                                                if (FactionHandHoes.getHandHoes().getSpeedItem().upgrade(mPlayer.getPlayer(), 1, false, false)) {
                                                    upgradeWorked(mPlayer.getFaction(),"Speed");
                                                    mPlayer.getPlayer().closeInventory();
                                                    mPlayer.getPlayer().openInventory(FactionHandHoes.getHandHoes().getFactionUpgradeGui().getUpgradeGui(mPlayer.getPlayer()));
                                                }

                                            } else if (event.getCurrentItem().getType() == FactionHandHoes.getHandHoes().getSaturationItem().getClassSaturationItem().getSaturationMaterial()) {
                                                if (FactionHandHoes.getHandHoes().getSaturationItem().upgrade(mPlayer.getPlayer(), 1, false, false)) {
                                                    upgradeWorked(mPlayer.getFaction(),"Saturation");
                                                    mPlayer.getPlayer().closeInventory();
                                                    mPlayer.getPlayer().openInventory(FactionHandHoes.getHandHoes().getFactionUpgradeGui().getUpgradeGui(mPlayer.getPlayer()));
                                                }

                                            } else if (event.getCurrentItem().getType() == FactionHandHoes.getHandHoes().getKeyItem().getClassKeyItem().getKeyMaterial()) {
                                                if (FactionHandHoes.getHandHoes().getKeyItem().upgrade(mPlayer.getPlayer(), 1, false, false)) {
                                                    upgradeWorked(mPlayer.getFaction(),"Key");
                                                    mPlayer.getPlayer().closeInventory();
                                                    mPlayer.getPlayer().openInventory(FactionHandHoes.getHandHoes().getFactionUpgradeGui().getUpgradeGui(mPlayer.getPlayer()));
                                                }

                                            } else if (event.getCurrentItem().getType() == FactionHandHoes.getHandHoes().getSpawnerItem().getClassSpawnerItem().getSpawnerMaterial()) {
                                                if (FactionHandHoes.getHandHoes().getSpawnerItem().upgrade(mPlayer.getPlayer(), 1, false, false)) {
                                                    upgradeWorked(mPlayer.getFaction(),"Spawner");
                                                    mPlayer.getPlayer().closeInventory();
                                                    mPlayer.getPlayer().openInventory(FactionHandHoes.getHandHoes().getFactionUpgradeGui().getUpgradeGui(mPlayer.getPlayer()));
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
    }

    public void upgradeWorked(Faction faction, String type) {
        String base = FactionHandHoes.getHandHoes().getJsonConfig().getClassMessageConfig().getUpgradeWorkedMessagePlaceH();
        base = base.replace("%type%",type);
        for (Player player : faction.getOnlinePlayers()) {
            player.sendMessage(Utils.col(base));
        }
    }

    public boolean hasToken(Player player) {
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null) {
                if (item.getType() != Material.AIR) {
                    NBTItem nbtItem = new NBTItem(item);
                    if (nbtItem.hasNBTData()) {
                        if (nbtItem.getString("tokenitem").equals("true")) {
                            return nbtItem.getBoolean("token");
                        }
                    }
                }
            }
        }
        return false;
    }

    public Integer getTokenIndex(Player player) {
        for (int i = 0; i < player.getInventory().getSize(); i++) {
            ItemStack item = player.getInventory().getItem(i);
            if (item != null) {
                if (item.getType() != Material.AIR) {
                    NBTItem nbtItem = new NBTItem(item);
                    if (nbtItem.hasNBTData()) {
                        if (nbtItem.getBoolean("token")) {
                            return i;
                        }
                    }
                }
            }
        }
        return null;
    }
}
