package org.royaldev.royalcommands.rcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.royaldev.royalcommands.AuthorizationHandler.PermType;
import org.royaldev.royalcommands.MessageColor;
import org.royaldev.royalcommands.RoyalCommands;

@ReflectCommand
public class CmdRide extends BaseCommand {

    public CmdRide(final RoyalCommands instance, final String name) {
        super(instance, name, true);
    }

    @Override
    public boolean runCommand(final CommandSender cs, final Command cmd, final String label, final String[] args) {
        if (!(cs instanceof Player)) {
            cs.sendMessage(MessageColor.NEGATIVE + "Only players are allowed to use this command.");
            return true;
        }
        Player p = (Player) cs;
        if (args.length < 1) {
            if (p.getVehicle() == null) {
                p.sendMessage(cmd.getDescription());
                return false;
            }
            p.getVehicle().eject();
            p.sendMessage(MessageColor.POSITIVE + "You have ejected.");
            return true;
        }
        if (args.length > 0) {
            Player t = this.plugin.getServer().getPlayer(args[0]);
            if (t == null || this.plugin.isVanished(t, cs)) {
                p.sendMessage(MessageColor.NEGATIVE + "That player does not exist!");
                return true;
            }
            if (p.equals(t)) {
                cs.sendMessage(MessageColor.NEGATIVE + "You cannot ride yourself.");
                return true;
            }
            if (this.ah.isAuthorized(t, cmd, PermType.EXEMPT)) {
                cs.sendMessage(MessageColor.NEGATIVE + "You cannot ride that player!");
                return true;
            }
            t.setPassenger(p);
            return true;
        }
        return true;
    }
}
