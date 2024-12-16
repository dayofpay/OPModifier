package org.vdevs.opmodifier.Events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.vdevs.opmodifier.Helpers.PlayerHelper;
import org.vdevs.opmodifier.OPModifier;
import org.vdevs.opmodifier.Utils.ActionDeserializer;

public class ItemUseEvent implements Listener {
    private final OPModifier plugin;

    public ItemUseEvent(final OPModifier plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemUse(PlayerInteractEvent event) {
        PlayerHelper playerHelper = new PlayerHelper(plugin);


        if (event.hasItem() && event.getItem() != null) {
            Material itemMaterial = event.getItem().getType();


            if (plugin.getConfig().getStringList("blocked_items_use").contains(itemMaterial.toString())
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
        }
    }
}
