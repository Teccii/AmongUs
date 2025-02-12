package tecci.amogus.minigame.phases;

import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.GamePhase;

public class OverPhase extends GamePhase {
    public OverPhase(GameManager gameManager) {
        super(gameManager);
    }

    @Override
    public GamePhaseEnum getPhaseType() { return GamePhaseEnum.OVER; }

    @Override
    public boolean isValidTransition(GamePhaseEnum nextPhase) {
        return nextPhase == GamePhaseEnum.CLEAN_UP;
    }

    @Override
    public void onStart() { }

    @Override
    public void onEnd() { }
}
