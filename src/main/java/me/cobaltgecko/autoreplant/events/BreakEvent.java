package me.cobaltgecko.autoreplant.events;

import me.cobaltgecko.autoreplant.AutoReplant;
import me.cobaltgecko.autoreplant.util.CropHandler;
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
import java.util.Arrays;

public class BreakEvent implements Listener {

    private final Material[] cropList = {Material.WHEAT, Material.POTATOES, Material.CARROTS, Material.BEETROOTS};

    @EventHandler
    public void breakEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("AutoReplant.replant")) {
            return;
        }

        Block block = event.getBlock();
        PlayerInventory inventory = player.getInventory();
        Material cropBlockType;

        // If the broken block is a crop then set cropBlockType to be the material type of the broken block
        if (Arrays.asList(cropList).contains(block.getType())) {
            cropBlockType = block.getType();
        } else {
            return;
        }
            // Main functionality of the plugin
            if (isFullyGrown(block)) {
                Material seedType = CropHandler.getSeedFromCrop(cropBlockType);
                if (inventory.contains(seedType)) {
                    removeSeed(inventory, seedType);
                    replantCrop(block.getLocation(), cropBlockType);
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

        // Loop to find the location of seeds in the player's inventory
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
