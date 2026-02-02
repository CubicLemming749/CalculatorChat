package me.andrestube.calculatorchat.command;

import me.andrestube.calculatorchat.config.cache.ConfigCache;
import me.andrestube.calculatorchat.config.cache.MessagesCache;
import me.andrestube.calculatorchat.config.registry.ConfigRegistry;
import me.andrestube.calculatorchat.player.PlayerService;
import me.andrestube.calculatorchat.util.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

public class MainCommand implements CommandExecutor, TabCompleter {
    private final ConfigRegistry configRegistry;
    private final PlayerService playerService;

    private final ConfigCache configCache;
    private final MessagesCache messagesCache;

    public MainCommand(ConfigRegistry configRegistry, PlayerService playerService, ConfigCache configCache, MessagesCache messagesCache) {
        this.configRegistry = configRegistry;
        this.playerService = playerService;
        this.configCache = configCache;
        this.messagesCache = messagesCache;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        String argument = String.join("", args);

        if(!commandSender.hasPermission("calculator.command")){
            MessageUtil.sendMsg(commandSender, messagesCache.getNoPermission());
            return true;
        }

        switch (argument){
            case "toggle" -> {
                if(!(commandSender instanceof Player player)){
                    MessageUtil.sendMsg(commandSender, messagesCache.getNoPermission());
                    return true;
                }

                if(!commandSender.hasPermission("calculator.command.toggle")){
                    MessageUtil.sendMsg(commandSender, messagesCache.getNoPermission());
                    return true;
                }

                playerService.togglePlayer(player);
            }
            case "reload" -> {
                if(!commandSender.hasPermission("calculator.command.reload")){
                    MessageUtil.sendMsg(commandSender, messagesCache.getNoPermission());
                    return true;
                }

                configRegistry.reloadAll();
                configCache.reload();
                messagesCache.reload();
                MessageUtil.sendMsg(commandSender, messagesCache.getReloaded());

                return true;
            }

            default -> MessageUtil.sendMsg(commandSender, messagesCache.getHelpMessage());
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> options = List.of("toggle", "reload");

        if (args.length == 1) {
            String typed = args[0].toLowerCase();
            return options.stream()
                    .filter(opt -> opt.startsWith(typed))
                    .toList();
        }

        return List.of();
    }
}
