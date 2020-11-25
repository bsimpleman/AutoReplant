package me.cobaltgecko.autoreplant.events;

import me.cobaltgecko.autoreplant.AutoReplant;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class BreakEvent implements Listener {

    @EventHandler
    public void breakEvent(BlockBreakEvent e) {
        Block block = e.getBlock();
        Player player = e.getPlayer();
        PlayerInventory inventory = player.getInventory();
        Material cropBlockType = null;

        // Check if the player has permission to replant
        if (player.hasPermission("AutoReplant.replant")) {
            // Get the type of the broken block
            if (block.getType() == Material.WHEAT) {
                cropBlockType = Material.WHEAT;
            } else if (block.getType() == Material.POTATOES) {
                cropBlockType = Material.POTATOES;
            } else if (block.getType() == Material.CARROTS) {
                cropBlockType = Material.CARROTS;
            } else if (block.getType() == Material.BEETROOTS) {
                cropBlockType = Material.BEETROOTS;
            }

            // Main functionality of the plugin
            if (cropBlockType != null && isFullyGrown(block)) {
                Material seedType = getSeedMaterial(cropBlockType);
                if (isSeedInInventory(inventory, cropBlockType)) {
                    removeSeed(inventory, seedType);
                    replantCrop(block.getLocation(), cropBlockType);
                }
            }
        }
    }

    /**
     * Removes a specified amount of seeds of the appropriate seed type for every replant
     *
     * @param inventory Inventory of the player
     * @param seedType Type of seed the player should have removed
     */
    public void removeSeed(PlayerInventory inventory, Material seedType) {
        int seedIndexLocation = -1;
        ItemStack currentItems;

        // For loop to find the location of seeds in the player's inventory
        for (int slotIndex = 0; slotIndex < inventory.getSize(); slotIndex++) {
            currentItems = inventory.getItem(slotIndex);
            if (currentItems != null) {
                if (currentItems.getType() == seedType) {
                    seedIndexLocation = slotIndex;

                    // Breaks the for loop
                    slotIndex = inventory.getSize();
                }
            }
        }

        // Remove a seed from the player's inventory
        if (seedIndexLocation != -1) {
            ItemStack seedItemStack = inventory.getItem(seedIndexLocation);
            if (seedItemStack != null) {
                int seedAmount = seedItemStack.getAmount();
                seedItemStack.setAmount(seedAmount - 1);
            }
        }

    }

    /**
     * Checks what type of block was broken to return the appropriate seed
     *
     * @param cropBlockType Block type of the crop being replanted
     * @return Material type of the seed
     */
    public Material getSeedMaterial(Material cropBlockType) {
        if (cropBlockType == Material.WHEAT) {
            return Material.WHEAT_SEEDS;
        } else if (cropBlockType == Material.POTATOES) {
            return Material.POTATO;
        } else if (cropBlockType == Material.CARROTS) {
            return Material.CARROT;
        } else if (cropBlockType == Material.BEETROOTS) {
            return Material.BEETROOT_SEEDS;
        }
        // Default condition, should not be reached
        return Material.WHEAT_SEEDS;
    }

    /**
     *
     * @param inventory Inventory of the player who broke the block
     * @param cropBlockType Material type of the block that was broken
     * @return true if the player has the appropriate seed in their inventory
     */
    public boolean isSeedInInventory(PlayerInventory inventory, Material cropBlockType) {
        if (cropBlockType == Material.WHEAT) {
            return inventory.contains(Material.WHEAT_SEEDS);
        } else if (cropBlockType == Material.POTATOES) {
            return inventory.contains(Material.POTATO);
        } else if (cropBlockType == Material.CARROTS) {
            return inventory.contains(Material.CARROT);
        }else if (cropBlockType == Material.BEETROOTS) {
            return inventory.contains(Material.BEETROOT_SEEDS);
        }
        return false;
    }

    /**
     * Checks if the crop was fully grown when broken
     *
     * @param block Block that was broken
     * @return true if the crop is fully grown
     */
    public boolean isFullyGrown(Block block) {
        // Check if it is fully grown
        Ageable ageable = (Ageable) block.getBlockData();
        int maximumAge = ageable.getMaximumAge();

        return ageable.getAge() == maximumAge;
    }

    /**
     * Replants the crop after a short interval
     *
     * @param location Location of the crop that was broken
     * @param cropBlockType Type of crop that was broken
     */
    public void replantCrop(Location location, Material cropBlockType) {
        Bukkit.getScheduler().runTaskLater(AutoReplant.getInstance(), () -> {
            location.getBlock().setType(cropBlockType);
        }, 20L);
    }
}
