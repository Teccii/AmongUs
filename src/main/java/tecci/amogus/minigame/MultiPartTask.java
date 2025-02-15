package tecci.amogus.minigame;

import tecci.amogus.managers.GameManager;

public abstract class MultiPartTask extends Task {
    protected int progress = 0;
    protected final TaskInteractable[] interactables;

    public MultiPartTask(GameManager gameManager) {
        super(gameManager);

        interactables = new TaskInteractable[getMaxProgress()];
    }

    public int getProgress() { return progress; }
    public abstract int getMaxProgress();

    public abstract void progressTask(); //should call submitTask() at some point
    public abstract String getBaseName();

    @Override
    public String getTaskName() {
        return String.format("%s (%d/%d)", getBaseName(), getProgress(), getMaxProgress());
    }
}
