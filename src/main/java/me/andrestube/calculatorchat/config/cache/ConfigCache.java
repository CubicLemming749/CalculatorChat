package me.andrestube.calculatorchat.config.cache;

import me.andrestube.calculatorchat.config.Config;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigCache implements Cache {
    private final Config config;

    private boolean enabledByDefault;
    private String format;
    private String soundSuccess;
    private String soundFail;

    public ConfigCache(Config config) {
        this.config = config;
        load();
    }

    @Override
    public void load() {
        FileConfiguration fileConfiguration = config.getFileConfiguration();

        this.enabledByDefault = fileConfiguration.getBoolean("enabled_by_default");
        this.format = fileConfiguration.getString("expression", "%expression%=");
        this.soundSuccess = fileConfiguration.getString("sound_success", "BLOCK_NOTE_BLOCK_PLING;1;1");
        this.soundFail = fileConfiguration.getString("sound_fail", "BLOCK_NOTE_BLOCK_BASS;1;1");
    }

    public boolean isEnabledByDefault() {
        return enabledByDefault;
    }

    public String getFormat() {
        return format;
    }

    public String getSoundSuccess() {
        return soundSuccess;
    }

    public String getSoundFail() {
        return soundFail;
    }
}
