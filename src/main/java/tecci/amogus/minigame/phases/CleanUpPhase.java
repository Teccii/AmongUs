package tecci.amogus.minigame.phases;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import tecci.amogus.managers.*;
import tecci.amogus.managers.MapManager.MapId;
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
        GlowingManager glowingManager = gameManager.getGlowingManager();
        MeetingManager meetingManager = gameManager.getMeetingManager();
        PlayerManager playerManager = gameManager.getPlayerManager();
        ItemManager itemManager = gameManager.getItemManager();
        MapManager mapManager = gameManager.getMapManager();

        meetingManager.clearAllVotes();
        meetingManager.setCanVote(false);

        gameManager.getCorpseManager().removeAllCorpses();

        mapManager.changeMap(MapId.LOBBY);
        playerManager.teleportAllRadial(mapManager.getCurrentMap().getSpawnLocation(), 5.0);

        playerManager.clearRoles();
        for (Player player : Bukkit.getOnlinePlayers()) {
            LobbyRole lobbyRole = new LobbyRole(gameManager, player);

            playerManager.setRole(player, lobbyRole);
            itemManager.setLoadout(player, lobbyRole.getRoleItems());

            glowingManager.unsetAllGlowingEntities(player);
        }

        gameManager.setPhase(new LobbyPhase(gameManager));
    }

    @Override
    public void onEnd() { }
}
