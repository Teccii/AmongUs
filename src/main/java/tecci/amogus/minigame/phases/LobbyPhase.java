package tecci.amogus.minigame.phases;

import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.GamePhase;

public class LobbyPhase extends GamePhase {
    public LobbyPhase(GameManager gameManager) {
        super(gameManager);
    }

    @Override
    public GamePhaseEnum getPhaseType() { return GamePhaseEnum.LOBBY; }

    @Override
    public boolean isValidTransition(GamePhaseEnum nextPhase) {
        return nextPhase == GamePhaseEnum.STARTING;
    }

    @Override
    public void onStart() { }

    @Override
    public void onEnd() { }
}
