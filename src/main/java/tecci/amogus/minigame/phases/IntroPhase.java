package tecci.amogus.minigame.phases;

import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.GamePhase;
import tecci.amogus.minigame.GamePhaseEnum;

public class IntroPhase extends GamePhase {
    public IntroPhase(GameManager gameManager) {
        super(gameManager);
    }

    @Override
    public GamePhaseEnum getPhase() { return GamePhaseEnum.INTRO; }

    @Override
    public boolean isValidTransition(GamePhaseEnum nextPhase) {
        return nextPhase == GamePhaseEnum.ACTIVE;
    }

    @Override
    public void onStart() { }

    @Override
    public void onEnd() { }
}
