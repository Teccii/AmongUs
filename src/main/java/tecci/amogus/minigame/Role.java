package tecci.amogus.minigame;

import org.bukkit.entity.Player;
import tecci.amogus.items.CustomItem;
import tecci.amogus.managers.GameManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Role {
    protected final GameManager gameManager;
    protected final Map<Integer, CustomItem> roleItems = new HashMap<>();
    protected final List<Task> tasks = new ArrayList<>();
    protected final Player player;
    protected int meetingsLeft;
    protected boolean isDead;

    public Role(GameManager gameManager, Player player) {
        this.gameManager = gameManager;
        this.player = player;

        setMeetingsLeft(gameManager.getConfig().getEmergencyMeetings());
        setRoleItems();
    }

    public int getMeetingsLeft() {
        return meetingsLeft;
    }

    public void setMeetingsLeft(int meetingsLeft) {
        this.meetingsLeft = meetingsLeft;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean isDead) {
        this.isDead = isDead;
    }

    public Task getTask(TaskInteractable interactable) {
        for (Task task : tasks) {
            if (task.getInteractable().equals(interactable)) {
                return task;
            }
        }

        return null;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void clearTasks() {
        tasks.clear();
    }

    public abstract WinCondition getWinCondition();
    public abstract void setRoleItems();

    public abstract boolean canInteract(Interactable interactable);

    public abstract boolean canInteractWithPlayer(Player target);
    public void interactWithPlayer(Player target) { }
}