package tecci.amogus.minigame.phases;

import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.GamePhase;

public class IntroPhase extends GamePhase {
    public IntroPhase(GameManager gameManager) {
        super(gameManager);
    }

    @Override
    public GamePhaseType getPhaseType() { return GamePhaseType.INTRO; }

    @Override
    public boolean isValidTransition(GamePhaseType nextPhase) {
        return nextPhase == GamePhaseType.ACTIVE;
    }

    @Override
    public void onStart() { }

    @Override
    public void onEnd() { }
}
