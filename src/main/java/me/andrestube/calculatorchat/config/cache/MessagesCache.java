package me.andrestube.calculatorchat.config.cache;

import me.andrestube.calculatorchat.config.Config;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class MessagesCache implements Cache {
    private final Config config;

    private String noPermission;
    private String reloaded;
    private String resultMessage;
    private String enabled;
    private String disabled;
    private String mathError;
    private List<String> helpMessage;

    public MessagesCache(Config config) {
        this.config = config;
        load();
    }

    @Override
    public void load() {
        FileConfiguration fileConfiguration = config.getFileConfiguration();

        this.noPermission = fileConfiguration.getString("no_permission");
        this.reloaded = fileConfiguration.getString("reloaded");
        this.resultMessage = fileConfiguration.getString("result_message");
        this.enabled = fileConfiguration.getString("calculator_enabled");
        this.disabled = fileConfiguration.getString("calculator_disabled");
        this.mathError = fileConfiguration.getString("error_math");
        this.helpMessage = fileConfiguration.getStringList("help_message");
    }

    public String getNoPermission() {
        return noPermission;
    }

    public String getReloaded() {
        return reloaded;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public String getEnabled() {
        return enabled;
    }

    public String getDisabled() {
        return disabled;
    }

    public String getMathError() {
        return mathError;
    }

    public List<String> getHelpMessage() {
        return helpMessage;
    }
}
