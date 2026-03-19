package dev.instantbrew.listener;

import dev.instantbrew.PluginConfig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BrewingStartEvent;

public class BrewingListener implements Listener {

    private final PluginConfig config;

    public BrewingListener(PluginConfig config) {
        this.config = config;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBrewingStart(BrewingStartEvent event) {
        int delay = config.getBrewDelayTicks();
        int effectiveDelay = delay == 0 ? 1 : delay;
        event.setBrewingTime(effectiveDelay);
        try {
            event.setRecipeBrewTime(effectiveDelay);
        } catch (NoSuchMethodError ignored) {
        }
    }
}
