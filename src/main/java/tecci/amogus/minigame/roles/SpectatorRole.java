package tecci.amogus.minigame.roles;

import org.bukkit.entity.Player;
import tecci.amogus.items.TeleportItem;
import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.Interactable;
import tecci.amogus.minigame.Role;
import tecci.amogus.minigame.WinCondition;

public class SpectatorRole extends Role {
    public SpectatorRole(GameManager gameManager, Player player) {
        super(gameManager, player);
    }

    @Override
    public boolean isDead() {
        return true;
    }

    @Override
    public WinCondition getWinCondition() {
        return null;
    }

    @Override
    public boolean checkVictory() {
        return false;
    }

    @Override
    public boolean isTeamRole() {
        return false;
    }

    @Override
    public boolean isNonPlayingRole() {
        return true;
    }

    @Override
    public void setRoleItems() {
        roleItems.put(0, new TeleportItem(gameManager));
    }

    @Override
    public boolean canInteract(Interactable interactable) { return false; }

    @Override
    public boolean canInteractWithPlayer(Player target) {
        return false;
    }
}
