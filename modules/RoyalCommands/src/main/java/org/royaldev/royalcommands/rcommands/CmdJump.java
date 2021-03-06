package org.royaldev.royalcommands.rcommands;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.royaldev.royalcommands.MessageColor;
import org.royaldev.royalcommands.RUtils;
import org.royaldev.royalcommands.RoyalCommands;

@ReflectCommand
public class CmdJump extends BaseCommand {

    public CmdJump(final RoyalCommands instance, final String name) {
        super(instance, name, true);
    }

    @Override
    public boolean runCommand(final CommandSender cs, final Command cmd, final String label, final String[] args) {
        if (!(cs instanceof Player)) {
            cs.sendMessage(MessageColor.NEGATIVE + "This command is only available to players!");
            return true;
        }
        Player p = (Player) cs;
        Block bb = RUtils.getTarget(p);
        if (bb == null) {
            cs.sendMessage(MessageColor.NEGATIVE + "Can't jump there!");
            return true;
        }
        Location bLoc = new Location(p.getWorld(), bb.getLocation().getX() + .5, bb.getLocation().getY() + 1, bb.getLocation().getZ() + .5, p.getLocation().getYaw(), p.getLocation().getPitch());
        String error = RUtils.teleport(p, bLoc);
        if (!error.isEmpty()) {
            p.sendMessage(MessageColor.NEGATIVE + error);
            return true;
        }
        return true;
    }
}
