package org.royaldev.royalcommands.rcommands;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.royaldev.royalcommands.MessageColor;
import org.royaldev.royalcommands.RoyalCommands;
import org.royaldev.royalcommands.configuration.PlayerConfiguration;
import org.royaldev.royalcommands.configuration.PlayerConfigurationManager;

@ReflectCommand
public class CmdGetIP extends BaseCommand {

    public CmdGetIP(final RoyalCommands instance, final String name) {
        super(instance, name, true);
    }

    @Override
    public boolean runCommand(final CommandSender cs, final Command cmd, final String label, final String[] args) {
        if (this.plugin.getConfig().getBoolean("disable_getip")) {
            cs.sendMessage(MessageColor.NEGATIVE + "/getip and /compareip have been disabled.");
            return true;
        }
        if (args.length < 1) {
            cs.sendMessage(cmd.getDescription());
            return false;
        }
        OfflinePlayer oplayer = this.plugin.getServer().getOfflinePlayer(args[0]);
        PlayerConfiguration pcm = PlayerConfigurationManager.getConfiguration(oplayer);
        if (pcm.exists()) cs.sendMessage(MessageColor.NEUTRAL + oplayer.getName() + ": " + pcm.getString("ip"));
        else cs.sendMessage(MessageColor.NEGATIVE + "The player " + oplayer.getName() + " does not exist.");
        return true;
    }
}
