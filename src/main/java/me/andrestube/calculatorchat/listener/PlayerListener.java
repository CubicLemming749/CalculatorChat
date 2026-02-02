package me.andrestube.calculatorchat.listener;

import me.andrestube.calculatorchat.CalculatorChat;
import me.andrestube.calculatorchat.config.cache.ConfigCache;
import me.andrestube.calculatorchat.config.cache.MessagesCache;
import me.andrestube.calculatorchat.math.MathService;
import me.andrestube.calculatorchat.player.PlayerService;
import me.andrestube.calculatorchat.util.MessageUtil;
import me.andrestube.calculatorchat.util.SoundUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Player Listener, it handles all the events that are relational with players.
 * @author CubicLemming749
 */
public class PlayerListener implements Listener {
    private final CalculatorChat plugin;

    private final PlayerService playerService;
    private final MathService mathService;

    private final ConfigCache configCache;
    private final MessagesCache messagesCache;

    public PlayerListener(CalculatorChat plugin, PlayerService playerService, MathService mathService, ConfigCache configCache, MessagesCache messagesCache) {
        this.plugin = plugin;
        this.playerService = playerService;
        this.mathService = mathService;
        this.configCache = configCache;
        this.messagesCache = messagesCache;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if(!playerService.getState(player)) return;

        String message = event.getMessage().trim();

        String format = configCache.getFormat();

        String regex = format
                .replace("%expression%", "(.+?)");

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(message);

        if (!matcher.matches()) return;
        event.setCancelled(true);
        String finalExpression = matcher.group(1)
                .replace(" ", "")
                .replace("x", "*")
                .replace("X", "*");

        mathService.calculateExpression(finalExpression)
                .thenAccept(optional -> {
                    if(optional.isEmpty()){

                        //We return to the main thread to interact with bukkit api.
                        Bukkit.getScheduler().runTask(plugin, () -> {
                            SoundUtil.sendSound(player, configCache.getSoundFail());
                            MessageUtil.sendMsg(player, messagesCache.getMathError());
                        });

                        return;
                    }

                    //The same here.
                    Bukkit.getScheduler().runTask(plugin, () -> {
                        SoundUtil.sendSound(player, configCache.getSoundSuccess());

                        DecimalFormat decimalFormat = new DecimalFormat("#.##");
                        String formattedResult = decimalFormat.format(optional.get());

                        MessageUtil.sendMsg(player, messagesCache.getResultMessage()
                                .replace("%result%", formattedResult));
                    });
                });
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();

        if(configCache.isEnabledByDefault()){
            playerService.addPlayer(player.getUniqueId());
        }
    }
}
