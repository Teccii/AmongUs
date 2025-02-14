package tecci.amogus.minigame.phases;

import tecci.amogus.managers.GameManager;
import tecci.amogus.managers.MapManager.MapId;
import tecci.amogus.minigame.GamePhase;

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
        gameManager.setPhase(new LobbyPhase(gameManager));
    }

    @Override
    public void onEnd() { }
}
