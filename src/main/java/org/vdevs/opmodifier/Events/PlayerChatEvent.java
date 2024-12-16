package org.vdevs.opmodifier.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.vdevs.opmodifier.Helpers.PlayerHelper;
import org.vdevs.opmodifier.OPModifier;

public class PlayerChatEvent implements Listener {
    private final OPModifier plugin;

    public PlayerChatEvent(final OPModifier plugin) {
        this.plugin = plugin;
    }
@EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (event.getPlayer().isOp()) {
            PlayerHelper playerHelper = new PlayerHelper(plugin);
            playerHelper.sendMessage(event.getPlayer(), plugin.getConfig().getString("messages.not_allowed_to_chat").replace("%player%", event.getPlayer().getName()));
            event.setCancelled(true);
            if (plugin.getConfig().getBoolean("violations.PlayerChat.punishment")) {
                String punishCommand = plugin.getConfig().getString("violations.PlayerChat.punish_command")
                        .replace("%player%", event.getPlayer().getName());
                plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), punishCommand);
            }
        }
    }
}
