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
            switch(block.getType()){
                case Material.WHEAT: cropBlockType = Material.WHEAT;
                case Material.POTATOES: cropBlockType = Material.POTATOES;
                case Material.CARROTS: cropBlockType = Material.CARROTS;
                case Material.BEETROOTS: cropBlockType = Material.BEETROOTS;
                default: return;
            }

            // Main functionality of the plugin
            if (isFullyGrown(block)) {

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
        int seedIndexLocation = null;
        ItemStack currentItems;

        // For loop to find the location of seeds in the player's inventory
        for (int slotIndex = 0; slotIndex < inventory.getSize(); slotIndex++) {
            
            currentItems = inventory.getItem(slotIndex);

            if (currentItems != null && currentItems.getType() == seedType) {

                seedIndexLocation = slotIndex;
                break;
            }
        }

        // Remove a seed from the player's inventory
        if (seedIndexLocation != null)
        {
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
        
        switch(cropBlockType){
            case Material.WHEAT: return Material.WHEAT_SEEDS;
            case Material.POTATOES: return Material.POTATO;
            case Material.CARROTS: return Material.CARROT;
            case Material.BEETROOTS: return Material.BEETROOT_SEEDS;
            default: return Material.WHEAT_SEEDS;
        }
    }

    /**
     *
     * @param inventory Inventory of the player who broke the block
     * @param cropBlockType Material type of the block that was broken
     * @return true if the player has the appropriate seed in their inventory
     */
    public boolean isSeedInInventory(PlayerInventory inventory, Material cropBlockType) {

        return inventory.contains(getSeedMaterial(cropBlockType ?? false))
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
