package me.cobaltgecko.autoreplant.util;

import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class CropHandler {

    /** Mappings of all crops to their respective seed **/
    private static final Map<Material, Material> cropToSeedMap = new HashMap<>();

    static {
        initMap();
    }

    private static void initMap() {
        cropToSeedMap.put(Material.WHEAT, Material.WHEAT_SEEDS);
        cropToSeedMap.put(Material.POTATOES, Material.POTATO);
        cropToSeedMap.put(Material.CARROTS, Material.CARROT);
        cropToSeedMap.put(Material.BEETROOTS, Material.BEETROOT_SEEDS);
    }

    public static Material getSeedFromCrop(Material crop) {
        return cropToSeedMap.getOrDefault(crop, null);
    }

    public static Material getCropFromSeed(Material seed) {
        for (Material crop : cropToSeedMap.keySet()) {
            if (cropToSeedMap.get(crop).equals(seed)) {
                return crop;
            }
        }
        return null;
    }


}
