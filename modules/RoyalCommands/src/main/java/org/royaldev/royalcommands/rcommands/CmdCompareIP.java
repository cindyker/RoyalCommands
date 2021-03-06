package org.royaldev.royalcommands.rcommands;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.royaldev.royalcommands.Config;
import org.royaldev.royalcommands.MessageColor;
import org.royaldev.royalcommands.RoyalCommands;
import org.royaldev.royalcommands.configuration.PlayerConfiguration;
import org.royaldev.royalcommands.configuration.PlayerConfigurationManager;

@ReflectCommand
public class CmdCompareIP extends BaseCommand {

    public CmdCompareIP(final RoyalCommands instance, final String name) {
        super(instance, name, true);
    }

    @Override
    public boolean runCommand(final CommandSender cs, final Command cmd, final String label, final String[] args) {
        if (Config.disablegetip) {
            cs.sendMessage(MessageColor.NEGATIVE + "/getip and /compareip have been disabled.");
            return true;
        }
        if (args.length < 2) {
            cs.sendMessage(cmd.getDescription());
            return false;
        }
        OfflinePlayer player1;
        OfflinePlayer player2;
        player1 = this.plugin.getServer().getOfflinePlayer(args[0]);
        player2 = this.plugin.getServer().getOfflinePlayer(args[1]);
        PlayerConfiguration pcm1 = PlayerConfigurationManager.getConfiguration(player1);
        PlayerConfiguration pcm2 = PlayerConfigurationManager.getConfiguration(player2);
        if (pcm1.exists()) {
            if (pcm2.exists()) {
                String p1ip = pcm1.getString("ip");
                String p2ip = pcm2.getString("ip");

                cs.sendMessage(MessageColor.NEUTRAL + player1.getName() + ": " + p1ip);
                cs.sendMessage(MessageColor.NEUTRAL + player2.getName() + ": " + p2ip);
                return true;
            } else {
                cs.sendMessage(MessageColor.NEGATIVE + "The player " + player2.getName() + " does not exist.");
                return true;
            }
        } else {
            cs.sendMessage(MessageColor.NEGATIVE + "The player " + player1.getName() + " does not exist.");
            return true;
        }
    }
}
