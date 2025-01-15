package org.vdevs.opmodifier.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.vdevs.opmodifier.Handlers.LogHandler;
import org.vdevs.opmodifier.Helpers.PlayerHelper;
import org.vdevs.opmodifier.OPModifier;
import org.vdevs.opmodifier.Utils.ActionDeserializer;
import org.vdevs.opmodifier.Utils.DateUtils;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class BlockBreakEvent implements Listener {
    private final OPModifier plugin;

    public BlockBreakEvent(final OPModifier plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(org.bukkit.event.block.BlockBreakEvent block) {
        ActionDeserializer actionDeserializer = new ActionDeserializer(plugin);
        PlayerHelper playerHelper = new PlayerHelper(plugin);
        Player player = block.getPlayer();


        if (actionDeserializer.isBlockedAction("BlockBreakEvent") && player.isOp() && !actionDeserializer.isWhitelistedPlayer(player.getName())) {
            block.setCancelled(true);


            playerHelper.sendMessage(player,
                    plugin.getConfig().getString("messages.not_allowed_to_break_blocks")
                            .replace("%player%", player.getName()));


            if (plugin.getConfig().getBoolean("violations.BlockBreakEvent.punishment")) {
                String punishCommand = plugin.getConfig().getString("violations.BlockBreakEvent.punish_command")
                        .replace("%player%", player.getName());

                plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), punishCommand);
            }

            if(plugin.getConfig().getBoolean("logger.status.VIOLATION_BLOCK_BREAK")){
                String currentDate = DateUtils.getCurrentDate();

                String message = plugin.getConfig().getString("logger.messages.VIOLATION_BLOCK_BREAK")
                        .replace("%date%", currentDate)
                        .replace("%player%", player.getName())
                        .replace("%block%", block.getBlock().getType().toString());

                LogHandler.logData(message);
            }
        }
    }
}
