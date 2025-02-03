package com.github.grumpycrouton.mcia;

import java.util.Collection;
import java.util.HashSet;
import org.bukkit.entity.Player;
import org.bukkit.block.*;
import org.bukkit.*;

public class Helper {

    // Return type changed to HashSet for consistency
    public static Collection<Chest> GetChestsAroundPlayer(Player player) {
        Collection<Chest> chests = new HashSet<>();

        // Directly call the static method here without 'this'
        for (Chunk chunk : getChunksAroundPlayer(player)) {
            for (BlockState state : chunk.getTileEntities()) { // Get tile entities (containers, etc.)
                if (state instanceof Chest chest) {
                    chests.add(chest);
                }
            }
        }

        return chests;
    }

    public static Collection<Chunk> getChunksAroundPlayer(Player player) {
        int[] offset = {-1, 0, 1};

        World world = player.getWorld();
        int baseX = player.getLocation().getChunk().getX();
        int baseZ = player.getLocation().getChunk().getZ();

        Collection<Chunk> chunksAroundPlayer = new HashSet<>();
        for (int x : offset) {
            for (int z : offset) {
                Chunk chunk = world.getChunkAt(baseX + x, baseZ + z);
                chunksAroundPlayer.add(chunk);
            }
        }
        return chunksAroundPlayer;
    }
}
