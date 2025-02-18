package tecci.amogus.minigame.roles;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import tecci.amogus.managers.GameManager;
import tecci.amogus.managers.PlayerManager;
import tecci.amogus.minigame.Interactable;
import tecci.amogus.minigame.Role;
import tecci.amogus.minigame.WinCondition;

import java.util.List;
import java.util.stream.Collectors;

public class ImpostorRole extends Role {
    public ImpostorRole(GameManager gameManager, Player player) {
        super(gameManager, player);
    }

    @Override
    public WinCondition getWinCondition() { return WinCondition.IMPOSTOR; }

    @Override
    public boolean checkVictory() {
        PlayerManager playerManager = gameManager.getPlayerManager();
        List<Player> playersAlive = Bukkit.getOnlinePlayers()
                .stream()
                .filter(p -> !playerManager.isDead(p))
                .collect(Collectors.toUnmodifiableList());

        long impostorsAlive = playersAlive.stream().filter(p -> playerManager.getRole(p) instanceof ImpostorRole).count();
        long nonImpostorsAlive = playersAlive.stream().filter(p -> !(playerManager.getRole(p) instanceof ImpostorRole)).count();

        return nonImpostorsAlive <= impostorsAlive;
    }

    @Override
    public boolean requiresRecheck() {
        return false;
    }

    @Override
    public boolean isNonPlayingRole() {
        return false;
    }

    @Override
    public void setRoleItems() {
        //TODO
    }

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
