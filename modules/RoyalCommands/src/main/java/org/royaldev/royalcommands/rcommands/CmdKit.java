package org.royaldev.royalcommands.rcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.royaldev.royalcommands.Config;
import org.royaldev.royalcommands.MessageColor;
import org.royaldev.royalcommands.RUtils;
import org.royaldev.royalcommands.RoyalCommands;
import org.royaldev.royalcommands.rcommands.kits.Kit;
import org.royaldev.royalcommands.shaded.com.sk89q.util.config.ConfigurationNode;
import org.royaldev.royalcommands.shaded.com.sk89q.util.config.FancyConfiguration;
import org.royaldev.royalcommands.wrappers.player.MemoryRPlayer;
import org.royaldev.royalcommands.wrappers.player.RPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ReflectCommand
public class CmdKit extends TabCommand {

    public CmdKit(final RoyalCommands instance, final String name) {
        super(instance, name, true, new Short[]{CompletionType.LIST.getShort()});
    }

    @Override
    public List<String> customList(CommandSender cs, Command cmd, String label, String[] args, String arg) {
        final List<String> kits = new ArrayList<>();
        final FileConfiguration c = this.plugin.getConfig();
        if (!c.isSet("kits") || !c.isSet("kits.list")) return kits;
        for (final String kit : c.getConfigurationSection("kits.list").getKeys(false)) {
            if (Config.kitPerms && !this.ah.isAuthorized(cs, "rcmds.kit." + kit)) continue;
            kits.add(kit);
        }
        return kits;
    }

    @Override
    public boolean runCommand(final CommandSender cs, final Command cmd, final String label, final String[] eargs, final CommandArguments ca) {
        if (eargs.length < 1) {
            cs.sendMessage(cmd.getDescription());
            return false;
        }
        if (!(cs instanceof Player)) {
            cs.sendMessage(MessageColor.NEGATIVE + "This command is only available to players!");
            return true;
        }
        final Player p = (Player) cs;
        final RPlayer rp = MemoryRPlayer.getRPlayer(p);
        final String kitName = eargs[0];
        final FancyConfiguration config = this.plugin.getFancyConfig();
        final ConfigurationNode kitNode = config.getNode("kits.list." + kitName);
        if (kitNode == null) {
            cs.sendMessage(MessageColor.NEGATIVE + "No such kit!");
            return true;
        }
        final Kit kit = new Kit(kitName, kitNode);
        if (!kit.hasCooldownPassedFor(rp)) {
            if (kit.getCooldown() == -1L) {
                cs.sendMessage(MessageColor.NEGATIVE + "This kit can only be used once.");
            } else {
                cs.sendMessage(MessageColor.NEGATIVE + "The cooldown for this kit has not ended. You still have to wait" + MessageColor.NEUTRAL + RUtils.formatDateDiff(kit.getCooldownFor(rp)) + MessageColor.NEGATIVE + ".");
            }
            return true;
        }
        final List<ItemStack> items = kit.getItems();
        final Map<Integer, ItemStack> leftOver = p.getInventory().addItem(items.toArray(new ItemStack[items.size()]));
        for (final ItemStack is : leftOver.values()) {
            p.getWorld().dropItemNaturally(p.getLocation(), is);
        }
        kit.setLastUsed(rp, System.currentTimeMillis());
        cs.sendMessage(MessageColor.POSITIVE + "You have been given kit " + MessageColor.NEUTRAL + kit.getName() + MessageColor.POSITIVE + ".");
        return true;
    }
}
