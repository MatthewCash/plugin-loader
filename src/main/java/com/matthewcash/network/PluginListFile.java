package com.matthewcash.network;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class PluginListFile {

    private final File dataFolder;
    private final String fileName = "plugins.yml";

    private List<String> pluginList;

    public PluginListFile() {
        dataFolder = PluginLoader.getPlugin().getDataFolder();
    }

    public List<String> readPluginList() throws IOException, InvalidConfigurationException {

        File pluginListFile = new File(dataFolder, fileName);

        YamlConfiguration config = new YamlConfiguration();

        try {
            config.load(pluginListFile);
        } catch (FileNotFoundException e) {
            createPluginList();
            config.load(pluginListFile);
        }

        pluginList = config.getStringList("plugins");

        return pluginList;
    }

    public void createPluginList() {
        File pluginListFile = new File(dataFolder, fileName);

        if (pluginListFile.exists()) {
            return;
        }

        pluginListFile.getParentFile().mkdirs();

        PluginLoader.getPlugin().saveResource(fileName, false);
    }

}
