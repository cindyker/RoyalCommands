package org.royaldev.royalcommands.rcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.royaldev.royalcommands.Config;
import org.royaldev.royalcommands.MessageColor;
import org.royaldev.royalcommands.RoyalCommands;

import java.util.List;

@ReflectCommand
public class CmdNear extends BaseCommand {

    public CmdNear(final RoyalCommands instance, final String name) {
        super(instance, name, true);
    }

    @Override
    public boolean runCommand(final CommandSender cs, final Command cmd, final String label, final String[] args) {
        if (!(cs instanceof Player)) {
            cs.sendMessage(MessageColor.NEGATIVE + "This command is only available to players!");
            return true;
        }
        if (args.length < 1) {
            Player p = (Player) cs;
            double radius = Config.defaultNear;
            List<Entity> ents = p.getNearbyEntities(radius, radius, radius);
            int amount = 0;
            for (Entity e : ents) {
                if (!(e instanceof Player)) continue;
                Player t = (Player) e;
                if (this.plugin.isVanished(t, cs)) continue;
                double dist = p.getLocation().distanceSquared(t.getLocation());
                p.sendMessage(MessageColor.NEUTRAL + t.getDisplayName() + ": " + MessageColor.RESET + Math.sqrt(dist));
                amount++;
            }
            if (amount == 0) {
                p.sendMessage(MessageColor.NEGATIVE + "No one nearby!");
                return true;
            }
            return true;
        }
        if (args.length > 0) {
            Player p = (Player) cs;
            Double radius;
            try {
                radius = Double.parseDouble(args[0]);
            } catch (Exception e) {
                cs.sendMessage(MessageColor.NEGATIVE + "That was not a valid number!");
                return true;
            }
            if (radius == null || radius < 1) {
                cs.sendMessage(MessageColor.NEGATIVE + "That was not a valid number!");
                return true;
            }
            if (radius > Config.maxNear) {
                p.sendMessage(MessageColor.NEGATIVE + "That radius was too large!");
                return true;
            }
            List<Entity> ents = p.getNearbyEntities(radius, radius, radius);
            int amount = 0;
            for (Entity e : ents) {
                if (!(e instanceof Player)) continue;
                Player t = (Player) e;
                if (this.plugin.isVanished(t, cs)) continue;
                double dist = p.getLocation().distanceSquared(t.getLocation());
                p.sendMessage(MessageColor.NEUTRAL + t.getDisplayName() + ": " + MessageColor.RESET + Math.sqrt(dist));
                amount++;
            }
            if (amount == 0) {
                p.sendMessage(MessageColor.NEGATIVE + "No one nearby!");
                return true;
            }
            return true;
        }
        return true;
    }
}
