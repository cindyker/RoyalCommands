package org.royaldev.royalcommands.runners;

import org.bukkit.entity.Player;
import org.royaldev.royalcommands.RUtils;
import org.royaldev.royalcommands.RoyalCommands;

public class MailRunner implements Runnable {

    private final RoyalCommands plugin;

    public MailRunner(RoyalCommands instance) {
        this.plugin = instance;
    }

    @Override
    public void run() {
        for (final Player p : this.plugin.getServer().getOnlinePlayers()) {
            RUtils.checkMail(p);
        }
    }

}
