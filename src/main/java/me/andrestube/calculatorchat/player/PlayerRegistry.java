package me.andrestube.calculatorchat.player;

import java.util.*;

/**
 * Simple registry to store the players that have toggled the CalculatorChat function.
 * @author CubicLemming749
 */
public class PlayerRegistry {
    private final Set<UUID> players;

    public PlayerRegistry(){
        this.players = new HashSet<>();
    }

    public void addPlayer(UUID uuid){
        players.add(uuid);
    }

    public void removePlayer(UUID uuid){
        players.remove(uuid);
    }

    public boolean getState(UUID uuid){
        return players.contains(uuid);
    }
}
