package org.royaldev.royalcommands.nms.api;

import org.bukkit.entity.Player;

public interface NMSFace {
    /**
     * Gets the ping reported to the server by the client.
     *
     * @param p Player to get ping of
     * @return ping
     */
    public int getPing(Player p);

    /**
     * Gets the version of the NMS support (e.g. v1_4_5).
     *
     * @return Version string
     */
    public String getVersion();

    /**
     * Checks to see if this is offering NMS/CB internals support.
     * <p/>
     * Note that if this returns false, calling any method besides this and getVersion() will throw an
     * {@link UnsupportedOperationException}.
     * If this is true, NMS support is enabled.
     *
     * @return true/false
     */
    public boolean hasSupport();

}
