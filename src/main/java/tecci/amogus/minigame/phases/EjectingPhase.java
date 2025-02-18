package tecci.amogus.minigame.phases;

import org.bukkit.Bukkit;
import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.GamePhase;
import tecci.amogus.minigame.VoteResult;
import tecci.amogus.minigame.VoteResult.ResultType;
import tecci.amogus.runnables.MeetingDismissedTimerRunnable;
import tecci.amogus.runnables.PlayerEjectedTimerRunnable;

public class EjectingPhase extends GamePhase {
    private final VoteResult voteResult;
    private MeetingDismissedTimerRunnable dismissedTask;
    private PlayerEjectedTimerRunnable ejectedTask;

    public EjectingPhase(GameManager gameManager, VoteResult voteResult) {
        super(gameManager);

        this.voteResult = voteResult;
    }

    @Override
    public GamePhaseType getPhaseType() { return GamePhaseType.EJECTING; }

    @Override
    public boolean isValidTransition(GamePhaseType nextPhase) {
        return nextPhase == GamePhaseType.ACTIVE || nextPhase == GamePhaseType.GAME_OVER;
    }

    @Override
    public void onStart() {
        ResultType resultType = voteResult.getResultType();

        if (resultType == ResultType.SKIPPED || resultType == ResultType.TIE) {
            dismissedTask = new MeetingDismissedTimerRunnable(gameManager);
            dismissedTask.runTaskTimer(gameManager.getPlugin(), 0, 20);

        } else if (resultType == ResultType.EJECTED) {
            ejectedTask = new PlayerEjectedTimerRunnable(gameManager, Bukkit.getPlayer(voteResult.getEjectedPlayer()));
            ejectedTask.runTaskTimer(gameManager.getPlugin(), 0, 20);
        }
    }

    @Override
    public void onEnd() {
        if (dismissedTask != null) {
            dismissedTask.cancel();
        }

        if (ejectedTask != null) {
            ejectedTask.cancel();
        }
    }
}
