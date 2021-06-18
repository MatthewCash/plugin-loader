package com.matthewcash.network;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.UnknownDependencyException;
import org.bukkit.plugin.java.JavaPlugin;

public class Loader {

    private final PluginManager pluginManager;

    Loader(JavaPlugin plugin) {
        this.pluginManager = plugin.getServer().getPluginManager();
    }

    public void loadPlugins() {
        List<File> pluginFiles = getPluginFiles();

        if (pluginFiles.isEmpty()) {
            PluginLogger.getLogger("PluginLoader").warning("Found no plugins to be loaded!");

        }

        for (File pluginFile : pluginFiles) {
            Plugin plugin = null;

            try {
                plugin = this.pluginManager.loadPlugin(pluginFile);
            } catch (UnknownDependencyException | InvalidPluginException | InvalidDescriptionException e) {
                PluginLogger.getLogger("PluginLoader").severe("Failed to load plugin " + pluginFile.getName());
                e.printStackTrace();
            }

            if (plugin == null) {
                continue;
            }

            pluginManager.enablePlugin(plugin);
        }
    }

    public List<File> getPluginFiles() {
        PluginListFile pluginListFile = new PluginListFile();

        List<String> pluginList = null;
        try {
            pluginList = pluginListFile.readPluginList();
        } catch (IOException | InvalidConfigurationException e) {
            PluginLogger.getLogger("PluginLoader").severe("Failed to load plugins.yml!");
            e.printStackTrace();
        }

        if (pluginList == null) {
            return new ArrayList<File>();
        }

        List<File> pluginFiles = pluginList.stream().map(filePath -> new File(filePath)).collect(Collectors.toList());

        return pluginFiles;
    }

}
