package tecci.amogus.util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import xyz.xenondevs.invui.item.builder.ItemBuilder;

public final class ItemUtil {
    public static ItemBuilder hideTooltip(ItemBuilder builder) {
        return builder.addModifier(item -> {
            ItemMeta meta = item.getItemMeta();
            meta.setHideTooltip(true);

            item.setItemMeta(meta);

            return item;
        });
    }

    public static ItemBuilder setSkullOwner(ItemBuilder builder, Player owner) {
        return builder.addModifier(item -> {
            if (item.getItemMeta() instanceof SkullMeta meta) {
                meta.setOwningPlayer(owner);

                item.setItemMeta(meta);
            }

            return item;
        });
    }
}
