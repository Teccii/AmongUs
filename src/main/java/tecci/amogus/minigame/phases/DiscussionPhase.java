package tecci.amogus.minigame.phases;

import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.GamePhase;
import tecci.amogus.minigame.GamePhaseEnum;

public class DiscussionPhase extends GamePhase {
    public DiscussionPhase(GameManager gameManager) {
        super(gameManager);
    }

    @Override
    public GamePhaseEnum getPhase() { return GamePhaseEnum.DISCUSSION; }

    @Override
    public boolean isValidTransition(GamePhaseEnum nextPhase) {
        return nextPhase == GamePhaseEnum.VOTING || nextPhase == GamePhaseEnum.OVER;
    }

    @Override
    public void onStart() { }

    @Override
    public void onEnd() { }
}
