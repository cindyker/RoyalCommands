package org.royaldev.royalcommands.rcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.royaldev.royalcommands.MessageColor;
import org.royaldev.royalcommands.RUtils;
import org.royaldev.royalcommands.RoyalCommands;
import org.royaldev.royalcommands.rcommands.teleport.TeleportRequest;

@ReflectCommand
public class CmdTeleportRequestHere implements CommandExecutor {

    private final RoyalCommands plugin;

    public CmdTeleportRequestHere(RoyalCommands plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("teleportrequesthere")) {
            if (!plugin.ah.isAuthorized(cs, "rcmds.teleportrequesthere")) {
                RUtils.dispNoPerms(cs);
                return true;
            }
            if (args.length < 1) {
                cs.sendMessage(cmd.getDescription());
                return false;
            }
            if (!(cs instanceof Player)) {
                cs.sendMessage(MessageColor.NEGATIVE + "This command is only available to players!");
                return true;
            }
            final Player p = (Player) cs;
            final Player t = plugin.getServer().getPlayer(args[0]);
            if (t == null || plugin.isVanished(t, cs)) {
                cs.sendMessage(MessageColor.NEGATIVE + "That player does not exist!");
                return true;
            }
            if (!RUtils.isTeleportAllowed(t) && !plugin.ah.isAuthorized(cs, "rcmds.tpoverride")) {
                cs.sendMessage(MessageColor.NEGATIVE + "That player has teleportation off!");
                return true;
            }
            TeleportRequest.send(p, t, TeleportRequest.TeleportType.HERE);
            return true;
        }
        return false;
    }

}