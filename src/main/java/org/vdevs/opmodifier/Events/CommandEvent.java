package org.vdevs.opmodifier.Events;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.vdevs.opmodifier.Handlers.LogHandler;
import org.vdevs.opmodifier.Helpers.PlayerHelper;
import org.vdevs.opmodifier.OPModifier;
import org.vdevs.opmodifier.Utils.DateUtils;
public class CommandEvent implements Listener {
    private OPModifier plugin;
    public CommandEvent(OPModifier plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerCommand(final PlayerCommandPreprocessEvent e) {
    final String msg = e.getMessage().toLowerCase();
    PlayerHelper playerHelper = new PlayerHelper(plugin);
    if (isBlockedCommand(msg)) {
        e.setCancelled(true);
        notifyPlayer(e, playerHelper);
        notifyAdmins(e, playerHelper, msg);
        logToConsole(e, msg);
        if (plugin.getConfig().getBoolean("violations.CommandEvent.punishment")) {
            String punishCommand = plugin.getConfig().getString("violations.CommandEvent.punish_command")
                    .replace("%player%", e.getPlayer().getName());

            plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), punishCommand);
        }
        if(plugin.getConfig().getBoolean("logger.status.VIOLATION_COMMAND")){
            String currentDate = DateUtils.getCurrentDate();

            String message = plugin.getConfig().getString("logger.messages.VIOLATION_COMMAND")
                    .replace("%date%", currentDate)
                    .replace("%player%", e.getPlayer().getName())
                    .replace("%command%", msg);

            LogHandler.logData(message);
        }
    }
}

    private boolean isBlockedCommand(final String msg) {

        String command = msg.split(" ")[0].toLowerCase();


        for (String blockedCommand : plugin.getConfig().getStringList("blocked_commands")) {

            if (command.equalsIgnoreCase(blockedCommand.toLowerCase())) {
                return true;
            }
        }
        return false;
    }


private void notifyPlayer(final PlayerCommandPreprocessEvent e, PlayerHelper playerHelper) {
    playerHelper.sendMessage(
        e.getPlayer(),
        plugin.getConfig().getString("messages.command_not_allowed")
            .replace("%player%", e.getPlayer().getName())
    );
}

private void notifyAdmins(final PlayerCommandPreprocessEvent e, PlayerHelper playerHelper, final String msg) {
    if (plugin.getConfig().getBoolean("settings.notify_admins")) {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            if (player.hasPermission("opmodifier.notify")) {
                playerHelper.sendMessage(
                    player,
                    plugin.getConfig().getString("messages.admin_notification")
                        .replace("%player%", e.getPlayer().getName())
                        .replace("%command%", msg)
                );
            }
        }
    }
}

private void logToConsole(final PlayerCommandPreprocessEvent e, final String msg) {
    if (plugin.getConfig().getBoolean("settings.log_console")) {
        plugin.getServer().getConsoleSender().sendMessage(
            ChatColor.RED + "[OPModifier] " +
            plugin.getConfig().getString("messages.console_notification")
                .replace("%player%", e.getPlayer().getName())
                .replace("%command%", msg)
        );
    }
}
}
