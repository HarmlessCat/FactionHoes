package net.invasionpvp.harmlesscat.files;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.entity.MPlayer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.invasionpvp.harmlesscat.FactionHandHoes;
import net.invasionpvp.harmlesscat.files.faction.JsonFactionUpgrade;
import net.invasionpvp.harmlesscat.utils.Utils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlaceHolders extends PlaceholderExpansion {


    @Override
    public @NotNull String getIdentifier() {
        return "handhoe";
    }

    @Override
    public @NotNull String getAuthor() {
        return "HarmlessCat5791";
    }

    @Override
    public @NotNull String getVersion() {
        return FactionHandHoes.getHandHoes().getServer().getVersion();
    }



    public String onPlaceholderRequest(Player player, @NotNull String identifier){

        if(player == null){
            return "player NULL";
        }
        JsonFactionUpgrade factionUpgrade = FactionHandHoes.getHandHoes().getJsonFactionUpgrade().getClassFactionUpgrade(MPlayer.get(player).getFaction().getId());
        JsonCostConfig jsonCostConfig = FactionHandHoes.getHandHoes().getJsonCostConfig().getClassCostConfig();
        switch (identifier) {
            case "speedmaxlvl":
                return jsonCostConfig.getSpeedMax().toString();
            case "speedcurrent":
                return factionUpgrade.getSpeedFactionLVL()+"";
            case "speedEcost":
                return FactionHandHoes.getHandHoes().getSpeedItem().getEssenceCost(factionUpgrade.getSpeedFactionLVL());
            case "speedTcost":
                return FactionHandHoes.getHandHoes().getSpeedItem().getTokenCost(factionUpgrade.getSpeedFactionLVL());

            case "tokenmaxlvl":
                return jsonCostConfig.getTokenMax().toString();
            case "tokencurrent":
                return factionUpgrade.getTokenFactionLVL()+"";
            case "tokenEcost":
                return FactionHandHoes.getHandHoes().getTokenItem().getEssenceCost(factionUpgrade.getTokenFactionLVL());
            case "tokenTcost":
                return FactionHandHoes.getHandHoes().getTokenItem().getTokenCost(factionUpgrade.getTokenFactionLVL());

            case "saturationmaxlvl":
                return jsonCostConfig.getSaturationMax().toString();
            case "saturationcurrent":
                return factionUpgrade.getSaturationFactionLVL()+"";
            case "saturationEcost":
                return FactionHandHoes.getHandHoes().getSaturationItem().getEssenceCost(factionUpgrade.getSaturationFactionLVL());
            case "saturationTcost":
                return FactionHandHoes.getHandHoes().getSaturationItem().getTokenCost(factionUpgrade.getSaturationFactionLVL());

            case "spawnermaxlvl":
                return jsonCostConfig.getSpawnerMax().toString();
            case "spawnercurrent":
                return factionUpgrade.getSpawnerFactionLVL()+"";
            case "spawnerEcost":
                return FactionHandHoes.getHandHoes().getSpawnerItem().getEssenceCost(factionUpgrade.getSpawnerFactionLVL());
            case "spawnerTcost":
                return FactionHandHoes.getHandHoes().getSpawnerItem().getTokenCost(factionUpgrade.getSpawnerFactionLVL());

            case "moneymaxlvl":
                return jsonCostConfig.getMoneyMax().toString();
            case "moneycurrent":
                return factionUpgrade.getMoneyFactionLVL()+"";
            case "moneyEcost":
                return FactionHandHoes.getHandHoes().getMoneyItem().getEssenceCost(factionUpgrade.getMoneyFactionLVL());
            case "moneyTcost":
                return FactionHandHoes.getHandHoes().getMoneyItem().getTokenCost(factionUpgrade.getMoneyFactionLVL());

            case "essencemaxlvl":
                return jsonCostConfig.getEssenceMax().toString();
            case "essencecurrent":
                return factionUpgrade.getEssenceFactionLVL()+"";
            case "essenceEcost":
                return FactionHandHoes.getHandHoes().getEssenceItem().getEssenceCost(factionUpgrade.getEssenceFactionLVL());
            case "essenceTcost":
                return FactionHandHoes.getHandHoes().getEssenceItem().getTokenCost(factionUpgrade.getEssenceFactionLVL());

            case "keymaxlvl":
                return jsonCostConfig.getKeyMax().toString();
            case "keycurrent":
                return factionUpgrade.getKeyFactionLVL()+"";
            case "keyEcost":
                return FactionHandHoes.getHandHoes().getKeyItem().getEssenceCost(factionUpgrade.getKeyFactionLVL());
            case "keyTcost":
                return FactionHandHoes.getHandHoes().getKeyItem().getTokenCost(factionUpgrade.getKeyFactionLVL());

            case "hastemaxlvl":
                return jsonCostConfig.getHasteMax().toString();
            case "hastecurrent":
                return factionUpgrade.getHasteFactionLVL()+"";
            case "hasteEcost":
                return FactionHandHoes.getHandHoes().getHasteItem().getEssenceCost(factionUpgrade.getHasteFactionLVL());
            case "hasteTcost":
                return FactionHandHoes.getHandHoes().getHasteItem().getTokenCost(factionUpgrade.getHasteFactionLVL());

            case "essencebalance":
                return Utils.getCost((int)Math.round(factionUpgrade.getEssenceBalance()));
            case "tokenbalance":
                return Utils.getCost((int)Math.round(factionUpgrade.getTokenBalance()));

            case "faction":
                if (MPlayer.get(player).getFaction() != null) {
                    if (!MPlayer.get(player).getFaction().getId().equals(Factions.ID_NONE)) {
                        return MPlayer.get(player).getFaction().getName();
                    }
                }
        }
        return "";
    }
}
