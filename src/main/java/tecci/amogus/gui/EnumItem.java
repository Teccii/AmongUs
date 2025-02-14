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

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;

public class EnumItem<E extends Enum<E>> extends AbstractItem {
    private final Consumer<E> changeHandler;
    private final Function<E, String> valueDisplayHandler;
    private final E[] values;
    private final int len;
    private int index;

    public EnumItem(Consumer<E> changeHandler, Function<E, String> valueDisplayHandler, E defaultState) {
        this.changeHandler = changeHandler;
        this.valueDisplayHandler = valueDisplayHandler;

        values = defaultState.getDeclaringClass().getEnumConstants();
        len = values.length;
        Arrays.sort(values);

        index = Arrays.binarySearch(values, defaultState);
    }

    public E getVariant() {
        return values[index];
    }

    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(Material.NAUTILUS_SHELL)
                .addAllItemFlags()
                .setDisplayName(new ComponentBuilder(valueDisplayHandler.apply(getVariant())).color(ChatColor.WHITE).italic(false).build());
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        if (clickType == ClickType.LEFT) {
            if (index == len - 1) {
                index = 0;
            } else {
                index++;
            }
        } else if (clickType == ClickType.RIGHT) {
            if (index == 0) {
                index = len - 1;
            } else {
                index--;
            }
        }

        changeHandler.accept(getVariant());
        notifyWindows();
    }
}
