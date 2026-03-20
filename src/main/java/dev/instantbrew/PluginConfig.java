package dev.instantbrew;

import org.bukkit.configuration.file.FileConfiguration;

public class PluginConfig {

    private final InstantBrewPlugin plugin;
    private int brewDelayTicks;
    private boolean blazePowderInfinite;
    private boolean blazePowderShiftClickToIngredient;
    private boolean fuelMaxOnPlaceOpen;

    public PluginConfig(InstantBrewPlugin plugin) {
        this.plugin = plugin;
        load();
    }

    public void reload() {
        load();
    }

    private void load() {
        FileConfiguration cfg = plugin.getConfig();
        brewDelayTicks = Math.max(0, cfg.getInt("brew-delay-ticks", 0));
        blazePowderInfinite = cfg.getBoolean("blaze-powder-infinite", false);
        blazePowderShiftClickToIngredient = cfg.getBoolean("blaze-powder-shift-click-to-ingredient",
                cfg.getBoolean("blaze-powder-shift-click-to-fuel", false));
        fuelMaxOnPlaceOpen = cfg.getBoolean("fuel-max-on-place-open", true);
    }

    public int getBrewDelayTicks() {
        return brewDelayTicks;
    }

    public boolean isBlazePowderInfinite() {
        return blazePowderInfinite;
    }

    public boolean isBlazePowderShiftClickToIngredient() {
        return blazePowderShiftClickToIngredient;
    }

    public boolean isFuelMaxOnPlaceOpen() {
        return fuelMaxOnPlaceOpen;
    }
}
