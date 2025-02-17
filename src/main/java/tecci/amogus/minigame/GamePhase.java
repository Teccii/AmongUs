package tecci.amogus.minigame;

import tecci.amogus.managers.GameManager;

public abstract class GamePhase {
    public enum GamePhaseType {
        LOBBY,              //LOBBY ->              STARTING
        STARTING,           //STARTING ->           INTRO | LOBBY
        INTRO,              //INTRO ->              ACTIVE
        ACTIVE,             //ACTIVE ->             MEETING_BEGIN | OVER
        MEETING_BEGIN,      //MEETING_BEGIN ->      DISCUSSION | OVER
        DISCUSSION,         //DISCUSSION ->         VOTING | OVER
        VOTING,             //VOTING ->             EJECTING | OVER
        EJECTING,           //EJECTING ->           ACTIVE | OVER
        OVER,               //OVER ->               CLEAN_UP
        CLEAN_UP,           //CLEAN_UP ->           LOBBY
    }

    protected GameManager gameManager;

    public GamePhase(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public abstract GamePhaseType getPhaseType();
    public abstract boolean isValidTransition(GamePhaseType nextPhase);
    public abstract void onStart();
    public abstract void onEnd();
}
