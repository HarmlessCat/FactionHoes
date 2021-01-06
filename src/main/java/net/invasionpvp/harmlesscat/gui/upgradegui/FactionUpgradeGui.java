package net.invasionpvp.harmlesscat.gui.upgradegui;

import com.cryptomorin.xseries.XMaterial;
import net.invasionpvp.harmlesscat.FactionHandHoes;
import net.invasionpvp.harmlesscat.files.faction.itemsConfig.*;
import net.invasionpvp.harmlesscat.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class FactionUpgradeGui {

    public void openGui(Player player) {
        player.openInventory(getUpgradeGui(player));
    }

    public Inventory getUpgradeGui(Player player) {
        Inventory factionGui = Bukkit.createInventory(
                null,
                FactionHandHoes.getHandHoes().getJsonConfig().getInventorySize(),
                Utils.col(FactionHandHoes.getHandHoes().getJsonConfig().getClassMessageConfig().getFactionHoeTitle()));
        MakeItems getItems = new MakeItems();
        SpeedItem speedItem = FactionHandHoes.getHandHoes().getSpeedItem().getClassSpeedItem();
        HasteItem hasteItem = FactionHandHoes.getHandHoes().getHasteItem().getClassHasteItem();
        EssenceItem essenceItem =  FactionHandHoes.getHandHoes().getEssenceItem().getClassEssenceItem();
        MoneyItem moneyItem =  FactionHandHoes.getHandHoes().getMoneyItem().getClassMoneyItem();
        TokenItem tokenItem = FactionHandHoes.getHandHoes().getTokenItem().getClassTokenItem();
        FactionBeacon factionBeacon = FactionHandHoes.getHandHoes().getFactionBeacon().getClassFactionItem();
        SaturationItem saturationItem = FactionHandHoes.getHandHoes().getSaturationItem().getClassSaturationItem();
        SpawnerItem spawnerItem = FactionHandHoes.getHandHoes().getSpawnerItem().getClassSpawnerItem();
        KeyItem keyItem = FactionHandHoes.getHandHoes().getKeyItem().getClassKeyItem();
        for (int i = 0; i <= FactionHandHoes.getHandHoes().getJsonConfig().getInventorySize()-1; i++) {
            if (i == essenceItem.getIndex()) {
                getItems.lore = essenceItem.getLore();
                getItems.material = essenceItem.getEssenceMaterial();
                getItems.title = essenceItem.getTitle();
                factionGui.setItem(i, FactionHandHoes.getHandHoes().getMakeItems().getItem(getItems,player));
            } else if (i == moneyItem.getIndex()) {
                getItems.lore = moneyItem.getLore();
                getItems.material = moneyItem.getMoneyMaterial();
                getItems.title = moneyItem.getTitle();
                factionGui.setItem(i, FactionHandHoes.getHandHoes().getMakeItems().getItem(getItems,player));
            } else if (i == saturationItem.getIndex()) {
                getItems.lore = saturationItem.getLore();
                getItems.material = saturationItem.getSaturationMaterial();
                getItems.title = saturationItem.getTitle();
                factionGui.setItem(i, FactionHandHoes.getHandHoes().getMakeItems().getItem(getItems,player));
            } else if (i == hasteItem.getIndex()) {
                getItems.lore = hasteItem.getLore();
                getItems.material = hasteItem.getHasteMaterial();
                getItems.title = hasteItem.getTitle();
                factionGui.setItem(i, FactionHandHoes.getHandHoes().getMakeItems().getItem(getItems,player));
            } else if (i == speedItem.getIndex()) {
                getItems.lore = speedItem.getLore();
                getItems.material = speedItem.getSpeedMaterial();
                getItems.title = speedItem.getTitle();
                factionGui.setItem(i, FactionHandHoes.getHandHoes().getMakeItems().getItem(getItems,player));
            } else if (i == tokenItem.getIndex()) {
                getItems.lore = tokenItem.getLore();
                getItems.material = tokenItem.getTokenMaterial();
                getItems.title = tokenItem.getTitle();
                factionGui.setItem(i, FactionHandHoes.getHandHoes().getMakeItems().getItem(getItems,player));
            } else if (i == spawnerItem.getIndex()) {
                getItems.lore = spawnerItem.getLore();
                getItems.material = spawnerItem.getSpawnerMaterial();
                getItems.title = spawnerItem.getTitle();
                factionGui.setItem(i, FactionHandHoes.getHandHoes().getMakeItems().getItem(getItems,player));
            } else if (i == factionBeacon.getIndex()) {
                getItems.lore = factionBeacon.getLore();
                getItems.material = factionBeacon.getMaterial();
                getItems.title = factionBeacon.getTitle();
                factionGui.setItem(i, FactionHandHoes.getHandHoes().getMakeItems().getItem(getItems,player));
            } else if (i == keyItem.getIndex()) {
                getItems.lore = keyItem.getLore();
                getItems.material = keyItem.getKeyMaterial();
                getItems.title = keyItem.getTitle();
                factionGui.setItem(i, FactionHandHoes.getHandHoes().getMakeItems().getItem(getItems,player));
            } else {
                factionGui.setItem(i, getFillings());
            }
        }
        return factionGui;
    }
    public ItemStack getFillings() {
        ItemStack fillings = new ItemStack(Objects.requireNonNull(XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()), 1, (short) 15);
        ItemMeta fillingsMeta = fillings.getItemMeta();
        fillingsMeta.setDisplayName("");
        fillings.setItemMeta(fillingsMeta);
        return fillings;
    }
}
