package tecci.amogus.minigame;

import tecci.amogus.managers.GameManager;

public abstract class GamePhase {
    protected GameManager gameManager;

    public GamePhase(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public abstract GamePhaseEnum getPhase();
    public abstract boolean isValidTransition(GamePhaseEnum nextPhase);
    public abstract void onStart();
    public abstract void onEnd();
}
