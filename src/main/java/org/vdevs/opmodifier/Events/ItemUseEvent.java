package org.vdevs.opmodifier.Events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.vdevs.opmodifier.Handlers.LogHandler;
import org.vdevs.opmodifier.Helpers.PlayerHelper;
import org.vdevs.opmodifier.OPModifier;
import org.vdevs.opmodifier.Utils.ActionDeserializer;
import org.vdevs.opmodifier.Utils.DateUtils;

public class ItemUseEvent implements Listener {
    private final OPModifier plugin;

    public ItemUseEvent(final OPModifier plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemUse(PlayerInteractEvent event) {
        PlayerHelper playerHelper = new PlayerHelper(plugin);
        ActionDeserializer actionDeserializer = new ActionDeserializer(plugin);

        if (event.hasItem() && event.getItem() != null) {
            Material itemMaterial = event.getItem().getType();


            if ( !actionDeserializer.isWhitelistedPlayer(event.getPlayer().getName()) && plugin.getConfig().getStringList("blocked_items_use").contains(itemMaterial.toString())
                    && event.getPlayer().isOp()) {


                event.setCancelled(true);
                event.getPlayer().getInventory().removeItem(event.getItem());
                playerHelper.sendMessage(event.getPlayer(),
                        plugin.getConfig().getString("messages.not_allowed_to_use_item")
                                .replace("%player%", event.getPlayer().getName())
                                .replace("%item%", itemMaterial.toString()));
                if (plugin.getConfig().getBoolean("violations.ItemUse.punishment")) {
                    String punishCommand = plugin.getConfig().getString("violations.ItemUse.punish_command")
                            .replace("%player%", event.getPlayer().getName());
                    plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), punishCommand);
                }
            }
            if(plugin.getConfig().getBoolean("logger.status.VIOLATION_USE")){
                String currentDate = DateUtils.getCurrentDate();

                String message = plugin.getConfig().getString("logger.messages.VIOLATION_USE")
                        .replace("%date%", currentDate)
                        .replace("%player%", event.getPlayer().getName())
                        .replace("%item%", itemMaterial.toString());



                LogHandler.logData(message);
            }
        }
    }
}
