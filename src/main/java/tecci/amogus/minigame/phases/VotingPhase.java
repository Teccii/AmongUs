package tecci.amogus.minigame.phases;

import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.GamePhase;
import tecci.amogus.minigame.GamePhaseEnum;

public class VotingPhase extends GamePhase {
    public VotingPhase(GameManager gameManager) {
        super(gameManager);
    }

    @Override
    public GamePhaseEnum getPhase() { return GamePhaseEnum.VOTING; }

    @Override
    public boolean isValidTransition(GamePhaseEnum nextPhase) {
        return nextPhase == GamePhaseEnum.EJECTING || nextPhase == GamePhaseEnum.OVER;
    }

    @Override
    public void onStart() { }

    @Override
    public void onEnd() { }
}
