package tecci.amogus.minigame.roles;

import org.bukkit.entity.Player;
import tecci.amogus.items.StartItem;
import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.Interactable;
import tecci.amogus.minigame.Role;
import tecci.amogus.minigame.WinCondition;

public class LobbyRole extends Role {
    public LobbyRole(GameManager gameManager, Player player) {
        super(gameManager, player);
    }

    @Override
    public WinCondition getWinCondition() { return null; }

    @Override
    public void setRoleItems() {
        if (gameManager.getPlayerManager().isHost(player)) {
            roleItems.put(4, new StartItem(gameManager));
        }
    }

    @Override
    public boolean canInteract(Interactable interactable) {
        return true;
    }

    @Override
    public boolean canInteractWithPlayer(Player target) {
        return false;
        //TODO ?
    }
}
