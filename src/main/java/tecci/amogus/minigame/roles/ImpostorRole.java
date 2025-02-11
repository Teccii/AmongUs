package tecci.amogus.minigame.roles;

import org.bukkit.entity.Player;
import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.*;

public class ImpostorRole extends Role {
    public ImpostorRole(GameManager gameManager, Player player) {
        super(gameManager, player);
    }

    @Override
    public WinCondition getWinCondition() { return WinCondition.IMPOSTOR; }

    @Override
    public boolean canInteract(Interactable interactable) {
        return false;
        //TODO
    }

    @Override
    public boolean canInteractWithPlayer(Player target) {
        return !(this.gameManager.getPlayerManager().getRole(target) instanceof ImpostorRole);
    }

    @Override
    public void interactWithPlayer(Player target) {
        //kill them
    }
}
