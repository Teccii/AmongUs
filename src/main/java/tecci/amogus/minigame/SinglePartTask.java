package tecci.amogus.minigame;

import tecci.amogus.managers.GameManager;

public abstract class SinglePartTask extends Task {
    protected final TaskInteractable interactable;

    public SinglePartTask(GameManager gameManager, TaskInteractable interactable) {
        super(gameManager);
        this.interactable = interactable;
    }

    @Override
    public TaskInteractable getInteractable() {
        return interactable;
    }
}
