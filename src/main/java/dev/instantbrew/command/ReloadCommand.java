package dev.instantbrew.command;

import dev.instantbrew.InstantBrewPlugin;
import dev.instantbrew.MessagesConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class ReloadCommand implements CommandExecutor, TabCompleter {

    private final InstantBrewPlugin plugin;
    private final MessagesConfig messages;

    public ReloadCommand(InstantBrewPlugin plugin, MessagesConfig messages) {
        this.plugin = plugin;
        this.messages = messages;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("instantbrew.reload")) {
                sender.sendMessage(messages.get("instantbrew.no-permission"));
                return true;
            }
            plugin.reloadPluginConfig();
            int delay = plugin.getPluginConfig().getBrewDelayTicks();
            sender.sendMessage(messages.format("instantbrew.reload-success", "{delay}", String.valueOf(delay)));
            return true;
        }
        sender.sendMessage(messages.get("instantbrew.reload-usage"));
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender,
                                                @NotNull Command command,
                                                @NotNull String alias,
                                                @NotNull String[] args) {
        if (args.length == 1) return Collections.singletonList("reload");
        return Collections.emptyList();
    }
}
