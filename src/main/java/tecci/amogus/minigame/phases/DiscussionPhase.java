package tecci.amogus.minigame.phases;

import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.GamePhase;
import tecci.amogus.runnables.DiscussionTimerRunnable;

public class DiscussionPhase extends GamePhase {
    private final DiscussionTimerRunnable task;

    public DiscussionPhase(GameManager gameManager) {
        super(gameManager);

        task = new DiscussionTimerRunnable(gameManager);
    }

    @Override
    public GamePhaseType getPhaseType() { return GamePhaseType.DISCUSSION; }

    @Override
    public boolean isValidTransition(GamePhaseType nextPhase) {
        return nextPhase == GamePhaseType.VOTING || nextPhase == GamePhaseType.OVER;
    }

    @Override
    public void onStart() {
        task.runTaskTimer(gameManager.getPlugin(), 0, 20);
    }

    @Override
    public void onEnd() {
        task.cancel();
    }
}
