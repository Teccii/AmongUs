package tecci.amogus.minigame.phases;

import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.GamePhase;

public class DiscussionPhase extends GamePhase {
    public DiscussionPhase(GameManager gameManager) {
        super(gameManager);
    }

    @Override
    public GamePhaseType getPhaseType() { return GamePhaseType.DISCUSSION; }

    @Override
    public boolean isValidTransition(GamePhaseType nextPhase) {
        return nextPhase == GamePhaseType.VOTING || nextPhase == GamePhaseType.OVER;
    }

    @Override
    public void onStart() { }

    @Override
    public void onEnd() { }
}
