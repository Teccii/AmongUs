package tecci.amogus.runnables;

import org.bukkit.scheduler.BukkitRunnable;
import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.GamePhase;

public class VotingTimerRunnable extends BukkitRunnable {
    private final GameManager gameManager;
    private int timer;

    public VotingTimerRunnable(GameManager gameManager) {
        this.gameManager = gameManager;
        this.timer = (int)gameManager.getConfig().getVotingTimeSeconds();
    }

    @Override
    public void run() {
        timer--;

        if (gameManager.getCurrentPhase().getPhaseType() != GamePhase.GamePhaseType.VOTING) {
            this.cancel();
            return;
        }

        if (timer > 0) {
            //show it somewhere
            return;
        }

        //TODO: either meeting_dismissed, ejecting or over
        //TODO: meeting can end early if everyone has already voted
    }
}
