package net.invasionpvp.harmlesscat.listeners;

import com.massivecraft.factions.event.EventFactionsCreate;
import com.massivecraft.factions.event.EventFactionsDisband;
import com.massivecraft.factions.event.EventFactionsMembershipChange;
import net.invasionpvp.harmlesscat.FactionHandHoes;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FactionListeners implements Listener {

    @EventHandler
    public void factionCreateEvent(EventFactionsCreate event) {
        createFiles(event.getFactionId());
    }


    @EventHandler
    public void factionDisbandEvent(EventFactionsDisband event) {
        for (Player player : event.getFaction().getOnlinePlayers()) {
            FactionHandHoes.getHandHoes().getPlayerCommands().removeHoe(player);
        }
        FactionHandHoes.getHandHoes().getJsonFactionUpgrade().getJsonUpgradeFile(event.getFactionId()).delete();
    }

    @EventHandler
    public void playerLeaveFaction(EventFactionsMembershipChange event) {
        FactionHandHoes.getHandHoes().getPlayerCommands().removeHoe(event.getMPlayer().getPlayer());
    }
    public void createFiles(String factionId) {
        FactionHandHoes.getHandHoes().getJsonFactionUpgrade().createJsonFactionUpgrade(factionId);
    }
}
