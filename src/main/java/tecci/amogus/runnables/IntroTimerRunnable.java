package tecci.amogus.runnables;

import org.bukkit.scheduler.BukkitRunnable;
import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.phases.ActivePhase;

public class IntroTimerRunnable extends BukkitRunnable {
    private final GameManager gameManager;
    private int timer = 5;

    public IntroTimerRunnable(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        timer--;

        if (timer <= 0) {
            gameManager.setPhase(new ActivePhase(gameManager));
        }
    }
}
