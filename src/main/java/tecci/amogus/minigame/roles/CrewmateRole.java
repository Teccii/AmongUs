package tecci.amogus.minigame.roles;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import tecci.amogus.managers.GameManager;
import tecci.amogus.managers.PlayerManager;
import tecci.amogus.minigame.Interactable;
import tecci.amogus.minigame.Role;
import tecci.amogus.minigame.WinCondition;

public class CrewmateRole extends Role {
    public CrewmateRole(GameManager gameManager, Player player) {
        super(gameManager, player);
    }

    @Override
    public WinCondition getWinCondition() { return WinCondition.CREWMATE; }

    @Override
    public boolean checkVictory() {
        PlayerManager playerManager = gameManager.getPlayerManager();

        boolean tasksCompleted = true;
        boolean impostorsDead = true;

        //task victory
        for (Player p : Bukkit.getOnlinePlayers()) {
            Role role = playerManager.getRole(p);

            if (role instanceof CrewmateRole && !role.hasCompletedAllTasks()) {
                tasksCompleted = false;
            }

            if (role instanceof ImpostorRole && !role.isDead()) {
                impostorsDead = false;
            }
        }

        return tasksCompleted || impostorsDead;
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
    public boolean canInteractWithPlayer(Player target) { return false; }
}
