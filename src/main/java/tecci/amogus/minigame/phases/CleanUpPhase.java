package tecci.amogus.minigame.phases;

import tecci.amogus.managers.GameManager;
import tecci.amogus.managers.MapManager.MapId;
import tecci.amogus.minigame.GamePhase;

public class CleanUpPhase extends GamePhase {
    public CleanUpPhase(GameManager gameManager) {
        super(gameManager);
    }

    @Override
    public GamePhaseEnum getPhaseType() { return GamePhaseEnum.CLEAN_UP; }

    @Override
    public boolean isValidTransition(GamePhaseEnum nextPhase) {
        return nextPhase == GamePhaseEnum.LOBBY;
    }

    @Override
    public void onStart() {
        gameManager.getMapManager().changeMap(MapId.LOBBY);
        gameManager.setPhase(new LobbyPhase(gameManager));
    }

    @Override
    public void onEnd() { }
}
