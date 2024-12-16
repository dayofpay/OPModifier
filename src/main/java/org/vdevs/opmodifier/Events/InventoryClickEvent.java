package org.vdevs.opmodifier.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.vdevs.opmodifier.OPModifier;
import org.vdevs.opmodifier.Helpers.PlayerHelper;

public class InventoryClickEvent implements Listener {
    private final OPModifier plugin;

    public InventoryClickEvent(final OPModifier plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(org.bukkit.event.inventory.InventoryClickEvent event) {
        PlayerHelper playerHelper = new PlayerHelper(plugin);

        Player player = (Player) event.getWhoClicked();
        if (event.getClickedInventory() != null && event.getClickedInventory().getType() == InventoryType.PLAYER) {


            if (plugin.getConfig().getStringList("blocked_items_move").contains(event.getCurrentItem().getType().toString()) && player.isOp()) {


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
            }
        }
    }

    @EventHandler
    public void onInventoryMoveItem(InventoryMoveItemEvent event) {

        if (event.getDestination().getHolder() instanceof Player) {
            Player player = (Player) event.getDestination().getHolder();


            if (player.isOp() && plugin.getConfig().getStringList("blocked_items_move").contains(event.getItem().getType().toString())) {
                event.setCancelled(true);
                player.sendMessage(plugin.getConfig().getString("messages.not_allowed_to_move_item")
                        .replace("%player%", player.getName())
                        .replace("%item%", event.getItem().getType().toString()));


            }
        }
    }
}
