package tecci.amogus.minigame.phases;

import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.GamePhase;

public class LobbyPhase extends GamePhase {
    public LobbyPhase(GameManager gameManager) {
        super(gameManager);
    }

    @Override
    public GamePhaseType getPhaseType() { return GamePhaseType.LOBBY; }

    @Override
    public boolean isValidTransition(GamePhaseType nextPhase) {
        return nextPhase == GamePhaseType.STARTING;
    }

    @Override
    public void onStart() {
        gameManager.setPhase(new ActivePhase(gameManager));
    }

    @Override
    public void onEnd() { }
}
