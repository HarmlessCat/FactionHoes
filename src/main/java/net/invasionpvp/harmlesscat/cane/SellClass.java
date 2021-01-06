package net.invasionpvp.harmlesscat.cane;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import net.invasionpvp.harmlesscat.FactionHandHoes;
import net.invasionpvp.harmlesscat.boosters.EssenceBooster;
import net.invasionpvp.harmlesscat.boosters.MoneyBooster;
import net.invasionpvp.harmlesscat.utils.Utils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Random;

public class SellClass {

    public Random random = new Random();

    public Economy econ = FactionHandHoes.getEconomy();

    public HashMap<Faction, EssenceBooster> factionEssenceBooster;
    public HashMap<Faction, MoneyBooster> factionMoneyBooster;

    public SellClass() {
        factionEssenceBooster = new HashMap<>();
        factionMoneyBooster = new HashMap<>();
    }

    public void putEssenceB(Faction faction, EssenceBooster essenceBooster) { factionEssenceBooster.put(faction,essenceBooster); }
    public void putMoneyB(Faction faction, MoneyBooster moneyBooster) { factionMoneyBooster.put(faction, moneyBooster); }
    public void removeMoneyB(Faction faction) {
        factionMoneyBooster.remove(faction);
    }
    public void removeEssenceB(Faction faction) {
        factionEssenceBooster.remove(faction);
    }

    public Boolean boosterEEnabled(Faction faction) {
        if (factionEssenceBooster.containsKey(faction)) {
            return factionEssenceBooster.get(faction).isEnable();
        }
        return false;
    }

    public Boolean boosterMEnabled(Faction faction) {
        if (factionMoneyBooster.containsKey(faction)) {
            return factionMoneyBooster.get(faction).isEnable();
        }
        return false;
    }

    public void sellCane(Player player) {
        econ = FactionHandHoes.getEconomy();
        Cane caneMode = FactionHandHoes.getHandHoes().getCaneListener().getCaneMode().get(player.getUniqueId());
        Integer amt = FactionHandHoes.getHandHoes().getCaneListener().getCaneBroken().get(player.getUniqueId());
        if (caneMode != null) {
            if (amt != null) {
                if (amt != 0) {
                    FactionHandHoes.getHandHoes().getPlayerCaneJson().addAmt(player,amt);
                    Faction faction = MPlayer.get(player).getFaction();
                    double factionMoneyBoostM = 1;
                    double factionEssenceBoostM = 1;
                    if (factionMoneyBooster.containsKey(MPlayer.get(player).getFaction())) {
                        if (factionMoneyBooster.get(faction).isEnable()) {
                            factionMoneyBoostM = factionMoneyBooster.get(MPlayer.get(player).getFaction()).multiplier;
                        }
                        if (factionEssenceBooster.get(faction).isEnable()) {
                            factionEssenceBoostM = factionEssenceBooster.get(MPlayer.get(player).getFaction()).multiplier;
                        }
                    }
                    econ.depositPlayer(player,
                            FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig().getSellPrice()*
                                    amt*
                                    caneMode.getMoneyMultiplier()*
                                    factionMoneyBoostM);

                    double essenceIncrease = amt*caneMode.getEssenceMultiplier()*factionEssenceBoostM;
                    FactionHandHoes.getHandHoes().getJsonFactionUpgrade().addEssence(MPlayer.get(player).getFaction().getId(),
                            (int) essenceIncrease);
                    if (caneMode.getTokenChance() != 0) {
                        FactionHandHoes.getHandHoes().getJsonFactionUpgrade().addTokens(MPlayer.get(player).getFaction().getId(),
                                chanceByRuns(amt,(int) caneMode.getTokenChance()));
                    }
                    if (caneMode.getSpawnerChance() != 0) {
                        Utils.runSpawner(player,chanceByRuns(amt, (int) caneMode.getSpawnerChance()));
                    }
                    if (caneMode.getKeyChance() != 0) {
                        Utils.runKey(player,chanceByRuns(amt, (int) caneMode.getKeyChance()));
                    }
                }
            }
            FactionHandHoes.getHandHoes().getCaneListener().resetPlayer(player);
        }
    }

    public int chanceByRuns(int runs, int chance) {
        if (chance != 0) {
            int Amount = 0;
            for (int i = 0; i<runs;i++) {
                int r = (random.nextInt(chance + 1));
                if (r == 10) {
                    Amount++;
                }
            }
            return Amount;
        } else {
            return 0;
        }
    }
}
