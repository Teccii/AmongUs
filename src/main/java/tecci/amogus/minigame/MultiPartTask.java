package tecci.amogus.minigame;

import tecci.amogus.managers.GameManager;
import tecci.amogus.util.MathUtil;

public abstract class MultiPartTask extends Task {
    protected final TaskInteractable[] interactables;
    protected int progress = 0;

    public MultiPartTask(GameManager gameManager, TaskInteractable[] interactables) {
        super(gameManager);
        this.interactables = interactables;
    }

    @Override
    public TaskInteractable getInteractable() {
        return interactables[progress];
    }

    public abstract String getBaseName();

    @Override
    public String getTaskName() {
        return String.format("%s (%d/%d)", getBaseName(), progress, getMaxProgress());
    }

    public int getProgress() { return progress; }
    public int getMaxProgress() { return interactables.length; }
    public void setProgress(int value) { this.progress = MathUtil.clamp(value, 0, getMaxProgress()); }

    public void progressTask() {
        progress++;

        if (progress >= getMaxProgress()) {
            submitTask();
        }
    }
}
