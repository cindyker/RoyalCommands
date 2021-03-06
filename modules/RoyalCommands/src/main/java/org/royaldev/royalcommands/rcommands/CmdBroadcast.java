package org.royaldev.royalcommands.rcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.royaldev.royalcommands.Config;
import org.royaldev.royalcommands.RUtils;
import org.royaldev.royalcommands.RoyalCommands;

@ReflectCommand
public class CmdBroadcast extends BaseCommand {

    public CmdBroadcast(final RoyalCommands instance, final String name) {
        super(instance, name, true);
    }

    @Override
    public boolean runCommand(final CommandSender cs, final Command cmd, final String label, final String[] args) {
        if (args.length < 1) {
            cs.sendMessage(cmd.getDescription());
            return false;
        }
        String message = RUtils.colorize(RoyalCommands.getFinalArg(args, 0));
        String format = Config.bcastFormat;
        message = format + message;
        this.plugin.getServer().broadcastMessage(message);
        return true;
    }
}
