package dev.instantbrew;

import dev.instantbrew.command.ReloadCommand;
import dev.instantbrew.listener.BrewingFuelListener;
import dev.instantbrew.listener.BrewingInventoryListener;
import dev.instantbrew.listener.BrewingListener;
import java.io.File;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class InstantBrewPlugin extends JavaPlugin {

    private static final int BSTATS_PLUGIN_ID = 30302;

    private PluginConfig config;
    private MessagesConfig messages;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        if (!new File(getDataFolder(), "messages.yml").exists()) {
            saveResource("messages.yml", false);
        }
        config = new PluginConfig(this);
        messages = new MessagesConfig(this);
        try {
            Class<?> metricsClass;
            try {
                metricsClass = Class.forName("org.bstats.bukkit.Metrics");
            } catch (ClassNotFoundException ignored) {
                String relocatedBase = getClass().getPackage().getName(); // e.g. dev.instantbrew
                metricsClass = Class.forName(relocatedBase + ".bukkit.Metrics");
            }
            metricsClass.getConstructor(Plugin.class, int.class).newInstance(this, BSTATS_PLUGIN_ID);
            //getLogger().info("bStats metrics enabled");
        } catch (ReflectiveOperationException e) {
            getLogger().warning("bStats failed to load: " + e.getMessage());
        }
        var pm = getServer().getPluginManager();
        pm.registerEvents(new BrewingListener(config), this);
        pm.registerEvents(new BrewingFuelListener(config, this), this);
        pm.registerEvents(new BrewingInventoryListener(config, messages), this);
        ReloadCommand reloadCmd = new ReloadCommand(this, messages);
        getCommand("instantbrew").setExecutor(reloadCmd);
        getCommand("instantbrew").setTabCompleter(reloadCmd);
    }

    public void reloadPluginConfig() {
        reloadConfig();
        config.reload();
        messages.reload();
    }

    public PluginConfig getPluginConfig() {
        return config;
    }

    public MessagesConfig getMessages() {
        return messages;
    }
}
