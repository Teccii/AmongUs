package tecci.amogus.minigame.phases;

import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.GamePhase;

public class StartingPhase extends GamePhase {
    public StartingPhase(GameManager gameManager) {
        super(gameManager);
    }

    @Override
    public GamePhaseType getPhaseType() { return GamePhaseType.STARTING; }

    @Override
    public boolean isValidTransition(GamePhaseType nextPhase) {
        return nextPhase == GamePhaseType.LOBBY || nextPhase == GamePhaseType.INTRO;
    }

    @Override
    public void onStart() { }

    @Override
    public void onEnd() { }
}
