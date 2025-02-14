package tecci.amogus.minigame.phases;

import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.GamePhase;

public class EjectingPhase extends GamePhase {
    public EjectingPhase(GameManager gameManager) {
        super(gameManager);
    }

    @Override
    public GamePhaseType getPhaseType() { return GamePhaseType.EJECTING; }

    @Override
    public boolean isValidTransition(GamePhaseType nextPhase) {
        return nextPhase == GamePhaseType.ACTIVE || nextPhase == GamePhaseType.OVER;
    }

    @Override
    public void onStart() { }

    @Override
    public void onEnd() { }
}
