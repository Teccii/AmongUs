package tecci.amogus.minigame.roles;

import org.bukkit.entity.Player;
import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.*;

public class CrewmateRole extends Role {
    public CrewmateRole(GameManager gameManager) {
        super(gameManager);
    }

    @Override
    public WinCondition getWinCondition() { return WinCondition.CREWMATE; }

    @Override
    public boolean canPerformTask(CrewmateTask task) { return true; }

    @Override
    public void performTask(CrewmateTask task) {
        //TODO
    }

    @Override
    public boolean canUseVent(Vent vent) { return false; }

    @Override
    public boolean canInteractWithPlayer(Player target) { return false; }
}
