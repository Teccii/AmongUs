package tecci.amogus.minigame;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import tecci.amogus.AmongUsPlugin;
import tecci.amogus.items.CustomItem;
import tecci.amogus.managers.CorpseManager;
import tecci.amogus.managers.GameManager;
import tecci.amogus.managers.PlayerManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Role {
    protected final GameManager gameManager;
    protected final Map<Integer, CustomItem> roleItems = new HashMap<>();
    protected final List<Task> tasks = new ArrayList<>();
    protected final Player player;
    protected int meetingsLeft;
    protected DeathReason deathReason;
    protected boolean isDead;

    public Role(GameManager gameManager, Player player) {
        this.gameManager = gameManager;
        this.player = player;

        setMeetingsLeft(gameManager.getConfig().getEmergencyMeetings());
        setRoleItems();
    }

    public int getMeetingsLeft() {
        return meetingsLeft;
    }

    public void setMeetingsLeft(int meetingsLeft) {
        this.meetingsLeft = meetingsLeft;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean isDead, DeathReason deathReason) {
        setDead(isDead, deathReason, true);
    }

    public void setDead(boolean isDead, DeathReason deathReason, boolean updateCorpseManager) {
        this.isDead = isDead;
        this.deathReason = deathReason;

        AmongUsPlugin plugin = gameManager.getPlugin();
        PlayerManager playerManager = gameManager.getPlayerManager();

        if (isDead) {
            playerManager.getGhostTeam().addEntry(player.getName());
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
        } else {
            playerManager.getAliveTeam().addEntry(player.getName());
            player.removePotionEffect(PotionEffectType.INVISIBILITY);
        }

        for (Player otherPlayer : Bukkit.getOnlinePlayers()) {
            if (otherPlayer.getUniqueId().equals(player.getUniqueId())) {
                continue;
            }

            if (!otherPlayer.isDead()) {
                if (isDead) {
                    otherPlayer.hideEntity(plugin, player);
                } else {
                    otherPlayer.showEntity(plugin, player);
                }
            }
        }

        if (updateCorpseManager) {
            CorpseManager corpseManager = gameManager.getCorpseManager();
            if (isDead) {
                corpseManager.addCorpse(player);
            } else {
                corpseManager.removeCorpse(player);
            }
        }
    }

    public DeathReason getDeathReason() {
        return deathReason;
    }

    public Task getTask(TaskInteractable interactable) {
        for (Task task : tasks) {
            if (task.getInteractable().equals(interactable)) {
                return task;
            }
        }

        return null;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public boolean hasCompletedAllTasks() {
        for (Task task : tasks) {
            if (!task.isCompleted()) {
                return false;
            }
        }

        return true;
    }

    public void clearTasks() {
        tasks.clear();
    }

    public abstract WinCondition getWinCondition();
    public abstract boolean isTeamRole();
    public abstract boolean checkVictory();
    public abstract boolean isNonPlayingRole();

    public abstract void setRoleItems();
    public Map<Integer, CustomItem> getRoleItems() {
        return roleItems;
    }

    public abstract boolean canInteract(Interactable interactable);

    public abstract boolean canInteractWithPlayer(Player target);
    public void interactWithPlayer(Player target) { }
}