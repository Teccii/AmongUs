package tecci.amogus.minigame.phases;

import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.GamePhase;

public class ActivePhase extends GamePhase {
    public ActivePhase(GameManager gameManager) {
        super(gameManager);
    }

    @Override
    public GamePhaseType getPhaseType() { return GamePhaseType.ACTIVE; }

    @Override
    public boolean isValidTransition(GamePhaseType nextPhase) {
        return nextPhase == GamePhaseType.MEETING_BEGIN || nextPhase == GamePhaseType.OVER;
    }

    @Override
    public void onStart() { }

    @Override
    public void onEnd() { }
}
