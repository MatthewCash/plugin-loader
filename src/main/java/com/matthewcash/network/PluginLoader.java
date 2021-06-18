package com.matthewcash.network;

import org.bukkit.plugin.java.JavaPlugin;

public class PluginLoader extends JavaPlugin {
    private static PluginLoader plugin;

    public static PluginLoader getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;

        getLogger().info("Enabled PluginLoader!");

        Loader loader = new Loader(this);
        loader.loadPlugins();

        getLogger().info("Finished loading PluginLoader plugins!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabled PluginLoader!");
    }
}