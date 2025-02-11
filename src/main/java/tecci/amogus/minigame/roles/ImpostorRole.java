package tecci.amogus.minigame.roles;

import org.bukkit.entity.Player;
import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.*;

public class ImpostorRole extends Role {
    public ImpostorRole(GameManager gameManager) {
        super(gameManager);
    }

    @Override
    public WinCondition getWinCondition() { return WinCondition.IMPOSTOR; }

    @Override
    public boolean canPerformTask(CrewmateTask task) { return task.getTaskType() != TaskType.REGULAR; }

    @Override
    public boolean canUseVent(Vent vent) { return true; }

    @Override
    public void useVent(Vent vent) {
        //TODO
    }

    @Override
    public boolean canInteractWithPlayer(Player target) {
        return !(this.gameManager.getPlayerManager().getRole(target) instanceof ImpostorRole);
    }

    @Override
    public void interactWithPlayer(Player target) {
        //kill them
    }
}
