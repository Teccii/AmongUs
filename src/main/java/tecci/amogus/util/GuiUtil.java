package tecci.amogus.util;

import org.bukkit.inventory.meta.ItemMeta;
import xyz.xenondevs.invui.item.builder.ItemBuilder;

public final class GuiUtil {
    public static ItemBuilder hideTooltip(ItemBuilder builder) {
        return builder.addModifier(item -> {
            ItemMeta meta = item.getItemMeta();
            meta.setHideTooltip(true);

            item.setItemMeta(meta);

            return item;
        });
    }
}
