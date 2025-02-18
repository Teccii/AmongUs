package tecci.amogus.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import tecci.amogus.managers.GameManager;
import tecci.amogus.managers.MeetingManager;
import tecci.amogus.minigame.Role;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public class SkipElement extends AbstractItem {
    private final GameManager gameManager;

    public SkipElement(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(Material.NAUTILUS_SHELL).addAllItemFlags().setDisplayName("Skip");
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        MeetingManager meetingManager = gameManager.getMeetingManager();
        Role role = gameManager.getPlayerManager().getRole(player);

        if (role == null || role.isDead() || role.isNonPlayingRole()) {
            return;
        }

        if (meetingManager.isMeetingActive() && meetingManager.canVote() && !meetingManager.hasVoted(player)) {
            meetingManager.addVote(player, null);
        }
    }
}
