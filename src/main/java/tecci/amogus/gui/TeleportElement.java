package tecci.amogus.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import tecci.amogus.util.ItemUtil;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public class TeleportElement extends AbstractItem {
    private final Player target;

    public TeleportElement(Player target) {
        this.target = target;
    }

    @Override
    public ItemProvider getItemProvider() {
        return ItemUtil.setSkullOwner(new ItemBuilder(Material.PLAYER_HEAD).addItemFlags().setDisplayName(target.getName()), target);
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        player.teleport(target.getLocation());
    }
}
