package net.invasionpvp.harmlesscat.commands.admin;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;
import net.invasionpvp.harmlesscat.FactionHandHoes;
import net.invasionpvp.harmlesscat.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (sender.hasPermission("") || sender.isOp()) {
                runCommand(sender,command,label,args);
            } else {
                Utils.sendFMessage((Player)sender,FactionHandHoes.getHandHoes().getJsonConfig().getClassMessageConfig().getNoPermissionMessage());
            }
        } else {
            runCommand(sender,command,label,args);
        }
        return false;
    }
    //ahoe give essence <target> <amt> <multi> <length>
    public void runCommand(CommandSender sender, Command command, String label, String[] args) {
        //WORLD ID
        if (args.length == 6) {
            if (args[0].equalsIgnoreCase("give")) {
                if (args[2].equalsIgnoreCase("essence")) {
                    if (Bukkit.getPlayer(args[1]) != null) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (Utils.isInteger(args[3])) {
                            if (Utils.isInteger(args[4])) {
                                if (Utils.isDouble(args[5])) {
                                    FactionHandHoes.getHandHoes().getEssenceBoosterItem().getEssenceItemClass().giveItem(
                                            target,
                                            Integer.parseInt(args[3]),
                                            Double.parseDouble(args[5]),
                                            Integer.parseInt(args[4]));
                                    sendAdminCommand(sender,target,"Essence Booster");
                                } else {
                                    Utils.sendFMessage(sender, FactionHandHoes.getHandHoes().getJsonConfig().getClassMessageConfig().getWrongCommandExecute());
                                }
                            } else {
                                Utils.sendFMessage(sender, FactionHandHoes.getHandHoes().getJsonConfig().getClassMessageConfig().getWrongCommandExecute());
                            }
                        } else {
                            Utils.sendFMessage(sender, FactionHandHoes.getHandHoes().getJsonConfig().getClassMessageConfig().getWrongCommandExecute());
                        }
                    } else {
                        Utils.sendFMessage(sender, FactionHandHoes.getHandHoes().getJsonConfig().getClassMessageConfig().getWrongCommandExecute());
                    }
                } else if (args[2].equalsIgnoreCase("money")) {
                    Player target = Bukkit.getPlayer(args[1]);
                    if (Utils.isInteger(args[3])) {
                        if (Utils.isInteger(args[4])) {
                            if (Utils.isInteger(args[5])) {
                                FactionHandHoes.getHandHoes().getMoneyBoosterItem().getMoneyItemClass().giveItem(
                                        target,
                                        Integer.parseInt(args[3]),
                                        Double.parseDouble(args[5]),
                                        Integer.parseInt(args[4]));
                                sendAdminCommand(sender,target,"Money Booster");
                            } else {
                                Utils.sendFMessage(sender, FactionHandHoes.getHandHoes().getJsonConfig().getClassMessageConfig().getWrongCommandExecute());
                            }
                        } else {
                            Utils.sendFMessage(sender, FactionHandHoes.getHandHoes().getJsonConfig().getClassMessageConfig().getWrongCommandExecute());
                        }
                    } else {
                        Utils.sendFMessage(sender, FactionHandHoes.getHandHoes().getJsonConfig().getClassMessageConfig().getWrongCommandExecute());
                    }
                } else {
                    Utils.sendFMessage(sender, FactionHandHoes.getHandHoes().getJsonConfig().getClassMessageConfig().getWrongCommandExecute());
                }
            }
        //TOKENS /ahoe give <player> <type> <amt>
        //ECO    /ahoe giveeco <player> <type> <amount>
        } else if (args.length == 4) {
            if (args[0].equalsIgnoreCase("give")) {
                if (Bukkit.getPlayer(args[1]) != null) {
                    Player target = Bukkit.getPlayer(args[1]);
                    if (Utils.isInteger(args[3])) {
                        if (args[2].equalsIgnoreCase("max")) {
                            FactionHandHoes.getHandHoes().getTokenMaxItem().getTokenMaxItemClass().givePlayer(target,Integer.parseInt(args[3]));
                            sendAdminCommand(sender,target,"Token Level Max");
                        } else if (args[2].equalsIgnoreCase("one")) {
                            FactionHandHoes.getHandHoes().getTokenOneItem().getTokenOneItemClass().givePlayer(target,Integer.parseInt(args[3]));
                            sendAdminCommand(sender,target,"Token Level 1");
                        } else {
                            Utils.sendFMessage(sender, FactionHandHoes.getHandHoes().getJsonConfig().getClassMessageConfig().getWrongCommandExecute());
                        }
                    } else {
                        Utils.sendFMessage(sender, FactionHandHoes.getHandHoes().getJsonConfig().getClassMessageConfig().getWrongCommandExecute());
                    }
                } else {
                    Utils.sendFMessage(sender, FactionHandHoes.getHandHoes().getJsonConfig().getClassMessageConfig().getWrongCommandExecute());
                }
            } else if (args[0].equalsIgnoreCase("giveeco")) {
                if (Bukkit.getPlayer(args[1]) != null) {
                    Player targetPlayerF = Bukkit.getPlayer(args[1]);
                    MPlayer mPlayer = MPlayer.get(targetPlayerF);
                    if (mPlayer.getFaction() != null) {
                        if (!mPlayer.getFaction().getId().equals(Factions.ID_NONE)) {
                            Faction targetF = mPlayer.getFaction();
                            if (Utils.isInteger(args[3])) {
                                if (args[2].equalsIgnoreCase("essence")) {
                                    FactionHandHoes.getHandHoes().getJsonFactionUpgrade().addEssence(targetF.getId(),Integer.parseInt(args[3]));
                                    sendAdminCommand(sender,targetPlayerF,"Essence");
                                } else if (args[2].equalsIgnoreCase("tokens")) {
                                    FactionHandHoes.getHandHoes().getJsonFactionUpgrade().addTokens(targetF.getId(),Integer.parseInt(args[3]));
                                    sendAdminCommand(sender,targetPlayerF,"Tokens");
                                }
                            }
                        }
                    }
                }
            } else {
                Utils.sendFMessage(sender, FactionHandHoes.getHandHoes().getJsonConfig().getClassMessageConfig().getWrongCommandExecute());
            }
        } else {
            Utils.sendFMessage(sender, FactionHandHoes.getHandHoes().getJsonConfig().getClassMessageConfig().getWrongCommandExecute());
        }
    }

    public void sendAdminCommand(CommandSender sender, Player target, String type) {
        String base = FactionHandHoes.getHandHoes().getJsonConfig().getClassMessageConfig().getAdminCommandWorked()[0];
        base = base.replace("%type%",type);
        base = base.replace("%target%", target.getName());
        sender.sendMessage(Utils.col(base));
    }
}
