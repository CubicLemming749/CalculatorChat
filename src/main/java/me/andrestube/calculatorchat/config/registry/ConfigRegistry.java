package me.andrestube.calculatorchat.config.registry;

import me.andrestube.calculatorchat.config.Config;
import me.andrestube.calculatorchat.config.ConfigType;

import java.util.EnumMap;
import java.util.Map;

public class ConfigRegistry {
    private final Map<ConfigType, Config> configs;

    public ConfigRegistry(){
        this.configs = new EnumMap<>(ConfigType.class);
    }

    public void registerConfig(ConfigType type, Config config){
        configs.put(type, config);
    }

    public Config getConfig(ConfigType type){
        return configs.get(type);
    }

    public void reloadAll(){
        configs.values().forEach(Config::loadConfig);
    }
}
