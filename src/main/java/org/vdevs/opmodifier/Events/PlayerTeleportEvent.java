package org.vdevs.opmodifier.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.vdevs.opmodifier.Helpers.PlayerHelper;
import org.vdevs.opmodifier.OPModifier;
import org.vdevs.opmodifier.Utils.ActionDeserializer;

public class PlayerTeleportEvent implements Listener {
    private final OPModifier plugin;

    public PlayerTeleportEvent(final OPModifier plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onTeleport(org.bukkit.event.player.PlayerTeleportEvent event){
        ActionDeserializer actionDeserializer = new ActionDeserializer(plugin);
        if( !actionDeserializer.isWhitelistedPlayer(event.getPlayer().getName()) && actionDeserializer.isBlockedAction("PlayerTeleport") && event.getPlayer().isOp()){
            PlayerHelper playerHelper = new PlayerHelper(plugin);
            playerHelper.sendMessage(event.getPlayer(), plugin.getConfig().getString("messages.not_allowed_to_teleport").replace("%player%", event.getPlayer().getName()));
            event.setCancelled(true);
            if (plugin.getConfig().getBoolean("violations.PlayerTeleport.punishment")) {
                String punishCommand = plugin.getConfig().getString("violations.PlayerTeleport.punish_command")
                        .replace("%player%", event.getPlayer().getName());
                plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), punishCommand);
            }
        }
    }
}
