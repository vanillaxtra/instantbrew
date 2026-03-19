package dev.instantbrew;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class MessagesConfig {

    private final JavaPlugin plugin;
    private FileConfiguration messages;

    public MessagesConfig(JavaPlugin plugin) {
        this.plugin = plugin;
        load();
    }

    public void reload() {
        load();
    }

    private void load() {
        File file = new File(plugin.getDataFolder(), "messages.yml");
        this.messages = YamlConfiguration.loadConfiguration(file);
    }

    public String get(String path) {
        String raw = messages.getString(path);
        if (raw == null) return "";
        return ChatColor.translateAlternateColorCodes('&', raw);
    }

    public String format(String path, String placeholder, String value) {
        return get(path).replace(placeholder, value);
    }
}

