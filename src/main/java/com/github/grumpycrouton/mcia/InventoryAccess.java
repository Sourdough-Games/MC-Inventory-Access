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

    public void HandleChestSearch(Player player, String item_name) {
        Collection<Chest> chests = Helper.GetChestsAroundPlayer(player);
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
}
