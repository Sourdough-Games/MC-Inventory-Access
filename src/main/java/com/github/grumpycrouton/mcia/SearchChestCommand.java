package com.github.grumpycrouton.mcia;

import org.bukkit.Material;
import java.util.stream.Collectors;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.Arrays;

@NullMarked
public class SearchChestCommand implements BasicCommand {
    private final InventoryAccess plugin;

    public SearchChestCommand(InventoryAccess plugin) {
        this.plugin = plugin;
    }
    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        if (!(commandSourceStack.getSender() instanceof Player)) {
            commandSourceStack.getSender().sendRichMessage("<red>Only players can use this command!");
            return;
        }

        if (args.length == 0) {
            commandSourceStack.getSender().sendRichMessage("<red>You must specify an item to search for!");
            return;
        }

        this.plugin.HandleChestSearch((Player)commandSourceStack.getSender(), args[0]);
    }

    @Override
    public @Nullable String permission() {
        return "inventoryaccess.searchchests";
    }

    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
        if (args.length == 0) {
            return getAllItemNames();
        }

        String lastArg = args[args.length - 1].toLowerCase();
        return getAllItemNames().stream()
                .filter(name -> name.startsWith(lastArg))
                .toList();
    }

    private Collection<String> getAllItemNames() {
        return Arrays.stream(Material.values())
                .map(material -> material.name().toLowerCase()) // Convert to lowercase for consistency
                .collect(Collectors.toList());
    }
}