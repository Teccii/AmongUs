package tecci.amogus.gui;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

import java.util.function.Consumer;

public class ToggleItem extends AbstractItem {
    private final Consumer<Boolean> toggleHandler;
    private boolean state;

    public ToggleItem(Consumer<Boolean> toggleHandler, boolean defaultState) {
        this.toggleHandler = toggleHandler;
        state = defaultState;
    }

    @Override
    public ItemProvider getItemProvider() {
        if (state) {
            return new ItemBuilder(Material.NAUTILUS_SHELL)
                    .addAllItemFlags()
                    .setDisplayName(new ComponentBuilder("On").color(ChatColor.GREEN).italic(false).build());
        } else {
            return new ItemBuilder(Material.NAUTILUS_SHELL)
                    .addAllItemFlags()
                    .setDisplayName(new ComponentBuilder("Off").color(ChatColor.RED).italic(false).build());
        }
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        state = !state;
        toggleHandler.accept(state);

        notifyWindows();
    }
}
