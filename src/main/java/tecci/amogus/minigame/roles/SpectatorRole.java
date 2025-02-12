package tecci.amogus.minigame.roles;

import org.bukkit.entity.Player;
import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.Interactable;
import tecci.amogus.minigame.Role;
import tecci.amogus.minigame.WinCondition;

public class SpectatorRole extends Role {
    public SpectatorRole(GameManager gameManager, Player player) {
        super(gameManager, player);
    }

    @Override
    public WinCondition getWinCondition() {
        return null;
    }

    @Override
    public boolean canInteract(Interactable interactable) {
        return true;
        //TODO
    }

    @Override
    public boolean canInteractWithPlayer(Player target) {
        return false;
    }
}
