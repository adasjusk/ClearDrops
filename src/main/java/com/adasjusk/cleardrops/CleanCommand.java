package com.adasjusk.cleardrops;
import io.papermc.paper.threadedregions.scheduler.RegionScheduler;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import java.util.Set;

public class CleanCommand implements CommandExecutor {
    private final ClearDrops plugin;
    public CleanCommand(ClearDrops plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("☒ Only players can use this command.");
            return true;
        }
        org.bukkit.Location location = player.getLocation();
        if (location == null) {
            player.sendMessage("⚐ Could not determine your location.");
            return true;
        }
        org.bukkit.World world = location.getWorld();
        if (world == null) {
            player.sendMessage("☒ Your world is unavailable right now.");
            return true;
        }
        int chunkRadius = plugin.getChunkRadius();
        int removed = 0;
        if (plugin.isFolia()) {
            removed = cleanFolia(player, world, location, chunkRadius);
        } else {
            removed = cleanSync(player, world, location, chunkRadius);
        }
        if (plugin.isFolia()) {
            player.sendMessage("⚠ Cleanup queued within " + chunkRadius + " chunk(s).);");
            return true;
        }
        player.sendMessage("✔ Removed " + removed + " dropped item(s) within " + chunkRadius + " chunk(s).");
        return true;
    }

    private int cleanSync(Player player, org.bukkit.World world, org.bukkit.Location location, int chunkRadius) {
        int removed = 0;
        Set<Material> excluded = plugin.getExcludedItems();
        org.bukkit.Chunk playerChunk = location.getChunk();
        int playerChunkX = playerChunk.getX();
        int playerChunkZ = playerChunk.getZ();
        for (int dx = -chunkRadius; dx <= chunkRadius; dx++) {
            for (int dz = -chunkRadius; dz <= chunkRadius; dz++) {
                int chunkX = playerChunkX + dx;
                int chunkZ = playerChunkZ + dz;
                if (!world.isChunkLoaded(chunkX, chunkZ)) {
                    continue;
                }
                org.bukkit.Chunk chunk = world.getChunkAt(chunkX, chunkZ);
                for (org.bukkit.entity.Entity entity : chunk.getEntities()) {
                    if (entity instanceof Item item) {
                        Material type = item.getItemStack().getType();
                        if (excluded.contains(type)) {
                            continue;
                        }
                        item.remove();
                        removed++;
                    }
                }
            }
        }
        return removed;
    }

    private int cleanFolia(Player player, org.bukkit.World world, org.bukkit.Location location, int chunkRadius) {
        Set<Material> excluded = plugin.getExcludedItems();
        RegionScheduler regionScheduler = plugin.getServer().getRegionScheduler();
        org.bukkit.Chunk playerChunk = location.getChunk();
        int playerChunkX = playerChunk.getX();
        int playerChunkZ = playerChunk.getZ();
        for (int dx = -chunkRadius; dx <= chunkRadius; dx++) {
            for (int dz = -chunkRadius; dz <= chunkRadius; dz++) {
                int cx = playerChunkX + dx;
                int cz = playerChunkZ + dz;
                regionScheduler.execute(
                        plugin,
                        world,
                        cx,
                        cz,
                        () -> {
                            if (!world.isChunkLoaded(cx, cz)) {
                                return;
                            }
                            org.bukkit.Chunk chunk = world.getChunkAt(cx, cz);
                            for (org.bukkit.entity.Entity entity : chunk.getEntities()) {
                                if (entity instanceof Item item) {
                                    Material type = item.getItemStack().getType();
                                    if (excluded.contains(type)) {
                                        continue;
                                    }
                                    item.remove();
                                }
                            }
                        }
                );
            }
        }
        return 0;
    }
}