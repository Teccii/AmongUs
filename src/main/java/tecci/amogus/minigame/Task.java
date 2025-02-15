package tecci.amogus.minigame;

import tecci.amogus.managers.GameManager;

public abstract class Task {
    protected final GameManager gameManager;
    protected boolean completed = false;

    public Task(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void submitTask() {
        completed = true;
    }

    public abstract TaskCategory getTaskType();
    public abstract TaskInteractable getInteractable();
    public abstract String getTaskName();
}
