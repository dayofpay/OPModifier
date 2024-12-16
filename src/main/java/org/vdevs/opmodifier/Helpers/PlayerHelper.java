package org.vdevs.opmodifier.Helpers;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.vdevs.opmodifier.OPModifier;

public class PlayerHelper {
    private final OPModifier plugin;

    public PlayerHelper(final OPModifier plugin) {
        this.plugin = plugin;
    }
    public void sendMessage(final Player player, final String message) {
        String plugin_prefix = plugin.getConfig().getString("messages.plugin_prefix");
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',plugin_prefix+message));
    }
}
