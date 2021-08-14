//package me.cobaltgecko.autoreplant;
//
//import org.bukkit.Material;
//
//public enum Crops {
//    WHEAT(Material.WHEAT, Material.WHEAT_SEEDS),
//    POTATO(Material.POTATOES, Material.POTATO),
//    CARROT(Material.CARROTS, Material.CARROT),
//    BEETROOT(Material.BEETROOTS, Material.BEETROOT_SEEDS);
//
//    private final Material cropBlock, seedItem;
//
//    Crops(Material cropBlock, Material seedItem) {
//        this.cropBlock = cropBlock;
//        this.seedItem = seedItem;
//    }
//
//    public Material getCropMaterial() {
//        return cropBlock;
//    }
//
//    public Material getSeedMaterial() {
//        return seedItem;
//    }
//
//    public String getPermission() {
//        return "AutoReplace.replant." + name().toLowerCase();
//    }
//}
