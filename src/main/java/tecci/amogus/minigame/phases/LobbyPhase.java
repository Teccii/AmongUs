package tecci.amogus.minigame.phases;

import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.GamePhase;
import tecci.amogus.minigame.GamePhaseEnum;

public class LobbyPhase extends GamePhase {
    public LobbyPhase(GameManager manager) {
        super(manager);
    }

    @Override
    public GamePhaseEnum getPhase() { return GamePhaseEnum.LOBBY; }

    @Override
    public void onStart() { }

    @Override
    public void onEnd() { }
}
