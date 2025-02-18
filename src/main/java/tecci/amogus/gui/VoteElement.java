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

public class VoteElement extends AbstractItem {
    private final GameManager gameManager;
    private final Player target;

    public VoteElement(GameManager gameManager, Player target) {
        this.gameManager = gameManager;
        this.target = target;
    }

    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(Material.NAUTILUS_SHELL).setDisplayName(target.getName());
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        MeetingManager meetingManager = gameManager.getMeetingManager();
        Role role = gameManager.getPlayerManager().getRole(player);

        if (target.getUniqueId() == player.getUniqueId() || role == null || role.isDead() || role.isNonPlayingRole()) {
            return;
        }

        if (meetingManager.isMeetingActive() && meetingManager.canVote() && !meetingManager.hasVoted(player)) {
            meetingManager.addVote(player, target.getUniqueId());
        }
    }
}
