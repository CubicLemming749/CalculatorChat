package me.andrestube.calculatorchat.listeners;

import java.text.DecimalFormat;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private final me.andrestube.calculatorchat.CalculatorChat plugin;

    public ChatListener(me.andrestube.calculatorchat.CalculatorChat plugin) {
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
                // using exp4j
                net.objecthunter.exp4j.Expression e = new net.objecthunter.exp4j.ExpressionBuilder(expression).build();
                double result = e.evaluate();

                event.setCancelled(true);

                DecimalFormat df = new DecimalFormat("#.##");
                String result_DecimalFormat = df.format(result);
                String result_message = plugin.getConfig().getString("result_message");
                result_message = ChatColor.translateAlternateColorCodes('&',
                        result_message.replace("%result%", result_DecimalFormat));

                // and finally send message
                player.sendMessage(result_message);
                // and a lil sound
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
            } catch (Exception e) {
                // bip bip error system
                String error_message = plugin.getConfig().getString("error_math");
                error_message = ChatColor.translateAlternateColorCodes('&', error_message);
                player.sendMessage(error_message);
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_HURT, 1.0f, 1.0f);
            }
        }
    }
}
