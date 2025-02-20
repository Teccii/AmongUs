package tecci.amogus.minigame.roles;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import tecci.amogus.items.ReportItem;
import tecci.amogus.items.TeleportItem;
import tecci.amogus.managers.GameManager;
import tecci.amogus.managers.PlayerManager;
import tecci.amogus.minigame.Interactable;
import tecci.amogus.minigame.Role;
import tecci.amogus.minigame.TaskInteractable;
import tecci.amogus.minigame.WinCondition;
import tecci.amogus.minigame.interactables.EmergencyButton;

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

        for (Player player : Bukkit.getOnlinePlayers()) {
            Role role = playerManager.getRole(player);

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
            roleItems.put(0, new TeleportItem(gameManager));
        } else {
            roleItems.put(0, new ReportItem(gameManager));
        }
    }

    @Override
    public boolean canInteract(Interactable interactable) {
        if (interactable instanceof TaskInteractable taskInteractable) {
            return !gameManager.getMeetingManager().isMeetingActive() && getTask(taskInteractable) != null;
        }

        return interactable instanceof EmergencyButton;
    }

    @Override
    public boolean canInteractWithPlayer(Player target) { return false; }
}
