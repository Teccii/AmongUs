package tecci.amogus.minigame;

import org.bukkit.entity.Player;
import tecci.amogus.managers.GameManager;

public abstract class Role {
    protected final GameManager gameManager;
    protected final Player player;
    protected int meetingsLeft;
    protected boolean isDead;

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

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean isDead) {
        this.isDead = isDead;
    }

    public abstract WinCondition getWinCondition();
    public abstract boolean canInteract(Interactable interactable);

    public abstract boolean canInteractWithPlayer(Player target);
    public void interactWithPlayer(Player target) { }
}