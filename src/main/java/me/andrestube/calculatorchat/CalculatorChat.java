package me.andrestube.calculatorchat;

import me.andrestube.calculatorchat.command.MainCommand;
import me.andrestube.calculatorchat.config.Config;
import me.andrestube.calculatorchat.config.ConfigType;
import me.andrestube.calculatorchat.config.cache.ConfigCache;
import me.andrestube.calculatorchat.config.cache.MessagesCache;
import me.andrestube.calculatorchat.config.registry.ConfigRegistry;
import me.andrestube.calculatorchat.listener.PlayerListener;
import me.andrestube.calculatorchat.math.MathService;
import me.andrestube.calculatorchat.player.PlayerRegistry;
import me.andrestube.calculatorchat.player.PlayerService;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * CalculatorChat's main class
 *
 * @author AndresTube
 * @author CubicLemming749
 */
public class CalculatorChat extends JavaPlugin {
    private ConfigRegistry configRegistry;
    private ConfigCache configCache;
    private MessagesCache messagesCache;

    private PlayerRegistry playerRegistry;
    private PlayerService playerService;

    private MathService mathService;

    @Override
    public void onEnable(){
        getLogger().info("Turning on CalculatorChat");
        config();

        playerRegistry = new PlayerRegistry();
        playerService = new PlayerService(messagesCache, playerRegistry);

        mathService = new MathService();

        registerCommands();
        registerListeners();
    }

    public void config(){
        configRegistry = new ConfigRegistry();

        Config mainConfig = new Config(this, "config.yml");
        Config messagesConfig = new Config(this, "messages.yml");

        configRegistry.registerConfig(ConfigType.MAIN, mainConfig);
        configRegistry.registerConfig(ConfigType.MESSAGES, messagesConfig);

        configCache = new ConfigCache(mainConfig);
        messagesCache = new MessagesCache(messagesConfig);
    }

    public void registerCommands(){
        getLogger().info("Registering commands...");

        MainCommand mainCmd = new MainCommand(configRegistry, playerService, configCache, messagesCache);

        this.getCommand("calculatorchat").setExecutor(mainCmd);
        this.getCommand("calculatorchat").setTabCompleter(mainCmd);
    }

    public void registerListeners(){
        getLogger().info("Registering listeners...");
        this.getServer().getPluginManager().registerEvents(new PlayerListener(this, playerService, mathService, configCache, messagesCache), this);
    }
}