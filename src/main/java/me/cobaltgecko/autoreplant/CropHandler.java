package me.cobaltgecko.autoreplant;

import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class CropHandler {

    private static final Map<Material, Material> cropToSeedMap = new HashMap<>();
    private static final Map<Material, Material> seedToCropMap = new HashMap<>();
    private static final Map<Material, String> cropToPermissionMap = new HashMap<>();

    static {
        cropToSeedMap.put(Material.WHEAT, Material.WHEAT_SEEDS);
        cropToSeedMap.put(Material.POTATOES, Material.POTATO);
        cropToSeedMap.put(Material.CARROTS, Material.CARROT);
        cropToSeedMap.put(Material.BEETROOTS, Material.BEETROOT_SEEDS);

        seedToCropMap.put(Material.WHEAT_SEEDS, Material.WHEAT);
        seedToCropMap.put(Material.POTATO, Material.POTATOES);
        seedToCropMap.put(Material.CARROT, Material.CARROTS);
        seedToCropMap.put(Material.BEETROOT_SEEDS, Material.BEETROOTS);

        cropToPermissionMap.put(Material.WHEAT, "AutoReplant.replant.wheat");
        cropToPermissionMap.put(Material.POTATOES, "AutoReplant.replant.potato");
        cropToPermissionMap.put(Material.CARROTS, "AutoReplant.replant.carrot");
        cropToPermissionMap.put(Material.BEETROOTS, "AutoReplant.replant.beetroot");
    }

    public static Material getSeedFromCrop(Material crop) {
        return cropToSeedMap.getOrDefault(crop, null);
    }

    public static Material getCropFromSeed(Material seed) { return seedToCropMap.getOrDefault(seed, null); }

    public static String getPermissionStringForCrop(Material crop) {return cropToPermissionMap.getOrDefault(crop, null); }
}
