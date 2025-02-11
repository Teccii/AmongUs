package tecci.amogus.minigame.phases;

import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.GamePhase;
import tecci.amogus.minigame.GamePhaseEnum;

public class ActivePhase extends GamePhase {
    public ActivePhase(GameManager gameManager) {
        super(gameManager);
    }

    @Override
    public GamePhaseEnum getPhase() { return GamePhaseEnum.ACTIVE; }

    @Override
    public boolean isValidTransition(GamePhaseEnum nextPhase) {
        return nextPhase == GamePhaseEnum.MEETING_BEGIN || nextPhase == GamePhaseEnum.OVER;
    }

    @Override
    public void onStart() { }

    @Override
    public void onEnd() { }
}
