package tk.royalcraf.rcommands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tk.royalcraf.royalcommands.RoyalCommands;

public class TeleportHere implements CommandExecutor {

	RoyalCommands plugin;

	public TeleportHere(RoyalCommands plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label,
			String[] args) {
		if (cmd.getName().equalsIgnoreCase("teleporthere")) {
			if (!plugin.isAuthorized(cs, "rcmds.tphere")) {
				cs.sendMessage(ChatColor.RED
						+ "You don't have permission for that!");
				plugin.log.warning("[RoyalCommands] " + cs.getName()
						+ " was denied access to the command!");
				return true;
			}
			if (!(cs instanceof Player)) {
				cs.sendMessage(ChatColor.RED
						+ "This command is only available to players!");
				return true;
			}
			if (args.length < 1) {
				cs.sendMessage(cmd.getDescription());
				return false;
			}
			Player t = plugin.getServer().getPlayer(args[0].trim());
			if (t == null) {
				cs.sendMessage(ChatColor.RED + "That player does not exist!");
				return true;
			}
			if (plugin.isVanished(t)) {
				cs.sendMessage(ChatColor.RED + "That player does not exist!");
				return true;
			}
			Player p = (Player) cs;
			Back.backdb.put(t, t.getLocation());
			p.sendMessage(ChatColor.BLUE + "Teleporting " + ChatColor.GRAY
					+ t.getName() + ChatColor.BLUE + " to you.");
			t.teleport(p.getLocation());
			return true;
		}
		return false;
	}

}