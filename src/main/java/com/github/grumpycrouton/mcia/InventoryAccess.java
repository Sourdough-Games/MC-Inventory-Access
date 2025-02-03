package com.github.grumpycrouton.mcia;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.entity.Player;
import org.bukkit.*;
import org.bukkit.block.*;
import java.util.Collection;
import java.util.Arrays;
import java.util.HashSet;
import org.bukkit.inventory.*;

public final class InventoryAccess extends JavaPlugin {
    @Override
    public void onEnable() {
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event -> event.registrar().register("searchchest", Arrays.asList("sc"), new SearchChestCommand(this))
        );
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void HandleChestSearch(Player player, String item_name) {
        Collection<Chest> chests = this.GetChestsAroundPlayer(player);
        for(Chest chest : chests) {
            Inventory inventory = chest.getInventory();

            for(ItemStack item : inventory.getContents()) {
                if(item == null) continue;

                if(item.getType().name().toLowerCase().startsWith(item_name.toLowerCase())) {
                    player.openInventory(inventory);
                    return;
                }
            }
        }
        player.sendMessage(Component.text("No chests in range containing item: ").append(Component.text(item_name)));
    }

    public Collection<Chest> GetChestsAroundPlayer(Player player) {
        Collection<Chest> chests = new HashSet<>();

        for (Chunk chunk : this.getChunksAroundPlayer(player)) {
            for (BlockState state : chunk.getTileEntities()) { // Get tile entities (containers, etc.)
                if (state instanceof Chest chest) {
                    chests.add(chest);
                }
            }
        }

        return chests;
    }

    private Collection<Chunk> getChunksAroundPlayer(Player player) {
        int[] offset = {-1,0,1};

        World world = player.getWorld();
        int baseX = player.getLocation().getChunk().getX();
        int baseZ = player.getLocation().getChunk().getZ();

        Collection<Chunk> chunksAroundPlayer = new HashSet<>();
        for(int x : offset) {
            for(int z : offset) {
                Chunk chunk = world.getChunkAt(baseX + x, baseZ + z);
                chunksAroundPlayer.add(chunk);
            }
        } return chunksAroundPlayer;
    }
}
