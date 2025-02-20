package tecci.amogus.items;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.Corpse;
import tecci.amogus.minigame.MeetingReason;
import tecci.amogus.minigame.phases.MeetingBeginPhase;
import tecci.amogus.util.BlockUtil;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;

import java.util.List;

public class ReportItem extends CustomItem {
    private final GameManager gameManager;

    public ReportItem(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    protected ItemProvider getItemProvider() {
        return new ItemBuilder(Material.NAUTILUS_SHELL).addAllItemFlags().setDisplayName("Report");
    }

    @Override
    public void onClick(ClickType clickType, Player player) {
        List<Corpse> corpses = gameManager.getCorpseManager().getActiveCorpses();
        Location playerLocation = player.getLocation();

        for (Corpse corpse : corpses) {
            Location corpseLocation = corpse.getLocation();

            if (corpse.isVisibleTo(player) && playerLocation.distance(corpseLocation) <= 16.0 && !BlockUtil.isObstructed(playerLocation, corpseLocation)) {
                gameManager.setPhase(new MeetingBeginPhase(gameManager, player, MeetingReason.DEAD_BODY));
            }
        }
    }
}
