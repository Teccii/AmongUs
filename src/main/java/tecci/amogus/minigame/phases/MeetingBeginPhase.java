package tecci.amogus.minigame.phases;

import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.GamePhase;
import tecci.amogus.minigame.GamePhaseEnum;

public class MeetingBeginPhase extends GamePhase {
    public MeetingBeginPhase(GameManager gameManager) {
        super(gameManager);
    }

    @Override
    public GamePhaseEnum getPhase() { return GamePhaseEnum.MEETING_BEGIN; }

    @Override
    public boolean isValidTransition(GamePhaseEnum nextPhase) {
        return nextPhase == GamePhaseEnum.DISCUSSION || nextPhase == GamePhaseEnum.OVER;
    }

    @Override
    public void onStart() { }

    @Override
    public void onEnd() { }
}
