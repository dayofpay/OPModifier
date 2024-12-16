package org.vdevs.opmodifier.Commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.vdevs.opmodifier.Helpers.PlayerHelper;
import org.vdevs.opmodifier.OPModifier;

import java.util.List;

import static java.lang.String.valueOf;

public class OPModifyCommand implements CommandExecutor {
    private final OPModifier plugin;

    public OPModifyCommand(OPModifier plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        PlayerHelper playerHelper = new PlayerHelper(plugin);
        if (args.length != 2 || !(args[0].equals("add") || args[0].equals("remove"))) {
            String message = plugin.getConfig().getString("messages.invalid_category");
            playerHelper.sendMessage(sender.getServer().getPlayer(sender.getName()), message);
            return false;
        }

        String action = args[0];
        String category = args[1];

        if (!category.equals("blocked_items_use") && !category.equals("blocked_items_pickup") &&
                !category.equals("blocked_items_drop") && !category.equals("blocked_items_move")) {
            String message = plugin.getConfig().getString("messages.invalid_category");
            playerHelper.sendMessage(sender.getServer().getPlayer(sender.getName()), message);
            return false;
        }

        if (!(sender instanceof Player)) {
            String message = plugin.getConfig().getString("messages.not_player");
            playerHelper.sendMessage(sender.getServer().getPlayer(sender.getName()), message);
            return false;
        }

        Player player = (Player) sender;
        Material itemInHand = player.getInventory().getItemInHand().getType();

        if (itemInHand == Material.AIR) {
            String message = plugin.getConfig().getString("messages.not_holding_item");
            playerHelper.sendMessage(sender.getServer().getPlayer(sender.getName()), message.replace("%item%", valueOf(itemInHand)).replace("%category%", category));
            return false;
        }

        List<String> blockedItems = plugin.getConfig().getStringList(category);

        if (action.equals("add")) {
            if (!blockedItems.contains(itemInHand.toString())) {
                blockedItems.add(itemInHand.toString());
                plugin.getConfig().set(category, blockedItems);
                String message = plugin.getConfig().getString("messages.item_added");
                playerHelper.sendMessage(sender.getServer().getPlayer(sender.getName()), message.replace("%item%", valueOf(itemInHand)).replace("%category%", category));
            } else {
                String message = plugin.getConfig().getString("messages.item_already_in_category");
                playerHelper.sendMessage(sender.getServer().getPlayer(sender.getName()), message.replace("%item%", valueOf(itemInHand)).replace("%category%", category));
            }
        } else if (action.equals("remove")) {
            if (blockedItems.contains(itemInHand.toString())) {
                blockedItems.remove(itemInHand.toString());
                plugin.getConfig().set(category, blockedItems);
                String message = plugin.getConfig().getString("messages.item_removed");
                playerHelper.sendMessage(sender.getServer().getPlayer(sender.getName()), message.replace("%item%", valueOf(itemInHand)).replace("%category%", category));
            } else {
                String message = plugin.getConfig().getString("messages.item_not_in_category");
                playerHelper.sendMessage(sender.getServer().getPlayer(sender.getName()), message.replace("%item%", valueOf(itemInHand)).replace("%category%", category));
            }
        }

        plugin.saveConfig();
        plugin.reloadConfig();
        return true;
    }

}
