package dev.instantbrew.listener;

import dev.instantbrew.PluginConfig;
import dev.instantbrew.MessagesConfig;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BrewingInventoryListener implements Listener {

    private static final int INGREDIENT_SLOT = 3;
    private static final int FUEL_SLOT = 4;

    private final PluginConfig config;
    private final MessagesConfig messages;

    public BrewingInventoryListener(PluginConfig config, MessagesConfig messages) {
        this.config = config;
        this.messages = messages;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getView().getTopInventory() instanceof BrewerInventory brewer)) return;
        Inventory clickedInv = event.getClickedInventory();
        if (clickedInv == null) return;

        // When infinite fuel is on: block putting blaze powder in fuel slot
        if (config.isBlazePowderInfinite()) {
            ItemStack cursor = event.getCursor();
            if (clickedInv.equals(event.getView().getTopInventory()) && event.getSlot() == FUEL_SLOT) {
                if (cursor != null && cursor.getType() == Material.BLAZE_POWDER) {
                    event.setCancelled(true);
                    event.getWhoClicked().sendMessage(messages.get("instantbrew.no-fuel-infinite"));
                    return;
                }
                // Shift-click from fuel slot is handled below
            }
            if (clickedInv.equals(event.getView().getBottomInventory()) && event.isShiftClick()) {
                ItemStack current = event.getCurrentItem();
                if (current != null && current.getType() == Material.BLAZE_POWDER) {
                    // Redirect to ingredient, never let it go to fuel
                    event.setCancelled(true);
                    mergeIntoIngredient(brewer, current);
                    event.setCurrentItem(current.getAmount() > 0 ? current : null);
                }
                return;
            }
        }

        if (!event.isShiftClick()) return;
        ItemStack current = event.getCurrentItem();
        if (current == null || current.getType() != Material.BLAZE_POWDER) return;

        if (clickedInv.equals(event.getView().getBottomInventory())) {
            if (config.isBlazePowderShiftClickToIngredient() && !config.isBlazePowderInfinite()) {
                event.setCancelled(true);
                mergeIntoIngredient(brewer, current);
                event.setCurrentItem(current.getAmount() > 0 ? current : null);
            }
        } else if (clickedInv.equals(event.getView().getTopInventory())
                && (config.isBlazePowderShiftClickToIngredient() || config.isBlazePowderInfinite())) {
            int slot = event.getSlot();
            if (slot == INGREDIENT_SLOT || slot == FUEL_SLOT) {
                event.setCancelled(true);
                ItemStack toMove = current.clone();
                if (slot == INGREDIENT_SLOT) {
                    brewer.setIngredient(null);
                } else {
                    brewer.setFuel(null);
                }
                Inventory playerInv = event.getView().getBottomInventory();
                for (ItemStack leftover : playerInv.addItem(toMove).values()) {
                    if (slot == INGREDIENT_SLOT) {
                        brewer.setIngredient(leftover);
                    } else {
                        brewer.setFuel(leftover);
                    }
                }
                event.setCurrentItem(null);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onInventoryDrag(InventoryDragEvent event) {
        if (!config.isBlazePowderInfinite()) return;
        if (!(event.getView().getTopInventory() instanceof BrewerInventory)) return;
        if (!event.getRawSlots().contains(FUEL_SLOT)) return;
        ItemStack dragged = event.getOldCursor();
        if (dragged != null && dragged.getType() == Material.BLAZE_POWDER) {
            event.setCancelled(true);
            event.getWhoClicked().sendMessage(messages.get("instantbrew.no-fuel-infinite"));
        }
    }

    private void mergeIntoIngredient(BrewerInventory brewer, ItemStack toAdd) {
        ItemStack ingredient = brewer.getIngredient();
        int toMove = toAdd.getAmount();
        if (ingredient == null || ingredient.getType().isAir()) {
            int move = Math.min(toMove, 64);
            brewer.setIngredient(toAdd.clone());
            brewer.getIngredient().setAmount(move);
            toAdd.setAmount(toAdd.getAmount() - move);
            if (toAdd.getAmount() <= 0) toAdd.setType(Material.AIR);
        } else if (ingredient.isSimilar(toAdd)) {
            int move = Math.min(toMove, 64 - ingredient.getAmount());
            if (move > 0) {
                ingredient.setAmount(ingredient.getAmount() + move);
                toAdd.setAmount(toAdd.getAmount() - move);
            }
            if (toAdd.getAmount() <= 0) toAdd.setType(Material.AIR);
        }
    }
}
