package tecci.amogus.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.MeetingReason;
import tecci.amogus.minigame.Role;
import tecci.amogus.minigame.phases.MeetingBeginPhase;
import tecci.amogus.util.GuiUtil;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public class EmergencyButtonElement extends AbstractItem  {
    private final GameManager gameManager;

    public EmergencyButtonElement(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public ItemProvider getItemProvider() {
        return GuiUtil.hideTooltip(new ItemBuilder(Material.NAUTILUS_SHELL));
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        Role role = gameManager.getPlayerManager().getRole(player);

        if (role != null && role.getMeetingsLeft() > 0) {
            role.setMeetingsLeft(role.getMeetingsLeft() - 1);

            gameManager.setPhase(new MeetingBeginPhase(gameManager, player, MeetingReason.EMERGENCY_BUTTON));
        }
    }
}
