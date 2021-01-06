package net.invasionpvp.harmlesscat.cane;

import com.cryptomorin.xseries.XMaterial;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.entity.MPlayer;
import net.invasionpvp.harmlesscat.FactionHandHoes;
import net.invasionpvp.harmlesscat.cane.runnable.RunnableTask;
import net.invasionpvp.harmlesscat.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.UUID;

public class CaneListener implements Listener {

    private final HashMap<UUID,Integer> caneBroken;
    private final HashMap<UUID, Cane> caneMode;
    private final HashMap<UUID,RunnableTask> playerRunnable;

    public CaneListener() {
        caneMode = new HashMap<>();
        caneBroken = new HashMap<>();
        playerRunnable = new HashMap<>();
    }

    public HashMap<UUID,RunnableTask>  getPlayerRunnable() { return playerRunnable; }
    public HashMap<UUID,Cane> getCaneMode() { return caneMode; }
    public HashMap<UUID,Integer> getCaneBroken() { return caneBroken; }

    public void putPlayer(Player player) {
        caneMode.put(player.getUniqueId(), FactionHandHoes.getHandHoes().getCaneMode().getLoadedCaneMode(MPlayer.get(player).getFaction()));
    }


    public void removePlayer(Player player) {
        caneMode.remove(player.getUniqueId());
    }

    public void toggleCaneMode(Player player, Boolean force) {
        if (MPlayer.get(player).getFaction() != null) {
            if (!MPlayer.get(player).getFaction().getId().equals(Factions.ID_NONE)) {
                if (force == null) {
                    if (caneMode.containsKey(player.getUniqueId())) {
                        Cane caneClass = caneMode.get(player.getUniqueId());
                        caneClass.inCane = !caneMode.get(player.getUniqueId()).isInCane();
                        caneMode.put(player.getUniqueId(),caneClass);
                    }
                } else {
                    if (caneMode.containsKey(player.getUniqueId())) {
                        Cane caneClass = caneMode.get(player.getUniqueId());
                        caneClass.inCane = force;
                        caneMode.put(player.getUniqueId(),caneClass);
                    }
                }
            }
        }
    }

    public void resetPlayer(Player player) {
        FactionHandHoes.getHandHoes().getCaneListener().getCaneBroken().remove(player.getUniqueId());
        FactionHandHoes.getHandHoes().getCaneListener().getPlayerRunnable().remove(player.getUniqueId());
    }

    @EventHandler
    public void caneBreakEvent(BlockBreakEvent event) {
        if (!event.isCancelled()) {
            if (XMaterial.matchXMaterial(event.getBlock().getType()) == XMaterial.SUGAR_CANE) {
                HashMap<UUID,Cane> localCaneMode = FactionHandHoes.getHandHoes().getCaneListener().getCaneMode();
                HashMap<UUID,Integer> localCaneBroken = FactionHandHoes.getHandHoes().getCaneListener().getCaneBroken();
                HashMap<UUID,RunnableTask>  localPlayerRunnable = FactionHandHoes.getHandHoes().getCaneListener().getPlayerRunnable();
                if (localCaneMode.containsKey(event.getPlayer().getUniqueId())) {
                    if (localCaneMode.get(event.getPlayer().getUniqueId()).inCane) {
                        event.setCancelled(true);
                        Material bottomBlock = event.getBlock().getChunk().getBlock((event.getBlock().getX()),(event.getBlock().getY()-1),(event.getBlock().getZ())).getType();
                        if (XMaterial.matchXMaterial(bottomBlock) == XMaterial.SUGAR_CANE) {
                            Block middleBlock = event.getBlock();
                            Block blockTop = event.getBlock().getChunk().getBlock(middleBlock.getX(),middleBlock.getY()+1,middleBlock.getZ());
                            if (localCaneBroken.get(event.getPlayer().getUniqueId()) != null) {
                                int prevVal = localCaneBroken.get(event.getPlayer().getUniqueId());
                                if (XMaterial.matchXMaterial(blockTop.getType()) == XMaterial.SUGAR_CANE) {
                                    localCaneBroken.put(event.getPlayer().getUniqueId(),prevVal+2);
                                    blockTop.setType(Material.AIR);
                                } else {
                                    localCaneBroken.put(event.getPlayer().getUniqueId(),prevVal+1);
                                }
                                middleBlock.setType(Material.AIR);
                            } else {
                                localCaneBroken.put(event.getPlayer().getUniqueId(),0);
                                RunnableTask task = new RunnableTask();
                                task.player = event.getPlayer();
                                task.sellTask = task.setSellTask(task.getPlayer());
                                task.taskId = Bukkit.getScheduler().scheduleSyncDelayedTask(
                                        FactionHandHoes.getHandHoes(),
                                        task.getSellTask(),
                                        20 * FactionHandHoes.getHandHoes().getJsonConfig().getTimeUntilSell());
                                localPlayerRunnable.put(event.getPlayer().getUniqueId(),task);
                                Utils.sendFMessage(event.getPlayer(),FactionHandHoes.getHandHoes().getJsonConfig().getClassMessageConfig().getStartedCaneTimer());
                                Cane playerCane = FactionHandHoes.getHandHoes().getCaneMode().getLoadedCaneMode(MPlayer.get(event.getPlayer()).getFaction());
                                if (playerCane.isSaturation() != 0) {
                                    event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SATURATION,
                                            FactionHandHoes.getHandHoes().getJsonConfig().getClassMessageConfig().getTimeUntilSell()*20-1,
                                            playerCane.getSpeedLvl()-1));
                                }
                                if (playerCane.getSpeedLvl() != 0) {
                                    event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED,
                                            FactionHandHoes.getHandHoes().getJsonConfig().getClassMessageConfig().getTimeUntilSell()*20-1,
                                            playerCane.getSpeedLvl()-1));
                                }
                                if (playerCane.getHasteLvl() != 0) {
                                    event.getPlayer().addPotionEffect(new PotionEffect(
                                            PotionEffectType.FAST_DIGGING,
                                            FactionHandHoes.getHandHoes().getJsonConfig().getClassMessageConfig().getTimeUntilSell()*20-1,
                                            playerCane.getHasteLvl()-1));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
