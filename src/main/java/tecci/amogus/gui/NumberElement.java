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
import java.util.function.Function;

public class NumberElement extends AbstractItem {
    private final Consumer<Integer> changeHandler;
    private final Function<Integer, String> valueDisplayHandler;
    private final int minValue;
    private final int maxValue;
    private int value;

    public NumberElement(Consumer<Integer> changeHandler, Function<Integer, String> valueDisplayHandler, int minValue, int maxValue, int defaultState) {
        this.changeHandler = changeHandler;
        this.valueDisplayHandler = valueDisplayHandler;
        this.minValue = minValue;
        this.maxValue = maxValue;
        value = defaultState;
    }

    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(Material.NAUTILUS_SHELL)
            .addAllItemFlags()
            .setDisplayName(new ComponentBuilder(valueDisplayHandler.apply(value)).color(ChatColor.WHITE).italic(false).build());
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        if (clickType == ClickType.LEFT) {
            if (value == maxValue) {
                value = minValue;
            } else {
                value++;
            }
        } else if (clickType == ClickType.RIGHT) {
            if (value == minValue) {
                value = maxValue;
            } else {
                value--;
            }
        }

        changeHandler.accept(value);
        notifyWindows();
    }
}
