package tecci.amogus.minigame.interactables;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import tecci.amogus.gui.FlickyItem;
import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.Task;
import tecci.amogus.minigame.TaskInteractable;
import tecci.amogus.minigame.TaskCategory;
import tecci.amogus.minigame.TaskType;
import tecci.amogus.minigame.tasks.FlickyTask;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.window.Window;

public class FlickyInteractable extends TaskInteractable {
    public FlickyInteractable(GameManager gameManager, Location location) {
        super(gameManager, location);
    }

    @Override
    public TaskType getTaskType() { return TaskType.FLICKY; }

    @Override
    public TaskCategory getTaskLength() { return TaskCategory.SHORT; }

    @Override
    public Task createTask() {
        return new FlickyTask(gameManager, this);
    }

    @Override
    public Gui getGui(Player player) {
        Task task = gameManager.getPlayerManager().getRole(player).getTask(this);

        return Gui.normal()
                .setStructure(
                        ". . .",
                        ". X .",
                        ". . ."
                )
                .addIngredient('X', new FlickyItem(this::progressTask, task))
                .build();
    }

    @Override
    public void interact(Player player) {
        Window.single()
                .setGui(getGui(player))
                .setTitle("Flicky Task")
                .open(player);
    }
}
