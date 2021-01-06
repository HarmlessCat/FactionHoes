package net.invasionpvp.harmlesscat.files;

import net.invasionpvp.harmlesscat.FactionHandHoes;
import net.invasionpvp.harmlesscat.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class JsonConfig {

    public final int inventorySize = 6*9;
    public final Integer timeUntilSell;
    public final String factionHoeTitle;
    public final String caneTopLayout;
    public final String upgradeWorkedMessagePlaceH;
    public final String caneBroadcastMessage;
    public final String[] caneTopBase = new String[]{"The top 10 ","Cane grinders!"};
    public final String[] adminCommandWorked = new String[]{"worked","true","false"};
    public final String[] info = new String[]{"Message","Boolean should send","Boolean faction wide message"};
    public final String[] wrongCommandExecute = new String[]{"14","true","false"};
    public final String[] errorMessage = new String[]{"1","true","false"};
    public final String[] noMoneyMessage = new String[]{"2","true","false"};
    public final String[] noPermissionMessage = new String[]{"3","true","false"};
    public final String[] boosterDeactiveMessage = new String[]{"4","true","false"};
    public final String[] boosterActivatedMessage = new String[]{"5","true","false"};
    public final String[] boosterAlreadyActiveMessage = new String[]{"6","true","false"};
    public final String[] maxLvlReachedMessage = new String[]{"7","true","false"};
    public final String[] startedCaneTimer = new String[]{"8","true","false"};
    public final String[] upgradeWorkedMessage = new String[]{"11","true","false"};
    public final String[] noFactionFound = new String[]{"20","true","false"};
    public final String[] inventoryFullMessage = new String[]{"21","true","false"};

    public JsonConfig() {
        upgradeWorkedMessagePlaceH = "%type% what?";
        caneBroadcastMessage = "sold %cane%!";
        caneTopLayout = "%nr%. %player% - %caneamt% Sugar cane";
        factionHoeTitle = "&d&l&nFaction Hoe&r &f&l&nMenu";
        timeUntilSell  = 10;
    }

    public String getCaneBroadcastMessage() { return caneBroadcastMessage; }
    public String[] getInventoryFullMessage() { return inventoryFullMessage; }
    public String[] getNoFactionFound() { return noFactionFound; }
    public String[] getAdminCommandWorked() { return adminCommandWorked; }
    public String getUpgradeWorkedMessagePlaceH() { return upgradeWorkedMessagePlaceH; }
    public int getInventorySize() { return inventorySize; }
    public String getCaneTopLayout() { return caneTopLayout; }
    public String[] getCaneTopBase() { return caneTopBase; }
    public Integer getTimeUntilSell() { return timeUntilSell; }
    public String[] getUpgradeWorkedMessage() { return upgradeWorkedMessage; }
    public String getFactionHoeTitle() { return factionHoeTitle; }
    public String[] getWrongCommandExecute() { return wrongCommandExecute; }
    public String[] getErrorMessage() { return errorMessage; }
    public String[] getNoMoneyMessage() { return noMoneyMessage; }
    public String[] getNoPermissionMessage() { return noPermissionMessage; }
    public String[] getBoosterDeactiveMessage() { return boosterDeactiveMessage; }
    public String[] getBoosterActivatedMessage() { return boosterActivatedMessage; }
    public String[] getBoosterAlreadyActiveMessage() { return boosterAlreadyActiveMessage; }
    public String[] getMaxLvlReachedMessage() { return maxLvlReachedMessage; }
    public String[] getStartedCaneTimer() { return startedCaneTimer; }


    private File jsonMessageConfigFile;

    public void jsonMessageConfig() {
        if (FactionHandHoes.getHandHoes().getConfigFolder().exists()) {
            jsonMessageConfigFile = getJsonMessageConfigFile();
            if (!jsonMessageConfigFile.exists()) {
                try {
                    jsonMessageConfigFile.createNewFile();
                    JsonConfig jsonMessageConfig = new JsonConfig();
                    PrintWriter pw = new PrintWriter(jsonMessageConfigFile, "UTF-8");
                    String jsonString = Utils.gson.toJson(jsonMessageConfig);
                    pw.print(jsonString);
                    pw.flush();
                    pw.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public JsonConfig getClassMessageConfig() {
        jsonMessageConfigFile = getJsonMessageConfigFile();
        if (jsonMessageConfigFile.exists()) {
            try {
                String configJson = new String(Files.readAllBytes(jsonMessageConfigFile.toPath()), StandardCharsets.UTF_8);
                return Utils.gson.fromJson(configJson, JsonConfig.class);
            } catch (IOException ex) {
                return new JsonConfig();
            }
        }
        return new JsonConfig();
    }

    public File getJsonMessageConfigFile() {
        return new File(FactionHandHoes.getHandHoes().getConfigFolder(),"config.json");
    }
}
