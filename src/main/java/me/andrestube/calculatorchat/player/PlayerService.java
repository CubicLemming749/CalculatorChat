package me.andrestube.calculatorchat.player;

import me.andrestube.calculatorchat.config.cache.MessagesCache;
import me.andrestube.calculatorchat.util.MessageUtil;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerService {
    private final MessagesCache messagesCache;
    private final PlayerRegistry playerRegistry;

    public PlayerService(MessagesCache messagesCache, PlayerRegistry playerRegistry) {
        this.messagesCache = messagesCache;
        this.playerRegistry = playerRegistry;
    }

    public void togglePlayer(Player player){
        UUID uuid = player.getUniqueId();

        if(!playerRegistry.getState(uuid)){
            MessageUtil.sendMsg(player, messagesCache.getEnabled());
            addPlayer(player.getUniqueId());
            return;
        }

        MessageUtil.sendMsg(player, messagesCache.getDisabled());
        playerRegistry.removePlayer(uuid);
    }

    public void addPlayer(UUID uuid){
        playerRegistry.addPlayer(uuid);
    }

    public boolean getState(Player player){
        return playerRegistry.getState(player.getUniqueId());
    }
}
