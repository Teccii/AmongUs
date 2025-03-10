package tecci.amogus.runnables;

import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.GamePhase;
import tecci.amogus.minigame.phases.ActivePhase;

public class IntroTimerRunnable extends PhaseTimerRunnable {
    public IntroTimerRunnable(GameManager gameManager) {
        super(gameManager);
        timer = 5;
    }

    @Override
    protected GamePhase.GamePhaseType getPhaseType() {
        return GamePhase.GamePhaseType.INTRO;
    }

    @Override
    public void timerTick() { }

    @Override
    public void timerEnd() {
        gameManager.setPhase(new ActivePhase(gameManager));
    }
}
