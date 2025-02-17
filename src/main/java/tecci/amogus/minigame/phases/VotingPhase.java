package tecci.amogus.minigame.phases;

import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.GamePhase;
import tecci.amogus.runnables.VotingTimerRunnable;

public class VotingPhase extends GamePhase {
    private final VotingTimerRunnable task;

    public VotingPhase(GameManager gameManager) {
        super(gameManager);

        task = new VotingTimerRunnable(gameManager);
    }

    @Override
    public GamePhaseType getPhaseType() { return GamePhaseType.VOTING; }

    @Override
    public boolean isValidTransition(GamePhaseType nextPhase) {
        return nextPhase == GamePhaseType.EJECTING || nextPhase == GamePhaseType.OVER;
    }

    @Override
    public void onStart() {
        gameManager.getMeetingManager().setCanVote(true);
        task.runTaskTimer(gameManager.getPlugin(), 0, 20);
    }

    @Override
    public void onEnd() {
        task.cancel();
    }
}
