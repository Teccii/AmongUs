package tecci.amogus.minigame.phases;

import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.GamePhase;
import tecci.amogus.runnables.StartCountdownRunnable;

public class StartingPhase extends GamePhase {
    private final StartCountdownRunnable task;

    public StartingPhase(GameManager gameManager) {
        super(gameManager);

        task = new StartCountdownRunnable(gameManager);
    }

    @Override
    public GamePhaseType getPhaseType() { return GamePhaseType.STARTING; }

    @Override
    public boolean isValidTransition(GamePhaseType nextPhase) {
        return nextPhase == GamePhaseType.LOBBY || nextPhase == GamePhaseType.INTRO;
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
