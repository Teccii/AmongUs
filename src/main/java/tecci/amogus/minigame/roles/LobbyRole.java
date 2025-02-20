package tecci.amogus.minigame.roles;

import org.bukkit.entity.Player;
import tecci.amogus.items.StartItem;
import tecci.amogus.items.TeleportItem;
import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.Interactable;
import tecci.amogus.minigame.Role;
import tecci.amogus.minigame.WinCondition;
import tecci.amogus.minigame.interactables.OptionsBook;

public class LobbyRole extends Role {
    public LobbyRole(GameManager gameManager, Player player) {
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
        if (gameManager.getPlayerManager().isHost(player)) {
            roleItems.put(4, new StartItem(gameManager));
        }
    }

    @Override
    public boolean canInteract(Interactable interactable) {
        if (interactable instanceof OptionsBook) {
            return gameManager.getPlayerManager().isHost(player);
        }

        return false;
    }

    @Override
    public boolean canInteractWithPlayer(Player target) { return false; }
}
