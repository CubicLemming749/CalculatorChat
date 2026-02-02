package me.andrestube.calculatorchat.util;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundUtil {
    public static void sendSound(Player player, String sound){
        String[] params = sound.split(";");

        Sound bukkitSound = null;

        try {
            bukkitSound = Sound.valueOf(params[0]);
        } catch (IllegalArgumentException e){
            //Default sound
            bukkitSound = Sound.AMBIENT_CAVE;
        }

        float volume;

        try {
            volume = Float.parseFloat(params[1]);
        } catch (NumberFormatException e) {
            volume = 1;
        }

        float pitch;

        try {
            pitch = Float.parseFloat(params[2]);
        } catch (NumberFormatException e) {
            pitch = 1;
        }

        player.playSound(player.getLocation(), bukkitSound, volume, pitch);
    }
}
