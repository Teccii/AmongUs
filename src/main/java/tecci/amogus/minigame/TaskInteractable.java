package tecci.amogus.minigame;

import org.bukkit.Location;
import tecci.amogus.managers.GameManager;

public abstract class TaskInteractable extends GuiInteractable {
    public TaskInteractable(GameManager gameManager, Location location) {
        super(gameManager, location);
    }

    public abstract TaskType getTaskType();
    public abstract TaskCategory getTaskLength();
    public abstract Task createTask();

    public void progressTask(Task task) {
        if (task instanceof MultiPartTask multiPartTask) {
            multiPartTask.progressTask();
        } else {
            task.submitTask();
        }
    }
}
