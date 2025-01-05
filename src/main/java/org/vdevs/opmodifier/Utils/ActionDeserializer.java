package org.vdevs.opmodifier.Utils;

import org.vdevs.opmodifier.OPModifier;

import java.util.List;

public class ActionDeserializer {

    private final OPModifier plugin;

    public ActionDeserializer(final OPModifier plugin) {
        this.plugin = plugin;
    }

    public List<String> getBlockedActions(){
        List<String> blockedActions = plugin.getConfig().getStringList("blocked_actions");

        return blockedActions;
    }

    public boolean isBlockedAction(final String action){
        List<String> blockedActions = plugin.getConfig().getStringList("blocked_actions");

        return blockedActions.contains(action);
    }

    public boolean isWhitelistedPlayer(final String player){

        List<String> whitelistedPlayers = plugin.getConfig().getStringList("whitelisted_players");

        return whitelistedPlayers.contains(player);
    }
}
