package org.vdevs.opmodifier.Events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.vdevs.opmodifier.Handlers.LogHandler;
import org.vdevs.opmodifier.Helpers.PlayerHelper;
import org.vdevs.opmodifier.OPModifier;
import org.vdevs.opmodifier.Utils.ActionDeserializer;
import org.vdevs.opmodifier.Utils.DateUtils;

public class ItemPickupEvent implements Listener {
    private final OPModifier plugin;

    public ItemPickupEvent(final OPModifier plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        Material itemMaterial = event.getItem().getItemStack().getType();
        PlayerHelper playerHelper = new PlayerHelper(plugin);
        ActionDeserializer actionDeserializer = new ActionDeserializer(plugin);

        if (!actionDeserializer.isWhitelistedPlayer(player.getName()) && plugin.getConfig().getStringList("blocked_items_pickup").contains(itemMaterial.toString())
                && player.isOp()) {


            event.setCancelled(true);


            playerHelper.sendMessage(player,
                    plugin.getConfig().getString("messages.not_allowed_to_pickup_item")
                            .replace("%player%", player.getName())
                            .replace("%item%", itemMaterial.toString()));


            if (plugin.getConfig().getBoolean("violations.ItemPickUp.punishment")) {
                String punishCommand = plugin.getConfig().getString("violations.ItemPickUp.punish_command")
                        .replace("%player%", player.getName());
                plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), punishCommand);
            }
            if(plugin.getConfig().getBoolean("logger.status.VIOLATION_PICKUP")){
                String currentDate = DateUtils.getCurrentDate();

                String message = plugin.getConfig().getString("logger.messages.VIOLATION_PICKUP")
                        .replace("%date%", currentDate)
                        .replace("%player%", player.getName()
                                .replace("%item%", itemMaterial.toString()));

                LogHandler.logData(message);
            }
        }
    }
}
