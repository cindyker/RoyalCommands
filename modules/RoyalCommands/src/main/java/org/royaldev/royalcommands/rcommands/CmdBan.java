package org.royaldev.royalcommands.rcommands;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.royaldev.royalcommands.AuthorizationHandler.PermType;
import org.royaldev.royalcommands.Config;
import org.royaldev.royalcommands.MessageColor;
import org.royaldev.royalcommands.RUtils;
import org.royaldev.royalcommands.RoyalCommands;
import org.royaldev.royalcommands.configuration.PlayerConfiguration;
import org.royaldev.royalcommands.configuration.PlayerConfigurationManager;

import java.util.Date;

@ReflectCommand
public class CmdBan extends BaseCommand {

    public CmdBan(final RoyalCommands instance, final String name) {
        super(instance, name, true);
    }

    @Override
    public boolean runCommand(final CommandSender cs, final Command cmd, final String label, final String[] args) {
        if (args.length < 1) {
            cs.sendMessage(cmd.getDescription());
            return false;
        }
        OfflinePlayer t = this.plugin.getServer().getPlayer(args[0]);
        if (t == null) t = this.plugin.getServer().getOfflinePlayer(args[0]);
        PlayerConfiguration pcm = PlayerConfigurationManager.getConfiguration(t);
/*            if (!pcm.getConfExists()) {
                if (args.length > 1 && args[1].equalsIgnoreCase("true")) {
                    args = (String[]) ArrayUtils.remove(args, 1);
                } else {
                    cs.sendMessage(MessageColor.NEGATIVE + "That player does not exist!");
                    return true;
                }
            }*/
        if (!pcm.exists()) pcm.createFile();
        if (this.ah.isAuthorized(t, cmd, PermType.EXEMPT)) {
            cs.sendMessage(MessageColor.NEGATIVE + "You can't ban that player!");
            return true;
        }
        String banreason = (args.length > 1) ? RoyalCommands.getFinalArg(args, 1) : Config.banMessage;
        banreason = RUtils.colorize(banreason);
        pcm.set("banreason", banreason);
        pcm.set("banner", cs.getName());
        pcm.set("bannedat", new Date().getTime());
        pcm.set("bantime", null);
        cs.sendMessage(MessageColor.POSITIVE + "You have banned " + MessageColor.NEUTRAL + t.getName() + MessageColor.POSITIVE + ".");
        RUtils.banPlayer(t, cs, banreason);
        return true;
    }
}
