package me.andrestube.calculatorchat.config;

import me.andrestube.calculatorchat.CalculatorChat;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.Yaml;

import java.io.File;

public class Config {
    private final CalculatorChat plugin;
    private final String name;

    private final File file;
    private FileConfiguration fileConfiguration;

    public Config(CalculatorChat plugin, String name) {
        this.plugin = plugin;
        this.name = name;

        this.file = new File(plugin.getDataFolder(), name);

        loadConfig();
    }

    public void loadConfig(){
        plugin.getLogger().info("Loading config file: "+name);

        if(!file.exists()){
            plugin.saveResource(name, true);
        }

        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getFileConfiguration(){
        return fileConfiguration;
    }
}
