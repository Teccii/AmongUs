package tecci.amogus.minigame.roles;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import tecci.amogus.items.KnifeItem;
import tecci.amogus.items.ReportItem;
import tecci.amogus.items.TeleportItem;
import tecci.amogus.managers.GameManager;
import tecci.amogus.managers.PlayerManager;
import tecci.amogus.minigame.*;
import tecci.amogus.minigame.interactables.EmergencyButton;
import tecci.amogus.minigame.interactables.Vent;

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
    public boolean isTeamRole() {
        return false;
    }

    @Override
    public boolean isNonPlayingRole() {
        return false;
    }

    @Override
    public void setRoleItems() {
        if (isDead()) {
            roleItems.put(1, new TeleportItem(gameManager));
        } else {
            roleItems.put(0, new KnifeItem(gameManager));
            roleItems.put(2, new ReportItem(gameManager));
        }
    }

    @Override
    public boolean canInteract(Interactable interactable) {
        if (interactable instanceof TaskInteractable) {
            return false;
        }

        return interactable instanceof EmergencyButton || interactable instanceof Vent;
    }

    @Override
    public boolean canInteractWithPlayer(Player target) {
        return !(this.gameManager.getPlayerManager().getRole(target) instanceof ImpostorRole);
    }

    @Override
    public void interactWithPlayer(Player target) {
        Role role = gameManager.getPlayerManager().getRole(target);
        role.setDead(true, DeathReason.KILLED);

        String title = new TextComponent(ChatColor.RED + "You were killed by " + target.getName()).toLegacyText();
        target.sendTitle(title, null, 10, 40, 10);
    }
}
