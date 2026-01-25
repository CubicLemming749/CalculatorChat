package me.andrestube.calculatorchat;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.andrestube.calculatorchat.listeners.ChatListener;

public class CalculatorChat extends JavaPlugin {

    private MessageConfig msgConfig;

    public MessageConfig getMsgConfig() {
        return this.msgConfig;
    }

    public void onEnable() {
        saveDefaultConfig(); // create config
        this.msgConfig = new MessageConfig(getConfig()); // load messageconfigs
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

                String help = this.getMsgConfig().getHelpHeader();
                String help_calculator = this.getMsgConfig().getHelpCalculator();
                String help_reload = this.getMsgConfig().getHelpCalculatorReload();

                // send message
                sender.sendMessage(help);
                sender.sendMessage(help_calculator);
                sender.sendMessage(help_reload);
                return true;
            }
            if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
                String reload_message = this.getMsgConfig().getCalculatorReloaded();
                // reload config
                this.reloadConfig();

                sender.sendMessage(reload_message);
                return true;
            }
            if (args.length > 0 && args[0].equalsIgnoreCase("toggle")) {
                String message = this.getMsgConfig().getCalculatorEnabled();
                String message_disabled = this.getMsgConfig().getCalculatorDisabled();
                Player player = (Player) sender;
                UUID uuid = player.getUniqueId();
                // toggle system
                if (disabledPlayers.contains(uuid)) {
                    disabledPlayers.remove(uuid);
                    player.sendMessage(message);
                } else {
                    disabledPlayers.add(uuid);
                    player.sendMessage(message_disabled);
                }
                return true;
            }
        }
        return false;
    }
}