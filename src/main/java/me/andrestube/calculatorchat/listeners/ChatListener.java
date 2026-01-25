package me.andrestube.calculatorchat.listeners;

import me.andrestube.calculatorchat.CalculatorChat;
import me.andrestube.calculatorchat.MathHandler;
import java.text.DecimalFormat;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private final CalculatorChat plugin;

    public ChatListener(CalculatorChat plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (plugin.getDisabledPlayers().contains(player.getUniqueId())) {
            return;
        }

        String message = event.getMessage().trim();

        // check for endsWith =
        if (message.endsWith("=")) {
            String expression = message.substring(0, message.length() - 1)
                    .replace(" ", "")
                    .replace("x", "*");

            try {
                double result = MathHandler.calculate(expression);
                event.setCancelled(true);

                DecimalFormat df = new DecimalFormat("#.##");
                String result_DecimalFormat = df.format(result);

                String result_message = this.plugin.getMsgConfig().getResultMessage();
                result_message = result_message.replace("%result%", result_DecimalFormat);
                // and finally send message
                player.sendMessage(result_message);

                // and a lil sound
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
            } catch (Exception e) {
                // bip bip error system
                String error_message = this.plugin.getMsgConfig().getErrorMath();
                player.sendMessage(error_message);
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_HURT, 1.0f, 1.0f);
            }
        }
    }
}
