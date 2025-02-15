package tecci.amogus.minigame;

import tecci.amogus.managers.GameManager;

public abstract class Task {
    protected final GameManager gameManager;
    protected final TaskInteractable interactable;
    protected boolean completed = false;

    public Task(GameManager gameManager, TaskInteractable interactable) {
        this.gameManager = gameManager;
        this.interactable = interactable;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void submitTask() {
        completed = true;
    }

    public abstract TaskType getTaskType();
    public abstract TaskCategory getTaskCategory();
    public abstract TaskInteractable getInteractable();
    public abstract String getTaskName();
}
