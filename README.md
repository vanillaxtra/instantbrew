# InstantBrew

[![Version](https://img.shields.io/badge/version-1.0.0-blue.svg)](https://github.com/instantbrew/instantbrew)
[![Platform](https://img.shields.io/badge/platform-Paper%20%7C%20Spigot-green.svg)](https://papermc.io/)
[![Folia](https://img.shields.io/badge/Folia-supported-8B5CF6.svg)](https://papermc.io/software/folia)
[![bStats](https://img.shields.io/badge/bStats-metrics-00ADB5.svg)](https://bstats.org/plugin/bukkit/instantbrew/30302)

Lightweight control over brewing stands: how fast potions brew, optional infinite blaze-powder fuel, and smarter shift-clicking for blaze powder. Config hot-reloads—no server restart.

[![Discord](https://img.shields.io/badge/Discord-Join%20Server-5865F2?logo=discord&logoColor=white)](https://discord.gg/WpYZkrdNVe)

---

## Summary

InstantBrew runs on **Paper**, **Spigot**, and **Folia** (Minecraft 1.21+). Tune `brew-delay-ticks` from instant to any delay you want, optionally stop blaze powder from being consumed while keeping the fuel bar full, and choose whether shift-click places blaze powder in the ingredient slot. Use `/instantbrew reload` after editing `config.yml`.

---

## Showcase

### Instant brewing and infinite blaze powder

![Instant brewing and infinite fuel](assets/showcase.gif)

With the default near-instant delay, potions finish as soon as you add an ingredient. Enable `blaze-powder-infinite` so the fuel bar stays full and blaze powder is not consumed.

### Configurable brew delay

Set `brew-delay-ticks` for a slower brew (e.g. `20` = 1 second, `200` = 10 seconds). The brewing arrow animates over that time before potions complete.

---

## Features

- **Configurable brew delay** — Ticks until brew completes (`0` ≈ instant, `20` = 1 second)
- **Infinite blaze powder** — Optional: full fuel bar, no powder consumption
- **Fuel max on place/open** — Optional: placing or opening a brewing stand sets fuel to max
- **Shift-click to ingredient** — Optional: shift-click blaze powder into the ingredient slot instead of fuel
- **Hot reload** — `/instantbrew reload` applies config without restarting

---

## Installation

1. Put `InstantBrew-1.0.0.jar` in your server’s `plugins` folder (use the **shadow** JAR from [Building](#building)—it bundles bStats).
2. Start the server once so `plugins/InstantBrew/config.yml` is created.
3. Edit the config to taste, then run `/instantbrew reload`.

---

## Usage

### Commands

| Command | Permission | Description |
|---------|------------|-------------|
| `/instantbrew` | — | Shows usage |
| `/instantbrew reload` | `instantbrew.reload` | Reloads the config |

### Permissions

| Permission | Default | Description |
|------------|---------|-------------|
| `instantbrew.reload` | op | Reload config with `/instantbrew reload` |

---

## Configuration

All options are in `plugins/InstantBrew/config.yml`:

| Option | Description |
|--------|-------------|
| `brew-delay-ticks` | Time until brewing finishes. `20` ticks = 1 second. `0` = near-instant. |
| `blaze-powder-infinite` | If `true`, fuel does not drain and blaze powder is not consumed. |
| `blaze-powder-shift-click-to-ingredient` | If `true`, shift-clicking blaze powder moves it to the ingredient slot. |
| `fuel-max-on-place-open` | If `true`, placing or opening a brewing stand sets its fuel to max. |

**Example:**

```yaml
brew-delay-ticks: 0
blaze-powder-infinite: false
blaze-powder-shift-click-to-ingredient: false
fuel-max-on-place-open: true
```

---

## Compatibility

**Paper**, **Folia**, and **Spigot**, Minecraft **1.21+**. One JAR for all.

---

## Building

Requires **Java 21**.

```bash
./gradlew build
```

Artifact: `build/libs/InstantBrew-1.0.0.jar` (shadow JAR; includes relocated bStats).

---

## bStats

This plugin reports anonymous usage to **[bStats — InstantBrew](https://bstats.org/plugin/bukkit/instantbrew/30302)** (plugin ID **30302**). That helps see Minecraft version, server software, and adoption—no personal data.

**Integration check (already set in code):** `InstantBrewPlugin` constructs bStats `Metrics` with service ID `30302`, matching the dashboard URL above. The shadow JAR relocates `org.bstats` into `dev.instantbrew`, and the plugin resolves the relocated `Metrics` class at runtime.

**If your server does not appear on the graph yet:** bStats sends the first payload after a random **3–6 minutes**, and the site updates around **:00 and :30** each hour. Ensure you are using the **built shadow JAR** (not a thin JAR without dependencies). Server owners can opt out via the global bStats settings under the server’s `plugins` folder ([server owner info](https://bstats.org/docs/server-owners)).

### Live chart (servers using InstantBrew)

[![bStats: servers using InstantBrew](https://bstats.org/signatures/bukkit/instantbrew.svg)](https://bstats.org/plugin/bukkit/instantbrew/30302)

*Signature chart provided by [bStats](https://bstats.org/). If the image does not load, open the plugin page directly.*
