package tk.royalcraf.rcommands;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tk.royalcraf.royalcommands.PConfManager;
import tk.royalcraf.royalcommands.RoyalCommands;

public class Freeze implements CommandExecutor {

	RoyalCommands plugin;

	public Freeze(RoyalCommands plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label,
			String[] args) {
		if (cmd.getName().equalsIgnoreCase("freeze")) {
			if (!plugin.isAuthorized(cs, "rcmds.freeze")) {
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
			if (victim != null) {
				if (plugin.isAuthorized(victim, "rcmds.exempt.freeze")) {
					cs.sendMessage(ChatColor.RED
							+ "You can't freeze that player!");
					return true;
				}
				if (!PConfManager.getPValBoolean((OfflinePlayer) victim, "frozen")) {
					PConfManager.setPValBoolean((OfflinePlayer) victim, true,
							"frozen");
					cs.sendMessage(ChatColor.BLUE + "You have frozen "
							+ ChatColor.GRAY + victim.getName()
							+ ChatColor.BLUE + "!");
					victim.sendMessage(ChatColor.RED + "You have been frozen!");
					return true;
				} else {
					PConfManager.setPValBoolean((OfflinePlayer) victim, false,
							"frozen");
					cs.sendMessage(ChatColor.BLUE + "You have thawed "
							+ ChatColor.GRAY + victim.getName()
							+ ChatColor.BLUE + "!");
					victim.sendMessage(ChatColor.BLUE + "You have been thawed!");
					return true;
				}
			} else {
				OfflinePlayer victim2 = plugin.getServer().getOfflinePlayer(
						args[0].trim());
				if (victim2.isOp()) {
					cs.sendMessage(ChatColor.RED
							+ "You can't freeze that player!");
					return true;
				}
				if (!PConfManager.getPConfExists(victim2)) {
					cs.sendMessage(ChatColor.RED
							+ "That player does not exist!");
					return true;
				}
				if (!PConfManager.getPValBoolean(victim2, "frozen")) {
					PConfManager.setPValBoolean(victim2, true, "frozen");
					cs.sendMessage(ChatColor.BLUE + "You have frozen "
							+ ChatColor.GRAY + victim2.getName()
							+ ChatColor.BLUE + "!");
					return true;
				} else {
					PConfManager.setPValBoolean(victim2, false, "frozen");
					cs.sendMessage(ChatColor.BLUE + "You have thawed "
							+ ChatColor.GRAY + victim2.getName()
							+ ChatColor.BLUE + "!");
					return true;
				}
			}
		}
		return false;
	}
}