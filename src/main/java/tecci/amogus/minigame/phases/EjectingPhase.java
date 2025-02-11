package tecci.amogus.minigame.phases;

import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.GamePhase;
import tecci.amogus.minigame.GamePhaseEnum;

public class EjectingPhase extends GamePhase {
    public EjectingPhase(GameManager gameManager) {
        super(gameManager);
    }

    @Override
    public GamePhaseEnum getPhase() { return GamePhaseEnum.EJECTING; }

    @Override
    public boolean isValidTransition(GamePhaseEnum nextPhase) {
        return nextPhase == GamePhaseEnum.ACTIVE || nextPhase == GamePhaseEnum.OVER;
    }

    @Override
    public void onStart() { }

    @Override
    public void onEnd() { }
}
