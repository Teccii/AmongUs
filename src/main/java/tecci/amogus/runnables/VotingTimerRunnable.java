package tecci.amogus.runnables;

import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.GamePhase;
import tecci.amogus.minigame.phases.EjectingPhase;

public class VotingTimerRunnable extends PhaseTimerRunnable {
    public VotingTimerRunnable(GameManager gameManager) {
        super(gameManager);
        timer = (int)gameManager.getConfig().getVotingTimeSeconds() + 3; //3 seconds to show players vote result
    }

    @Override
    protected GamePhase.GamePhaseType getPhaseType() {
        return GamePhase.GamePhaseType.VOTING;
    }

    @Override
    public void timerTick() {
        if (timer > 3) {
            //show the timer somewhere (subtract 3 seconds from it)
        } else {
            if (gameManager.getMeetingManager().canVote()) {
                gameManager.getMeetingManager().setCanVote(false);
            }

            //show all the votes
        }
    }

    @Override
    public void timerEnd() {
        gameManager.setPhase(new EjectingPhase(gameManager, gameManager.getMeetingManager().getVoteResult()));
    }
}
