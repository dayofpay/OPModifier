package org.vdevs.opmodifier.Events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.vdevs.opmodifier.Helpers.PlayerHelper;
import org.vdevs.opmodifier.OPModifier;

public class ItemDropEvent implements Listener {
    private final OPModifier plugin;

    public ItemDropEvent(final OPModifier plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        Material itemMaterial = event.getItemDrop().getItemStack().getType();
        PlayerHelper playerHelper = new PlayerHelper(plugin);


        if (plugin.getConfig().getStringList("blocked_items_drop").contains(itemMaterial.toString())
                && player.isOp()) {


            event.setCancelled(true);


            playerHelper.sendMessage(player,
                    plugin.getConfig().getString("messages.not_allowed_to_drop_item")
                            .replace("%player%", player.getName())
                            .replace("%item%", itemMaterial.toString()));


            if (plugin.getConfig().getBoolean("violations.ItemDrop.punishment")) {
                String punishCommand = plugin.getConfig().getString("violations.ItemDrop.punish_command")
                        .replace("%player%", player.getName());
                plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), punishCommand);
            }
        }
    }
}
