package tecci.amogus.minigame.phases;

import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.GamePhase;

public class VotingPhase extends GamePhase {
    public VotingPhase(GameManager gameManager) {
        super(gameManager);
    }

    @Override
    public GamePhaseType getPhaseType() { return GamePhaseType.VOTING; }

    @Override
    public boolean isValidTransition(GamePhaseType nextPhase) {
        return nextPhase == GamePhaseType.EJECTING || nextPhase == GamePhaseType.OVER;
    }

    @Override
    public void onStart() { }

    @Override
    public void onEnd() { }
}
