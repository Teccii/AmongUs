package tecci.amogus.runnables;

import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.GamePhase;
import tecci.amogus.minigame.phases.CleanUpPhase;

public class GameOverTimerRunnable extends PhaseTimerRunnable {
    public GameOverTimerRunnable(GameManager gameManager) {
        super(gameManager);
        timer = 10;
    }

    @Override
    protected GamePhase.GamePhaseType getPhaseType() {
        return GamePhase.GamePhaseType.GAME_OVER;
    }

    @Override
    public void timerTick() { }

    @Override
    public void timerEnd() {
        gameManager.setPhase(new CleanUpPhase(gameManager));
    }
}
