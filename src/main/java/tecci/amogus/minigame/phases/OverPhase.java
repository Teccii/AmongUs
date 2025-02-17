package tecci.amogus.minigame.phases;

import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.GamePhase;
import tecci.amogus.minigame.WinCondition;

public class OverPhase extends GamePhase {
    private final WinCondition winCondition;

    public OverPhase(GameManager gameManager, WinCondition winCondition) {
        super(gameManager);
        this.winCondition = winCondition;
    }

    @Override
    public GamePhaseType getPhaseType() { return GamePhaseType.OVER; }

    @Override
    public boolean isValidTransition(GamePhaseType nextPhase) {
        return nextPhase == GamePhaseType.CLEAN_UP;
    }

    @Override
    public void onStart() { }

    @Override
    public void onEnd() { }
}
