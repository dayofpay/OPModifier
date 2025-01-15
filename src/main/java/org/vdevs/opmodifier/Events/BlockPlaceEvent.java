package org.vdevs.opmodifier.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.vdevs.opmodifier.Handlers.LogHandler;
import org.vdevs.opmodifier.Helpers.PlayerHelper;
import org.vdevs.opmodifier.OPModifier;
import org.vdevs.opmodifier.Utils.ActionDeserializer;
import org.vdevs.opmodifier.Utils.DateUtils;

public class BlockPlaceEvent implements Listener {
    private final OPModifier plugin;

    public BlockPlaceEvent(final OPModifier plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onBlockPlace(org.bukkit.event.block.BlockPlaceEvent block){
        ActionDeserializer actionDeserializer = new ActionDeserializer(plugin);
        PlayerHelper playerHelper = new PlayerHelper(plugin);
        if(actionDeserializer.isBlockedAction("BlockPlaceEvent") && block.getPlayer().isOp() && !actionDeserializer.isWhitelistedPlayer(block.getPlayer().getName())){
            block.setCancelled(true);
            playerHelper.sendMessage(block.getPlayer(), plugin.getConfig().getString("messages.not_allowed_to_place_blocks").replace("%player%", block.getPlayer().getName()));
            if(plugin.getConfig().getBoolean("logger.status.VIOLATION_BLOCK_PLACE")){
                String currentDate = DateUtils.getCurrentDate();

                String message = plugin.getConfig().getString("logger.messages.VIOLATION_BLOCK_PLACE")
                        .replace("%date%", currentDate)
                        .replace("%player%", block.getPlayer().getName())
                        .replace("%block%", block.getBlock().getType().toString());

                LogHandler.logData(message);
            }
            if (plugin.getConfig().getBoolean("violations.BlockPlaceEvent.punishment")) {
                String punishCommand = plugin.getConfig().getString("violations.BlockPlaceEvent.punish_command")
                        .replace("%player%", block.getPlayer().getName());

                plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), punishCommand);
            }
        }
    }
}
