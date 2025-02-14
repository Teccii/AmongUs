package tecci.amogus.minigame.phases;

import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.GamePhase;

public class MeetingBeginPhase extends GamePhase {
    public MeetingBeginPhase(GameManager gameManager) {
        super(gameManager);
    }

    @Override
    public GamePhaseType getPhaseType() { return GamePhaseType.MEETING_BEGIN; }

    @Override
    public boolean isValidTransition(GamePhaseType nextPhase) {
        return nextPhase == GamePhaseType.DISCUSSION || nextPhase == GamePhaseType.OVER;
    }

    @Override
    public void onStart() { }

    @Override
    public void onEnd() { }
}
