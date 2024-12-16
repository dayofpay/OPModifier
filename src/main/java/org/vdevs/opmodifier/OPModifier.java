package org.vdevs.opmodifier;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.vdevs.opmodifier.Commands.OPModifyCommand;
import org.vdevs.opmodifier.Events.*;
import org.vdevs.opmodifier.Utils.ActionDeserializer;

import javax.swing.*;

public final class OPModifier extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        getLogger().info("OPModifier has been loaded");
        getCommand("opmodifier").setExecutor((new OPModifyCommand(this)));
        registerConditionalListeners();
    }

    @Override
    public void onDisable() {
        getLogger().info("OPModifier has been disabled");
    }

    private void registerConditionalListeners() {
        ActionDeserializer actionDeserializer = new ActionDeserializer(this);
        if(actionDeserializer.isBlockedAction("BlockPlaceEvent")){
            getServer().getPluginManager().registerEvents(new BlockPlaceEvent(this), this);
            getLogger().info("BlockPlaceEvent listener registered.");
        }

        if(actionDeserializer.isBlockedAction("CommandEvent")){
            getServer().getPluginManager().registerEvents(new CommandEvent(this), this);
            getLogger().info("CommandEvent listener registered.");
        }

        if(actionDeserializer.isBlockedAction("BlockBreakEvent")){
            getServer().getPluginManager().registerEvents(new BlockBreakEvent(this), this);
            getLogger().info("BlockBreakEvent listener registered.");
        }

        if(actionDeserializer.isBlockedAction("ItemUse")){
            getServer().getPluginManager().registerEvents(new ItemUseEvent(this), this);
            getLogger().info("ItemUse listener registered.");
        }

        if(actionDeserializer.isBlockedAction("ItemPickUp")){
            getServer().getPluginManager().registerEvents(new ItemPickupEvent(this), this);
            getLogger().info("ItemPickup listener registered.");
        }
        if(actionDeserializer.isBlockedAction("ItemDrop")){
            getServer().getPluginManager().registerEvents(new ItemDropEvent(this), this);
            getLogger().info("ItemDrop listener registered.");
        }

        if(actionDeserializer.isBlockedAction("InventoryClick")){
            getServer().getPluginManager().registerEvents(new InventoryClickEvent(this), this);
            getLogger().info("InventoryClick listener registered.");
        }

        if(actionDeserializer.isBlockedAction("PlayerTeleport")){
            getServer().getPluginManager().registerEvents(new PlayerTeleportEvent(this), this);
            getLogger().info("PlayerTeleport listener registered.");
        }
        if(actionDeserializer.isBlockedAction("PlayerChat")){
            getServer().getPluginManager().registerEvents(new PlayerChatEvent(this), this);
            getLogger().info("PlayerChat listener registered.");
        }
    }
}
