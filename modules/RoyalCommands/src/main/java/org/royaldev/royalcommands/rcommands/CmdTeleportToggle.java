package org.royaldev.royalcommands.rcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.royaldev.royalcommands.MessageColor;
import org.royaldev.royalcommands.RoyalCommands;
import org.royaldev.royalcommands.configuration.PlayerConfiguration;
import org.royaldev.royalcommands.configuration.PlayerConfigurationManager;

@ReflectCommand
public class CmdTeleportToggle extends BaseCommand {

    public CmdTeleportToggle(final RoyalCommands instance, final String name) {
        super(instance, name, true);
    }

    @Override
    public boolean runCommand(final CommandSender cs, final Command cmd, final String label, final String[] args) {
        if (!(cs instanceof Player)) {
            cs.sendMessage(MessageColor.NEGATIVE + "This command is only available to players.");
            return true;
        }
        Player p = (Player) cs;
        PlayerConfiguration pcm = PlayerConfigurationManager.getConfiguration(p);
        if (pcm.getBoolean("allow_tp")) {
            pcm.set("allow_tp", false);
            cs.sendMessage(MessageColor.POSITIVE + "Disabled teleportation.");
            return true;
        }
        pcm.set("allow_tp", true);
        cs.sendMessage(MessageColor.POSITIVE + "Enabled teleportation.");
        return true;
    }
}
