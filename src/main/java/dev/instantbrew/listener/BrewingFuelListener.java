package dev.instantbrew.listener;

import dev.instantbrew.PluginConfig;
import org.bukkit.Material;
import org.bukkit.block.BrewingStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.BrewingStandFuelEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class BrewingFuelListener implements Listener {

    private final PluginConfig config;
    private final JavaPlugin plugin;

    public BrewingFuelListener(PluginConfig config, JavaPlugin plugin) {
        this.config = config;
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!config.isFuelMaxOnPlaceOpen()) return;
        if (event.getBlock().getState() instanceof BrewingStand stand) {
            stand.setFuelLevel(20);
            stand.update();
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (!config.isFuelMaxOnPlaceOpen()) return;
        if (event.getInventory().getHolder() instanceof BrewingStand stand) {
            stand.setFuelLevel(20);
            stand.update();
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onBrewingStandFuel(BrewingStandFuelEvent event) {
        if (!config.isBlazePowderInfinite()) return;
        ItemStack fuel = event.getFuel();
        if (fuel != null && fuel.getType() == Material.BLAZE_POWDER) {
            event.setConsuming(false);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBrew(BrewEvent event) {
        if (!config.isBlazePowderInfinite()) return;
        var block = event.getBlock();
        plugin.getServer().getScheduler().runTask(plugin, () -> {
            if (block.getState() instanceof BrewingStand stand) {
                stand.setFuelLevel(20);
                stand.update();
            }
        });
    }
}
