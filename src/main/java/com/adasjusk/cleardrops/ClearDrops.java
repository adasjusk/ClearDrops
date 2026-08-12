package com.adasjusk.cleardrops;
import io.papermc.paper.threadedregions.scheduler.RegionScheduler;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClearDrops extends JavaPlugin {
    private int chunkRadius;
    private boolean folia;
    private Set<Material> excludedItems = new HashSet<>();
    @Override
    public void onEnable() {
        saveDefaultConfig();
        chunkRadius = getConfig().getInt("chunk-radius", 2);
        loadExcludedItems();
        folia = detectFolia();

        CleanCommand cleanCommand = new CleanCommand(this);
        var clean = getCommand("clean");
        if (clean == null) {
            getLogger().severe("⚠ Could not register the /clean command. Check plugin.yml.");
            return;
        }
        clean.setExecutor(cleanCommand);
        var clearlag = getCommand("clearlag");
        if (clearlag == null) {
            getLogger().severe("⚠ Could not register the /clearlag command. Check plugin.yml.");
            return;
        }
        clearlag.setExecutor(cleanCommand);
    }
    @Override
    public void onDisable() {
    }
    public int getChunkRadius() {
        return chunkRadius;
    }
    public boolean isFolia() {
        return folia;
    }
    public Set<Material> getExcludedItems() {
        return excludedItems;
    }
    private void loadExcludedItems() {
        excludedItems.clear();
        List<String> raw = getConfig().getStringList("excluded-items");
        for (String name : raw) {
            String upper = name.trim().toUpperCase();
            Material mat = Material.matchMaterial(upper);
            if (mat != null) {
                excludedItems.add(mat);
            } else {
                getLogger().warning("⚠ Unknown material in excluded-items: " + name);
            }
        }
        getLogger().info("✔ Loaded " + excludedItems.size() + " excluded item(s).");
    }
    public RegionScheduler getRegionScheduler() {
        try {
            return (RegionScheduler) getServer().getClass()
                    .getMethod("getRegionScheduler")
                    .invoke(getServer());
        } catch (Exception e) {
            return null;
        }
    }
    private boolean detectFolia() {
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}