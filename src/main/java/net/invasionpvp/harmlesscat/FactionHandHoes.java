package net.invasionpvp.harmlesscat;

import net.invasionpvp.harmlesscat.cane.CaneListener;
import net.invasionpvp.harmlesscat.cane.Cane;
import net.invasionpvp.harmlesscat.cane.HarvesterHoe;
import net.invasionpvp.harmlesscat.cane.SellClass;
import net.invasionpvp.harmlesscat.cane.runnable.RunnableTask;
import net.invasionpvp.harmlesscat.commands.admin.AdminCommands;
import net.invasionpvp.harmlesscat.commands.player.CaneTop;
import net.invasionpvp.harmlesscat.commands.player.PlayerCommands;
import net.invasionpvp.harmlesscat.files.JsonCostConfig;
import net.invasionpvp.harmlesscat.files.JsonConfig;
import net.invasionpvp.harmlesscat.files.PlaceHolders;
import net.invasionpvp.harmlesscat.files.PlayerCaneJson;
import net.invasionpvp.harmlesscat.files.faction.JsonFactionUpgrade;
import net.invasionpvp.harmlesscat.files.faction.itemsConfig.*;
import net.invasionpvp.harmlesscat.files.faction.itemsConfig.miscitem.EssenceBoosterItem;
import net.invasionpvp.harmlesscat.files.faction.itemsConfig.miscitem.MoneyBoosterItem;
import net.invasionpvp.harmlesscat.files.faction.itemsConfig.miscitem.TokenMaxItem;
import net.invasionpvp.harmlesscat.files.faction.itemsConfig.miscitem.TokenOneItem;
import net.invasionpvp.harmlesscat.gui.upgradegui.FactionUpgradeGui;
import net.invasionpvp.harmlesscat.gui.upgradegui.MakeItems;
import net.invasionpvp.harmlesscat.listeners.BoosterUse;
import net.invasionpvp.harmlesscat.listeners.FactionListeners;
import net.invasionpvp.harmlesscat.listeners.GuiListener;
import net.invasionpvp.harmlesscat.listeners.PlayerListeners;
import net.invasionpvp.harmlesscat.utils.Utils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class FactionHandHoes extends JavaPlugin {
    //
    public static FactionHandHoes handHoes;
    public static FactionHandHoes getHandHoes() { return  handHoes; }

    //
    private JsonFactionUpgrade jsonUpgrade;
    private JsonCostConfig jsonCostConfig;
    private JsonConfig jsonMessageConfig;
    private SpeedItem speedItem;
    private FactionUpgradeGui factionUpgradeGui;
    private MakeItems getItems;
    private HasteItem hasteItem;
    private EssenceItem essenceItem;
    private MoneyItem moneyItem;
    private TokenItem tokenItem;
    private SaturationItem saturationItem;
    private SpawnerItem spawerItem;
    private KeyItem keyItem;
    private FactionBeacon factionBeacon;
    private CaneListener caneListener;
    private Cane caneMode;
    private SellClass sellClass;
    private MoneyBoosterItem moneyBoosterItem;
    private EssenceBoosterItem essenceBoosterItem;
    private TokenOneItem tokenOneItem;
    private TokenMaxItem tokenMaxItem;
    private RunnableTask runnableTask;
    private BoosterUse boosterUse;
    private PlayerCaneJson playerCaneJson;
    private HarvesterHoe harvesterHoe;
    private PlayerCommands playerCommands;

    public PlayerCommands getPlayerCommands() { return playerCommands; }
    public HarvesterHoe getHarvesterHoe() { return harvesterHoe; }
    public PlayerCaneJson getPlayerCaneJson() { return playerCaneJson; }
    public BoosterUse getBoosterUse() { return boosterUse; }
    public RunnableTask getRunnableTask() { return runnableTask; }
    public MoneyBoosterItem getMoneyBoosterItem() { return moneyBoosterItem; }
    public TokenMaxItem getTokenMaxItem() { return tokenMaxItem; }
    public TokenOneItem getTokenOneItem() { return tokenOneItem; }
    public EssenceBoosterItem getEssenceBoosterItem() { return essenceBoosterItem; }
    public SellClass getSellClass() { return sellClass; }
    public Cane getCaneMode() { return caneMode; }
    public CaneListener getCaneListener() { return caneListener; }
    public FactionBeacon getFactionBeacon() { return factionBeacon; }
    public KeyItem getKeyItem() { return keyItem; }
    public SpawnerItem getSpawnerItem() { return spawerItem; }
    public SaturationItem getSaturationItem() { return saturationItem; }
    public TokenItem getTokenItem() { return tokenItem; }
    public MoneyItem getMoneyItem() { return moneyItem; }
    public EssenceItem getEssenceItem() { return essenceItem; }
    public HasteItem getHasteItem() { return hasteItem; }
    public MakeItems getMakeItems() { return getItems; }
    public FactionUpgradeGui getFactionUpgradeGui() { return factionUpgradeGui; }
    public SpeedItem getSpeedItem() { return speedItem; }
    public JsonConfig getJsonConfig() { return jsonMessageConfig; }
    public JsonFactionUpgrade getJsonFactionUpgrade() { return jsonUpgrade; }
    public JsonCostConfig getJsonCostConfig() { return jsonCostConfig; }

    //
    private File configFolder;
    private File factionDataFolder;
    private File playerDataFolder;

    public File getPlayerDataFolder() { return playerDataFolder; }
    public File getFactionDataFolder() { return factionDataFolder; }
    public File getConfigFolder() { return configFolder; }

    public static Economy econ;
    public static Economy getEconomy() {
        return econ;
    }

    @Override
    public void onEnable() {
        handHoes = this;
        registerClasses();
        loadFiles();
        registerListeners();
        registerCommands();
        new PlaceHolders().register();
    }

    @Override
    public void onDisable() {

    }

    public void registerClasses() {
        jsonMessageConfig = new JsonConfig();
        jsonCostConfig = new JsonCostConfig();
        jsonUpgrade = new JsonFactionUpgrade();
        speedItem = new SpeedItem();
        factionUpgradeGui = new FactionUpgradeGui();
        getItems = new MakeItems();
        hasteItem = new HasteItem();
        essenceItem = new EssenceItem();
        moneyItem = new MoneyItem();
        tokenItem = new TokenItem();
        spawerItem = new SpawnerItem();
        saturationItem = new SaturationItem();
        keyItem = new KeyItem();
        factionBeacon = new FactionBeacon();
        caneListener = new CaneListener();
        caneMode = new Cane();
        sellClass = new SellClass();
        essenceBoosterItem = new EssenceBoosterItem();
        tokenOneItem = new TokenOneItem();
        tokenMaxItem = new TokenMaxItem();
        moneyBoosterItem = new MoneyBoosterItem();
        runnableTask = new RunnableTask();
        boosterUse = new BoosterUse();
        playerCaneJson = new PlayerCaneJson();
        harvesterHoe = new HarvesterHoe();
        playerCommands = new PlayerCommands();
    }

    public void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new FactionListeners(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerListeners(), this);
        Bukkit.getPluginManager().registerEvents(new GuiListener(), this);
        Bukkit.getPluginManager().registerEvents(new CaneListener(), this);
        Bukkit.getPluginManager().registerEvents(new BoosterUse(), this);
    }

    public void registerCommands() {
        getCommand("hoe").setExecutor(new PlayerCommands());
        getCommand("ahoe").setExecutor(new AdminCommands());
        getCommand("cane").setExecutor(new CaneTop());
    }

    public void loadFiles() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        configFolder = new File(getDataFolder(),"config");
        if (!configFolder.exists()) {
            configFolder.mkdir();
        }
        factionDataFolder = new File(getDataFolder(),"factiondata");
        if (!factionDataFolder.exists()) {
            factionDataFolder.mkdir();
        }
        playerDataFolder = new File(getDataFolder(), "playerdata");
        if (!playerDataFolder.exists()) {
            playerDataFolder.mkdir();
        }
        jsonMessageConfig.jsonMessageConfig();
        jsonCostConfig.createJsonCostConfig();
        speedItem.createSpeedJson();
        hasteItem.createHasteJson();
        essenceItem.createEssenceJson();
        moneyItem.createMoneyJson();
        keyItem.createKeyJson();
        saturationItem.createSaturationJson();
        tokenItem.createTokenJson();
        spawerItem.createSpawnerJson();
        factionBeacon.createFactionItemJson();
        essenceBoosterItem.createEssenceItemJson();
        tokenOneItem.createTokenOneItemJson();
        moneyBoosterItem.createMoneyItemJson();
        tokenMaxItem.createMoneyItemJson();
        harvesterHoe.createFactionItemJson();
    }
    private boolean setupEconomy() {
        if (FactionHandHoes.getHandHoes().getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
}
