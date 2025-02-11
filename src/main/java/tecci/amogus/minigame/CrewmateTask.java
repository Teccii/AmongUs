package tecci.amogus.minigame;

import org.bukkit.entity.Player;
import tecci.amogus.managers.GameManager;
import xyz.xenondevs.invui.gui.Gui;

public abstract class CrewmateTask {
    protected GameManager gameManager;

    public CrewmateTask(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public abstract TaskType getTaskType();
    public abstract Gui getGui(Role role);
    public abstract void perform(Player player, Role role);
}
