package me.cobaltgecko.autoreplant;

import me.cobaltgecko.autoreplant.events.BreakEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class AutoReplant extends JavaPlugin {

    public static Plugin instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        getServer().getPluginManager().registerEvents(new BreakEvent(), instance);
    }

    @Override
    public void onDisable() {
        // Plugin disable logic
    }

    public static Plugin getInstance() {
        return instance;
    }

}
