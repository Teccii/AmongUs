package tecci.amogus.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import tecci.amogus.managers.GameManager;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public class EmergencyButtonItem extends AbstractItem {
    private final GameManager gameManager;

    public EmergencyButtonItem(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public ItemProvider getItemProvider() {
        return ItemUtil.hideTooltip(new ItemBuilder(Material.NAUTILUS_SHELL).setCustomModelData(1));
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        //TODO: set phase to MeetingBeginPhase, which should keep track of the player that called the meeting
    }
}
