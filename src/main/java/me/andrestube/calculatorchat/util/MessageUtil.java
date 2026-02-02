package me.andrestube.calculatorchat.util;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class MessageUtil {
    public static void sendMsg(CommandSender commandSender, String msg){
        commandSender.sendMessage(coloredMessage(msg));
    }

    public static void sendMsg(CommandSender commandSender, List<String> msg){
        msg.forEach(l -> commandSender.sendMessage(coloredMessage(l)));
    }

    public static String coloredMessage(String msg){
        if(msg == null) return "";
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
