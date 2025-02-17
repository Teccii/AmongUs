package tecci.amogus.runnables;

import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.GamePhase;
import tecci.amogus.minigame.phases.ActivePhase;

public class MeetingDismissedTimerRunnable extends PhaseTimerRunnable {
    public MeetingDismissedTimerRunnable(GameManager gameManager) {
        super(gameManager);
        timer = 3;
    }

    @Override
    protected GamePhase.GamePhaseType getPhaseType() {
        return GamePhase.GamePhaseType.EJECTING;
    }

    @Override
    public void timerTick() {
        //idk
    }

    @Override
    public void timerEnd() {
        gameManager.setPhase(new ActivePhase(gameManager));
    }
}
