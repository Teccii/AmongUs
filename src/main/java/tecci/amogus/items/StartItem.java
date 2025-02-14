package tecci.amogus.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.GamePhase.GamePhaseType;
import tecci.amogus.minigame.phases.StartingPhase;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;

public class StartItem extends CustomItem {
    private final GameManager gameManager;

    public StartItem(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    protected ItemProvider getItemProvider() {
        return new ItemBuilder(Material.NAUTILUS_SHELL).addAllItemFlags().setDisplayName("Start");
    }

    @Override
    public void onClick(ClickType clickType, Player player) {
        if (gameManager.getPlayerManager().isHost(player) && gameManager.getCurrentPhase().getPhaseType() == GamePhaseType.LOBBY) {
            gameManager.setPhase(new StartingPhase(gameManager));
        }
    }

}
