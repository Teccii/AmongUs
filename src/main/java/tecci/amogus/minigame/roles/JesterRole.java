package tecci.amogus.minigame.roles;

import org.bukkit.entity.Player;
import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.*;

public class JesterRole extends Role {
    public JesterRole(GameManager gameManager) {
        super(gameManager);
    }

    @Override
    public WinCondition getWinCondition() { return WinCondition.JESTER; }

    @Override
    public boolean canPerformTask(CrewmateTask task) { return task.getTaskType() != TaskType.REGULAR; }

    @Override
    public boolean canUseVent(Vent vent) { return false; }

    @Override
    public boolean canInteractWithPlayer(Player target) { return false; }
}
