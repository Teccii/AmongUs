package tecci.amogus.minigame.phases;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import tecci.amogus.managers.GameManager;
import tecci.amogus.managers.MapManager.MapId;
import tecci.amogus.managers.PlayerManager;
import tecci.amogus.minigame.GamePhase;
import tecci.amogus.minigame.roles.LobbyRole;

public class CleanUpPhase extends GamePhase {
    public CleanUpPhase(GameManager gameManager) {
        super(gameManager);
    }

    @Override
    public GamePhaseType getPhaseType() { return GamePhaseType.CLEAN_UP; }

    @Override
    public boolean isValidTransition(GamePhaseType nextPhase) {
        return nextPhase == GamePhaseType.LOBBY;
    }

    @Override
    public void onStart() {
        gameManager.getMapManager().changeMap(MapId.LOBBY);

        PlayerManager playerManager = gameManager.getPlayerManager();
        playerManager.clearRoles();

        for (Player player : Bukkit.getOnlinePlayers()) {
            playerManager.setRole(player, new LobbyRole(gameManager, player));
        }

        gameManager.setPhase(new LobbyPhase(gameManager));
    }

    @Override
    public void onEnd() { }
}
