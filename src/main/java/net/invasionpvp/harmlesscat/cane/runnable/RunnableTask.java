package net.invasionpvp.harmlesscat.cane.runnable;

import com.massivecraft.factions.entity.Faction;
import net.invasionpvp.harmlesscat.FactionHandHoes;
import net.invasionpvp.harmlesscat.utils.Utils;
import org.bukkit.entity.Player;

public class RunnableTask {

    public Runnable sellTask;
    public Player player;
    public Integer taskId;

    public RunnableTask() {
        player = null;
        sellTask = setSellTask(null);
        taskId = null;
    }

    public Player getPlayer() {
        return player;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public Runnable getSellTask() {
        return sellTask;
    }

    public Runnable setSellTask(Player player) {
        if (player != null) {
            return () -> {
                if (FactionHandHoes.getHandHoes().getCaneListener().getCaneBroken().get(player.getUniqueId()) != null) {
                    try {
                        player.sendTitle(Utils.col(
                                FactionHandHoes.getHandHoes().getJsonConfig().getClassMessageConfig().getCaneBroadcastMessage().replace("%cane%",FactionHandHoes.getHandHoes().getCaneListener().getCaneBroken().get(player.getUniqueId()).toString())),
                                "");
                        FactionHandHoes.getHandHoes().getSellClass().sellCane(player);
                    } catch (Exception ignored) {

                    }

                }
            };
        }
        return null;
    }

    public Runnable setBoosterTask(Faction faction, String task) {
        if (faction != null) {
            return () -> {
                if (task.equals("essence")) {
                    FactionHandHoes.getHandHoes().getSellClass().removeEssenceB(faction);
                    for (Player player : faction.getOnlinePlayers()) {
                        player.sendMessage(
                                Utils.col(FactionHandHoes.getHandHoes().getJsonConfig().getClassMessageConfig().getBoosterDeactiveMessage()[0].replace("%booster%","Essence"))
                        );
                    }
                } else if (task.equals("money")) {
                    FactionHandHoes.getHandHoes().getSellClass().removeMoneyB(faction);
                    for (Player player : faction.getOnlinePlayers()) {
                        player.sendMessage(
                                Utils.col(FactionHandHoes.getHandHoes().getJsonConfig().getClassMessageConfig().getBoosterDeactiveMessage()[0].replace("%booster%","Money"))
                        );
                    }
                }
            };
        }
        return null;
    }
}
