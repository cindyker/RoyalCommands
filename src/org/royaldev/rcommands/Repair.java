package org.royaldev.rcommands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.royaldev.royalcommands.RoyalCommands;

public class Repair implements CommandExecutor {

    RoyalCommands plugin;

    public Repair(RoyalCommands plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label,
                             String[] args) {
        if (cmd.getName().equalsIgnoreCase("repair")) {
            if (!plugin.isAuthorized(cs, "rcmds.repair")) {
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
                Player p = (Player) cs;
                ItemStack hand = p.getItemInHand();
                if (hand.getTypeId() == 0) {
                    cs.sendMessage(ChatColor.RED + "You can't repair air!");
                    return true;
                }
                if (hand.getDurability() == (short) 0) {
                    cs.sendMessage(ChatColor.RED
                            + "That doesn't need to be repaired!");
                    return true;
                }
                hand.setDurability((short) 0);
                cs.sendMessage(ChatColor.BLUE
                        + "Fixed your "
                        + ChatColor.GRAY
                        + hand.getType().toString().toLowerCase()
                        .replace("_", " ") + ChatColor.BLUE + ".");
                return true;
            }
            if (args.length > 0) {
                // if (args[0].trim().toLowerCase() == "all") {
                Player p = (Player) cs;
                ItemStack[] pInv = p.getInventory().getContents();
                String items = "";
                for (ItemStack aPInv : pInv) {
                    if (aPInv != null && aPInv.getTypeId() != 0
                            && aPInv.getDurability() != (short) 0) {
                        aPInv.setDurability((short) 0);
                        if (items.equals("")) {
                            items = items.concat(aPInv.getType().toString()
                                    .toLowerCase().replace("_", " "));
                        } else {
                            items = items.concat(", "
                                    + aPInv.getType().toString()
                                    .toLowerCase().replace("_", " "));
                        }
                    }
                }
                if (!items.equals("")) {
                    cs.sendMessage(ChatColor.BLUE + "Fixed: " + ChatColor.GRAY
                            + items + ChatColor.BLUE + ".");
                    return true;
                }
                cs.sendMessage(ChatColor.RED + "You have nothing to repair!");
                return true;
                // } else {
                // return false;
                // }
            }
        }
        return false;
    }

}