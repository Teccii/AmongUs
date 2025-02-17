package tecci.amogus.runnables;

import org.bukkit.scheduler.BukkitRunnable;
import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.GamePhase;

public abstract class PhaseTimerRunnable extends BukkitRunnable {
    protected final GameManager gameManager;
    protected int timer;

    public PhaseTimerRunnable(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    protected abstract GamePhase.GamePhaseType getPhaseType();

    @Override
    public void run() {
        if (gameManager.getCurrentPhase().getPhaseType() != getPhaseType()) {
            this.cancel();
            return;
        }

        if (timer > 0) {
            timerTick();
            return;
        }

        timerEnd();
    }

    public abstract void timerTick();
    public abstract void timerEnd();
}
