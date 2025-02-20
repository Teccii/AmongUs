package tecci.amogus.minigame.roles;

import org.bukkit.entity.Player;
import tecci.amogus.items.KnifeItem;
import tecci.amogus.items.ReportItem;
import tecci.amogus.items.TeleportItem;
import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.*;
import tecci.amogus.minigame.interactables.EmergencyButton;
import tecci.amogus.minigame.interactables.Vent;

public class JesterRole extends Role {
    public JesterRole(GameManager gameManager, Player player) {
        super(gameManager, player);
    }

    @Override
    public WinCondition getWinCondition() { return WinCondition.JESTER; }

    @Override
    public boolean checkVictory() {
        return isDead() && getDeathReason() == DeathReason.EJECTED;
    }

    @Override
    public boolean isTeamRole() {
        return true;
    }

    @Override
    public boolean isNonPlayingRole() {
        return false;
    }

    @Override
    public void setRoleItems() {
        if (isDead()) {
            roleItems.put(0, new TeleportItem(gameManager));
        } else {
            roleItems.put(0, new ReportItem(gameManager));
        }
    }

    @Override
    public boolean canInteract(Interactable interactable) {
        if (interactable instanceof TaskInteractable) {
            return false;
        }

        return interactable instanceof EmergencyButton;

    }


    @Override
    public boolean canInteractWithPlayer(Player target) { return false; }
}
