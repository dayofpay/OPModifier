package org.vdevs.opmodifier.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.vdevs.opmodifier.Handlers.LogHandler;
import org.vdevs.opmodifier.OPModifier;
import org.vdevs.opmodifier.Helpers.PlayerHelper;
import org.vdevs.opmodifier.Utils.ActionDeserializer;
import org.vdevs.opmodifier.Utils.DateUtils;

public class InventoryClickEvent implements Listener {
    private final OPModifier plugin;

    public InventoryClickEvent(final OPModifier plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(org.bukkit.event.inventory.InventoryClickEvent event) {
        PlayerHelper playerHelper = new PlayerHelper(plugin);
        ActionDeserializer actionDeserializer = new ActionDeserializer(plugin);
        Player player = (Player) event.getWhoClicked();
        if (event.getClickedInventory() != null && event.getClickedInventory().getType() == InventoryType.PLAYER) {


            if (plugin.getConfig().getStringList("blocked_items_move").contains(event.getCurrentItem().getType().toString()) && player.isOp() && !actionDeserializer.isWhitelistedPlayer(player.getName())) {


                event.setCancelled(true);
                event.getClickedInventory().removeItem(event.getClickedInventory().getItem(event.getSlot()));


                playerHelper.sendMessage(player,
                        plugin.getConfig().getString("messages.not_allowed_to_move_item")
                                .replace("%player%", player.getName())
                                .replace("%item%", event.getCurrentItem().getType().toString()));


                if (plugin.getConfig().getBoolean("violations.InventoryClick.punishment")) {
                    String punishCommand = plugin.getConfig().getString("violations.InventoryClick.punish_command")
                            .replace("%player%", player.getName());
                    plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), punishCommand);
                }
                if(plugin.getConfig().getBoolean("logger.status.VIOLATION_INVENTORY_INTERACT")){
                    String currentDate = DateUtils.getCurrentDate();

                    String message = plugin.getConfig().getString("logger.messages.VIOLATION_INVENTORY_INTERACT")
                            .replace("%date%", currentDate)
                            .replace("%player%", player.getName());

                    LogHandler.logData(message);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryMoveItem(InventoryMoveItemEvent event) {
        ActionDeserializer actionDeserializer = new ActionDeserializer(plugin);
        if (event.getDestination().getHolder() instanceof Player) {
            Player player = (Player) event.getDestination().getHolder();


            if (!actionDeserializer.isWhitelistedPlayer(player.getName()) && player.isOp() && plugin.getConfig().getStringList("blocked_items_move").contains(event.getItem().getType().toString())) {
                event.setCancelled(true);
                player.sendMessage(plugin.getConfig().getString("messages.not_allowed_to_move_item")
                        .replace("%player%", player.getName())
                        .replace("%item%", event.getItem().getType().toString()));

                if(plugin.getConfig().getBoolean("logger.status.VIOLATION_INVENTORY_INTERACT")){
                    String currentDate = DateUtils.getCurrentDate();

                    String message = plugin.getConfig().getString("logger.messages.VIOLATION_INVENTORY_INTERACT")
                            .replace("%date%", currentDate)
                            .replace("%player%", player.getName());

                    LogHandler.logData(message);
                }
            }
        }
    }
}
