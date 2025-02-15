package tecci.amogus.minigame.tasks;

import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.Task;
import tecci.amogus.minigame.TaskCategory;
import tecci.amogus.minigame.TaskInteractable;
import tecci.amogus.minigame.interactables.FlickyInteractable;

public class FlickyTask extends Task {
    private final FlickyInteractable interactable;

    public FlickyTask(GameManager gameManager, FlickyInteractable interactable) {
        super(gameManager);

        this.interactable = interactable;
    }

    @Override
    public TaskCategory getTaskType() { return TaskCategory.SHORT; }

    @Override
    public TaskInteractable getInteractable() { return interactable; }

    @Override
    public String getTaskName() { return "Flicky Task"; }
}
