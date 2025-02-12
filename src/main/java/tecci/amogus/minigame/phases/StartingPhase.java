package tecci.amogus.minigame.phases;

import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.GamePhase;

public class StartingPhase extends GamePhase {
    public StartingPhase(GameManager gameManager) {
        super(gameManager);
    }

    @Override
    public GamePhaseEnum getPhaseType() { return GamePhaseEnum.STARTING; }

    @Override
    public boolean isValidTransition(GamePhaseEnum nextPhase) {
        return nextPhase == GamePhaseEnum.LOBBY || nextPhase == GamePhaseEnum.INTRO;
    }

    @Override
    public void onStart() { }

    @Override
    public void onEnd() { }
}
