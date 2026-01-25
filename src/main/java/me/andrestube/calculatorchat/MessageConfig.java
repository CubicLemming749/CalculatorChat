package me.andrestube.calculatorchat;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.ChatColor;
import lombok.Getter;

@Getter
public class MessageConfig {

    // get strings with lombok (ty iChocoMilk)
    private final String resultMessage;
    private final String errorMath;
    private final String errorUnknown;
    private final String errorTimeout;
    private final String calculatorEnabled;
    private final String calculatorDisabled;
    private final String calculatorReloaded;
    private final String helpHeader;
    private final String helpCalculator;
    private final String helpCalculatorReload;

    public MessageConfig(FileConfiguration config) {
        // get strings from config.yml
        this.resultMessage = color(config.getString("result_message"));
        this.errorMath = color(config.getString("error"));
        this.errorUnknown = color(config.getString("error_math"));
        this.errorTimeout = color(config.getString("error_timeout"));
        this.calculatorEnabled = color(config.getString("calculator_enabled"));
        this.calculatorDisabled = color(config.getString("calculator_disabled"));
        this.calculatorReloaded = color(config.getString("calculator_reloaded"));
        this.helpHeader = color(config.getString("help_header"));
        this.helpCalculator = color(config.getString("help_calculator"));
        this.helpCalculatorReload = color(config.getString("help_calculator_reload"));
    }

    private String color(String text) {
        // colorize strings
        return text == null ? "" : ChatColor.translateAlternateColorCodes('&', text);
    }
}