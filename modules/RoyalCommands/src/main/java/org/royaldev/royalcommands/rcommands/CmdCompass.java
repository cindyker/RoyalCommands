package org.royaldev.royalcommands.rcommands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.royaldev.royalcommands.MessageColor;
import org.royaldev.royalcommands.RoyalCommands;

@ReflectCommand
public class CmdCompass extends BaseCommand {

    public CmdCompass(final RoyalCommands instance, final String name) {
        super(instance, name, true);
    }

    @Override
    public boolean runCommand(final CommandSender cs, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equals("compass")) {
            if (!(cs instanceof Player)) {
                cs.sendMessage(MessageColor.NEGATIVE + "This command is only available to players!");
                return true;
            }
            if (args.length < 1) {
                cs.sendMessage(cmd.getDescription());
                return false;
            }
            Player p = (Player) cs;
            String command = args[0];
            if (args.length < 2) {
                if (command.equalsIgnoreCase("here")) {
                    p.setCompassTarget(p.getLocation());
                    cs.sendMessage(MessageColor.POSITIVE + "Your compass now points to your current location.");
                    return true;
                } else if (command.equalsIgnoreCase("reset")) {
                    p.setCompassTarget((p.getBedSpawnLocation() != null) ? p.getBedSpawnLocation() : CmdSpawn.getWorldSpawn(p.getWorld()));
                    p.sendMessage(MessageColor.POSITIVE + "Reset your compass.");
                    return true;
                } else {
                    cs.sendMessage(cmd.getDescription());
                    return false;
                }
            }
            if (command.equalsIgnoreCase("player")) {
                Player t = this.plugin.getServer().getPlayer(args[1]);
                if (t == null || this.plugin.isVanished(t, cs)) {
                    cs.sendMessage(MessageColor.NEGATIVE + "That player does not exist!");
                    return true;
                }
                p.setCompassTarget(t.getLocation());
                cs.sendMessage(MessageColor.POSITIVE + "Your compass now points towards " + MessageColor.NEUTRAL + t.getName() + MessageColor.POSITIVE + ".");
                return true;
            }
            if (command.equalsIgnoreCase("location")) {
                if (args.length < 4) {
                    cs.sendMessage(cmd.getDescription());
                    return false;
                }
                double x;
                double y;
                double z;
                try {
                    x = Double.valueOf(args[1]);
                    y = Double.valueOf(args[2]);
                    z = Double.valueOf(args[3]);
                } catch (Exception e) {
                    cs.sendMessage(MessageColor.NEGATIVE + "Those coordinates are invalid!");
                    return true;
                }
                Location cLocation = new Location(p.getWorld(), x, y, z);
                p.setCompassTarget(cLocation);
                cs.sendMessage(MessageColor.POSITIVE + "Your compass now points towards " + MessageColor.NEUTRAL + x + MessageColor.POSITIVE + ", " + MessageColor.NEUTRAL + y + MessageColor.POSITIVE + ", " + MessageColor.NEUTRAL + z + MessageColor.POSITIVE + ".");
                return true;
            }
        }
        return false;
    }
}
