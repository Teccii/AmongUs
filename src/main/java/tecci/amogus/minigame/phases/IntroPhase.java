package tecci.amogus.minigame.phases;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import tecci.amogus.managers.GameManager;
import tecci.amogus.managers.MapManager;
import tecci.amogus.managers.PlayerManager;
import tecci.amogus.minigame.*;
import tecci.amogus.minigame.roles.CrewmateRole;
import tecci.amogus.minigame.roles.ImpostorRole;
import tecci.amogus.minigame.roles.JesterRole;
import tecci.amogus.util.RandomUtil;

import java.util.*;

public class IntroPhase extends GamePhase {
    public IntroPhase(GameManager gameManager) {
        super(gameManager);
    }

    @Override
    public GamePhaseType getPhaseType() { return GamePhaseType.INTRO; }

    @Override
    public boolean isValidTransition(GamePhaseType nextPhase) {
        return nextPhase == GamePhaseType.ACTIVE;
    }

    @Override
    public void onStart() {
        PlayerManager playerManager = gameManager.getPlayerManager();
        MapManager mapManager = gameManager.getMapManager();

        mapManager.changeMap(gameManager.getConfig().getSelectedMap());
        playerManager.teleportAllRadial(mapManager.getCurrentMap().getSpawnLocation(), 5.0);

        //distribute roles
        playerManager.clearRoles();

        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        Collections.shuffle(players);
        int playerCount = players.size();

        int impostorsLeft = gameManager.getConfig().getImpostorCount();

        if (impostorsLeft == 3 && playerCount < 9) {
            impostorsLeft = 2;
        }

        if (impostorsLeft == 2 && playerCount < 7) {
            impostorsLeft = 1;
        }

        int jestersLeft = 1;

        for (Player player : players) {
            if (impostorsLeft > 0) {
                playerManager.setRole(player, new ImpostorRole(gameManager, player));
                impostorsLeft--;
                continue;
            }

            if (jestersLeft > 0) {
                playerManager.setRole(player, new JesterRole(gameManager, player));
                jestersLeft--;
                continue;
            }

            playerManager.setRole(player, new CrewmateRole(gameManager, player));
        }

        //distribute tasks
        int commonTaskCount = gameManager.getConfig().getCommonTaskCount();
        int longTaskCount = gameManager.getConfig().getLongTaskCount();
        int shortTaskCount = gameManager.getConfig().getShortTaskCount();

        Map<TaskCategory, List<TaskInteractable>> taskInteractables = mapManager.getCurrentMap().getActiveTaskInteractables();
        List<TaskInteractable> commonTasks = taskInteractables.get(TaskCategory.COMMON);
        List<TaskInteractable> longTasks = taskInteractables.get(TaskCategory.LONG);
        List<TaskInteractable> shortTasks = taskInteractables.get(TaskCategory.SHORT);

        //if only one common task decide which one it is

        //flip a coin if impostor

        for (Player player : Bukkit.getOnlinePlayers()) {
            Role role = playerManager.getRole(player);
            role.clearTasks();

            for (int i = 0; i < longTaskCount; i++) {
                role.addTask(longTasks.get(RandomUtil.rng.nextInt(longTasks.size())).createTask());
            }

            for (int i = 0; i < shortTaskCount; i++) {
                role.addTask(shortTasks.get(RandomUtil.rng.nextInt(shortTasks.size())).createTask());
            }
        }

        //multi-second task: blindness, send title to players showing their roles
    }

    @Override
    public void onEnd() {
        //get rid of blindness
    }
}
