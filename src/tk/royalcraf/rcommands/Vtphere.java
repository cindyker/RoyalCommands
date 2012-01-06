package tk.royalcraf.rcommands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tk.royalcraf.royalcommands.RoyalCommands;

public class Vtphere implements CommandExecutor {

	RoyalCommands plugin;

	public Vtphere(RoyalCommands plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label,
			String[] args) {
		if (cmd.getName().equalsIgnoreCase("vtphere")) {
			if (!plugin.isAuthorized(cs, "rcmds.vtphere")) {
				cs.sendMessage(ChatColor.RED
						+ "You don't have permission for that!");
				plugin.log.warning("[RoyalCommands] " + cs.getName()
						+ " was denied access to the command!");
				return true;
			}
			if (args.length < 1) {
				cs.sendMessage(cmd.getDescription());
				return false;
			}
			Player victim = plugin.getServer().getPlayer(args[0]);
			if (victim == null) {
				cs.sendMessage(ChatColor.RED + "That player does not exist!");
				return true;
			}
			if (plugin.isVanished(victim)) {
				cs.sendMessage(ChatColor.RED + "That player does not exist!");
				return true;
			}
			if (!(cs instanceof Player)) {
				cs.sendMessage(ChatColor.RED
						+ "This command cannot be used in console.");
				return true;
			}
			Player player = (Player) cs;
			cs.sendMessage(ChatColor.BLUE + "Teleporting player "
					+ ChatColor.GRAY + victim.getName() + ChatColor.BLUE
					+ " to you.");
			victim.sendMessage(ChatColor.BLUE + "You are being teleported to "
					+ ChatColor.GRAY + cs.getName() + ChatColor.BLUE + ".");
			Back.backdb.put(victim, victim.getLocation());
			victim.teleport(player.getLocation());
			return true;
		}
		return false;
	}

}