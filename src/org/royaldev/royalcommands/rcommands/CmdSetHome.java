package org.royaldev.royalcommands.rcommands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.royaldev.royalcommands.PConfManager;
import org.royaldev.royalcommands.RUtils;
import org.royaldev.royalcommands.RoyalCommands;

import java.io.File;
import java.io.IOException;

public class CmdSetHome implements CommandExecutor {

    RoyalCommands plugin;

    public CmdSetHome(RoyalCommands plugin) {
        this.plugin = plugin;
    }

    private Integer getHomeLimit(Player p) {
        String name = p.getName();
        String group = RoyalCommands.permission.getPrimaryGroup(p);
        if (group == null) group = "";
        ConfigurationSection players = plugin.homeLimits.getConfigurationSection("players");
        ConfigurationSection groups = plugin.homeLimits.getConfigurationSection("groups");
        Integer limit;
        if (players != null && players.contains(name)) limit = players.getInt(name);
        else if (groups != null && groups.contains(group)) limit = groups.getInt(group);
        else limit = null;
        return limit;
    }

    private int getCurrentHomes(Player p) {
        ConfigurationSection pconf = PConfManager.getPConfSection(p, "home");
        if (pconf == null) return 0;
        return pconf.getValues(false).keySet().size();
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("sethome")) {
            if (!plugin.isAuthorized(cs, "rcmds.sethome")) {
                RUtils.dispNoPerms(cs);
                return true;
            }

            if (args.length > 0 && !plugin.isAuthorized(cs, "rcmds.sethome.multi")) {
                RUtils.dispNoPerms(cs, ChatColor.RED + "You don't have permission for multiple homes!");
                return true;
            }

            if (!(cs instanceof Player)) {
                cs.sendMessage(ChatColor.RED + "This command is only available to players!");
                return true;
            }
            Player p = (Player) cs;

            double locX = p.getLocation().getX();
            double locY = p.getLocation().getY();
            double locZ = p.getLocation().getZ();
            Float locYaw = p.getLocation().getYaw();
            Float locPitch = p.getLocation().getPitch();
            String locW = p.getWorld().getName();
            String name = "";
            if (args.length > 0) name = args[0];

            if (name.contains(":")) {
                cs.sendMessage(ChatColor.RED + "The name of your home cannot contain \":\"!");
                return true;
            }

            File pconfl = new File(plugin.getDataFolder() + File.separator + "userdata" + File.separator + cs.getName().toLowerCase() + ".yml");
            if (pconfl.exists()) {
                FileConfiguration pconf = YamlConfiguration.loadConfiguration(pconfl);
                Integer limit = getHomeLimit(p);
                int curHomes = getCurrentHomes(p);
                if (limit != null && pconf.get("home." + name) != null) {
                    if (limit == 0) {
                        RUtils.dispNoPerms(cs, ChatColor.RED + "Your home limit is set to " + ChatColor.GRAY + "0" + ChatColor.RED + "!");
                        cs.sendMessage(ChatColor.RED + "You can't set any homes!");
                        return true;
                    } else if (curHomes >= limit && limit > -1) {
                        RUtils.dispNoPerms(cs, ChatColor.RED + "You've reached your max number of homes! (" + ChatColor.GRAY + limit + ChatColor.RED + ")");
                        return true;
                    }
                }
                if (!name.equals("")) {
                    pconf.set("home." + name + ".set", true);
                    pconf.set("home." + name + ".x", locX);
                    pconf.set("home." + name + ".y", locY);
                    pconf.set("home." + name + ".z", locZ);
                    pconf.set("home." + name + ".pitch", locPitch.toString());
                    pconf.set("home." + name + ".yaw", locYaw.toString());
                    pconf.set("home." + name + ".w", locW);
                } else {
                    pconf.set("home.home.set", true);
                    pconf.set("home.home.x", locX);
                    pconf.set("home.home.y", locY);
                    pconf.set("home.home.z", locZ);
                    pconf.set("home.home.pitch", locPitch.toString());
                    pconf.set("home.home.yaw", locYaw.toString());
                    pconf.set("home.home.w", locW);
                }
                try {
                    pconf.save(pconfl);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (args.length > 0) {
                    p.sendMessage(ChatColor.BLUE + "Home \"" + ChatColor.GRAY + name + ChatColor.BLUE + "\" set.");
                } else {
                    p.sendMessage(ChatColor.BLUE + "Home set.");
                }
                return true;
            }
        }
        return false;
    }

}
