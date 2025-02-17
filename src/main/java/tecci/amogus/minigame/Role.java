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
    protected DeathReason deathReason;
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
        setDead(isDead, true);
    }

    public void setDead(boolean isDead, boolean updateMeetingManager) {
        this.isDead = isDead;

        if (updateMeetingManager) {
            if (isDead) {
                gameManager.getMeetingManager().addNewDeadBody(player);
            } else {
                gameManager.getMeetingManager().removeDeadBody(player);
            }
        }
    }

    public DeathReason getDeathReason() {
        return deathReason;
    }

    public void setDeathReason(DeathReason reason) {
        this.deathReason = reason;
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

    public boolean hasCompletedAllTasks() {
        for (Task task : tasks) {
            if (!task.isCompleted()) {
                return false;
            }
        }

        return true;
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