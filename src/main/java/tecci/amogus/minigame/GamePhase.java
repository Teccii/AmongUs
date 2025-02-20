package tecci.amogus.minigame;

import tecci.amogus.managers.GameManager;

public abstract class GamePhase {
    public enum GamePhaseType {
        LOBBY,              //LOBBY ->              STARTING
        STARTING,           //STARTING ->           INTRO | LOBBY
        INTRO,              //INTRO ->              ACTIVE
        ACTIVE,             //ACTIVE ->             MEETING_BEGIN | GAME_OVER
        MEETING_BEGIN,      //MEETING_BEGIN ->      DISCUSSION | GAME_OVER
        DISCUSSION,         //DISCUSSION ->         VOTING | GAME_OVER
        VOTING,             //VOTING ->             EJECTING | GAME_OVER
        EJECTING,           //EJECTING ->           ACTIVE | GAME_OVER
        GAME_OVER,          //GAME_OVER ->          CLEAN_UP
        CLEAN_UP;           //CLEAN_UP ->           LOBBY

        @Override
        public String toString() {
            return switch (this) {
                case LOBBY -> "Lobby";
                case STARTING -> "Starting";
                case INTRO -> "Intro";
                case ACTIVE -> "Active";
                case MEETING_BEGIN -> "Meeting Begin";
                case DISCUSSION -> "Discussion";
                case VOTING -> "Voting";
                case EJECTING -> "Ejecting";
                case GAME_OVER -> "Game Over";
                case CLEAN_UP -> "Clean Up";
            };
        }
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
