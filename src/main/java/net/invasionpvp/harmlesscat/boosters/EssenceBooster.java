package net.invasionpvp.harmlesscat.boosters;

import com.massivecraft.factions.entity.Faction;

public class EssenceBooster {

    public boolean enabled;
    public String type;
    public Faction owner;
    public Integer length;
    public Double multiplier;

    public EssenceBooster() {
        type = "Essence";
        owner = null;
        length = null;
        multiplier = null;
        enabled = false;
    }

    public boolean isEnable() { return enabled; }
}
