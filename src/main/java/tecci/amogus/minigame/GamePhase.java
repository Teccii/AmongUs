package tecci.amogus.minigame;

import tecci.amogus.managers.GameManager;

public abstract class GamePhase {
    protected GameManager manager;

    public GamePhase(GameManager manager) {
        this.manager = manager;
    }

    public abstract GamePhaseEnum getPhase();

    public abstract void onStart();
    public abstract void onEnd();
}
