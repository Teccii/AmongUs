package tecci.amogus.minigame.phases;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import tecci.amogus.managers.GameManager;
import tecci.amogus.managers.ItemManager;
import tecci.amogus.managers.PlayerManager;
import tecci.amogus.minigame.GamePhase;
import tecci.amogus.minigame.Role;

public class ActivePhase extends GamePhase {
    public ActivePhase(GameManager gameManager) {
        super(gameManager);
    }

    @Override
    public GamePhaseType getPhaseType() { return GamePhaseType.ACTIVE; }

    @Override
    public boolean isValidTransition(GamePhaseType nextPhase) {
        return nextPhase == GamePhaseType.MEETING_BEGIN || nextPhase == GamePhaseType.GAME_OVER;
    }

    @Override
    public void onStart() {
        PlayerManager playerManager = gameManager.getPlayerManager();
        ItemManager itemManager = gameManager.getItemManager();

        for (Player player : Bukkit.getOnlinePlayers()) {
            itemManager.setLoadout(player, playerManager.getRole(player).getRoleItems());
        }
    }

    @Override
    public void onEnd() { }
}
