package tecci.amogus.runnables;

import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.GamePhase;
import tecci.amogus.minigame.phases.VotingPhase;

public class DiscussionTimerRunnable extends PhaseTimerRunnable {
    public DiscussionTimerRunnable(GameManager gameManager) {
        super(gameManager);
        timer = (int)gameManager.getConfig().getDiscussionTimeSeconds();
    }

    @Override
    protected GamePhase.GamePhaseType getPhaseType() {
        return GamePhase.GamePhaseType.DISCUSSION;
    }

    @Override
    public void timerTick() {
        //show the time somewhere
    }

    @Override
    public void timerEnd() {
        gameManager.setPhase(new VotingPhase(gameManager));
    }
}

