package me.andrestube.calculatorchat;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.andrestube.calculatorchat.listeners.ChatListener;

public class CalculatorChat extends JavaPlugin {
    public void onEnable() {
        saveDefaultConfig(); // create config
        getLogger().info("CalculatorChat has been enabled");
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
    }

    // hashset for calculator toggle
    private final Set<UUID> disabledPlayers = new HashSet<>();

    public Set<UUID> getDisabledPlayers() {
        return disabledPlayers;
    }

    public void onDisable() {
        getLogger().info("CalculatorChat has been disabled");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("calculatorchat")) { // base command
            if (args.length == 0) {
                String help = this.getConfig().getString("help_header");
                String help_calculator = this.getConfig().getString("help_calculator");
                String help_reload = this.getConfig().getString("help_calculator_reload");

                // send message
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', help));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', help_calculator));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', help_reload));
                return true;
            }
            if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
                String reload_message = this.getConfig().getString("calculator_reloaded");
                // reload config
                this.reloadConfig();

                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', reload_message));
                return true;
            }
            if (args.length > 0 && args[0].equalsIgnoreCase("toggle")) {
                String message = this.getConfig().getString("calculator_enabled");
                String message_disabled = this.getConfig().getString("calculator_disabled");
                Player player = (Player) sender;
                UUID uuid = player.getUniqueId();
                // toggle system
                if (disabledPlayers.contains(uuid)) {
                    disabledPlayers.remove(uuid);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                } else {
                    disabledPlayers.add(uuid);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', message_disabled));
                }
                return true;
            }
        }
        return false;
    }
}