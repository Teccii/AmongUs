package tecci.amogus.runnables;

import org.bukkit.scheduler.BukkitRunnable;
import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.GamePhase;
import tecci.amogus.minigame.phases.VotingPhase;

public class DiscussionTimerRunnable extends BukkitRunnable {
    private final GameManager gameManager;
    private int timer;

    public DiscussionTimerRunnable(GameManager gameManager) {
        this.gameManager = gameManager;
        this.timer = (int)gameManager.getConfig().getDiscussionTimeSeconds();
    }

    @Override
    public void run() {
        timer--;

        if (gameManager.getCurrentPhase().getPhaseType() == GamePhase.GamePhaseType.OVER) {
            this.cancel();
            return;
        }

        if (timer > 0) {
            //show it somewhere
            return;
        }

        gameManager.setPhase(new VotingPhase(gameManager));
    }
}
