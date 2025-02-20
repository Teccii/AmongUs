package tecci.amogus.minigame;

import tecci.amogus.managers.GameManager;

public abstract class Sabotage {
    protected final GameManager gameManager;

    public Sabotage(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public abstract boolean disablesEmergencyButton();
    public abstract void onStart();
    public abstract void onEnd();
}
