package tecci.amogus.minigame;

import org.bukkit.entity.Player;
import tecci.amogus.managers.GameManager;

public abstract class Role {
    protected GameManager gameManager;
    protected Player player;
    protected int meetingsLeft;

    public Role(GameManager gameManager, Player player) {
        this.gameManager = gameManager;
        this.player = player;

        setMeetingsLeft(gameManager.getConfig().getEmergencyMeetings());
    }

    public int getMeetingsLeft() {
        return meetingsLeft;
    }

    public void setMeetingsLeft(int meetingsLeft) {
        this.meetingsLeft = meetingsLeft;
    }

    public abstract WinCondition getWinCondition();

    public abstract boolean canPerformTask(CrewmateTask task);
    public void performTask(CrewmateTask task) { }

    public abstract boolean canUseVent(Vent vent);
    public void useVent(Vent vent) { }

    public abstract boolean canInteractWithPlayer(Player target);
    public void interactWithPlayer(Player target) { }
}