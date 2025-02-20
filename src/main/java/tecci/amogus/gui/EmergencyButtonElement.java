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
import xyz.xenondevs.invui.gui.AbstractGui;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;
import xyz.xenondevs.invui.item.impl.controlitem.ControlItem;

public class EmergencyButtonElement extends ControlItem<AbstractGui> {
    private final GameManager gameManager;
    private final Player player;

    public EmergencyButtonElement(GameManager gameManager, Player player) {
        this.gameManager = gameManager;
        this.player = player;
    }

    @Override
    public ItemProvider getItemProvider(AbstractGui gui) {
        return new ItemBuilder(Material.NAUTILUS_SHELL)
                .addAllItemFlags()
                .setDisplayName(gameManager.getPlayerManager().getRole(player).getMeetingsLeft() + " Emergency Meetings Left");
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        Role role = gameManager.getPlayerManager().getRole(player);

        if (role != null && role.getMeetingsLeft() > 0) {
            role.setMeetingsLeft(role.getMeetingsLeft() - 1);

            gameManager.setPhase(new MeetingBeginPhase(gameManager, player, MeetingReason.EMERGENCY_BUTTON));
            getGui().closeForAllViewers();
        }
    }
}
