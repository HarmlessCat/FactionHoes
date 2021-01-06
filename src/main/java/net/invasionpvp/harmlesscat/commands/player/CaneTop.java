package net.invasionpvp.harmlesscat.commands.player;

import net.invasionpvp.harmlesscat.FactionHandHoes;
import net.invasionpvp.harmlesscat.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CaneTop implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("top")) {
                    FactionHandHoes.getHandHoes().getPlayerCommands().caneTop((Player) sender);
                } else {
                    Utils.sendFMessage((Player)sender,FactionHandHoes.getHandHoes().getJsonConfig().getClassMessageConfig().getErrorMessage());
                }
            }
        }
        return false;
    }
}
