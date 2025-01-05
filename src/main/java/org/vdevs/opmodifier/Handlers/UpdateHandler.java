package org.vdevs.opmodifier.Handlers;

import com.tchristofferson.configupdater.ConfigUpdater;
import org.bukkit.configuration.Configuration;
import org.vdevs.opmodifier.OPModifier;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class UpdateHandler {
    private final OPModifier plugin;
    public UpdateHandler(final OPModifier plugin) {
        this.plugin = plugin;
    }
    public void checkConfigUpdates() {
        plugin.saveDefaultConfig();
        File configFile = new File(plugin.getDataFolder(), "config.yml");

        try {
            ConfigUpdater.update(plugin, "config.yml", configFile, Arrays.asList());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
