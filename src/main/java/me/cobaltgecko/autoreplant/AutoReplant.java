package me.cobaltgecko.autoreplant;

import me.cobaltgecko.autoreplant.events.BreakEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.Callable;

public final class AutoReplant extends JavaPlugin {

    public static Plugin instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        getServer().getPluginManager().registerEvents(new BreakEvent(), instance);

        int pluginId = 12393; // <-- Replace with the id of your plugin!
        Metrics metrics = new Metrics(this, pluginId);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Plugin getInstance() {
        return instance;
    }

}
