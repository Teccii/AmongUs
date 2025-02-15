package tecci.amogus.minigame.phases;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import tecci.amogus.managers.GameManager;
import tecci.amogus.managers.MapManager;
import tecci.amogus.managers.PlayerManager;
import tecci.amogus.minigame.*;
import tecci.amogus.minigame.roles.*;
import tecci.amogus.runnables.IntroTimerRunnable;
import tecci.amogus.util.RandomUtil;

import java.util.*;

public class IntroPhase extends GamePhase {
    private final IntroTimerRunnable task;

    public IntroPhase(GameManager gameManager) {
        super(gameManager);

        task = new IntroTimerRunnable(gameManager);
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
        MinigameConfig config = gameManager.getConfig();

        mapManager.changeMap(config.getSelectedMap());
        playerManager.teleportAllRadial(mapManager.getCurrentMap().getSpawnLocation(), 5.0);

        Player[] playerList = new Player[Bukkit.getOnlinePlayers().size()];
        playerList = Bukkit.getOnlinePlayers().toArray(playerList);

        //distribute roles
        playerManager.clearRoles();

        int impostorsLeft = config.getImpostorCount();
        int jestersLeft = config.getJesterCount();

        while (impostorsLeft > 0) {
            Player player = playerList[RandomUtil.rng.nextInt(playerList.length)];

            if (playerManager.getRole(player) == null) {
                playerManager.setRole(player, new ImpostorRole(gameManager, player));
                impostorsLeft--;
            }
        }

        while (jestersLeft > 0) {
            Player player = playerList[RandomUtil.rng.nextInt(playerList.length)];

            if (playerManager.getRole(player) == null) {
                playerManager.setRole(player, new JesterRole(gameManager, player));
                jestersLeft--;
            }
        }

        for (Player player : playerList) {
            Role role = playerManager.getRole(player);

            if (role == null || role instanceof LobbyRole || role instanceof SpectatorRole) {
                role = new CrewmateRole(gameManager, player);
                playerManager.setRole(player, role);
            }

            //give them blindness and show their role while we're here ig
            String title = new TextComponent(ChatColor.WHITE + "Your role is").toLegacyText();
            String subtitle = new TextComponent(switch (role) {
                case CrewmateRole ignored -> ChatColor.AQUA + "Crewmate";
                case ImpostorRole ignored -> ChatColor.RED + "Impostor";
                case JesterRole ignored  -> ChatColor.LIGHT_PURPLE + "Jester";
                default -> ChatColor.RED + "Placeholder";
            }).toLegacyText();

            player.sendTitle(title, subtitle, 10, 70, 20);
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 5, 1, false, false));
        }

        //distribute tasks
        Map<TaskCategory, List<TaskInteractable>> taskInteractables = mapManager.getCurrentMap().getActiveTaskInteractables();
        int commonTaskCount = config.getCommonTaskCount();
        int longTaskCount = config.getLongTaskCount();
        int shortTaskCount = config.getShortTaskCount();

        //decide crewmate common tasks
        List<TaskType> mapCommonTaskTypes = mapManager.getCurrentMap().getCommonTaskTypes();
        List<TaskType> crewmateCommonTasks = new ArrayList<>(commonTaskCount);

        for (int i = 0; i < commonTaskCount; i++) {
            crewmateCommonTasks.add(mapCommonTaskTypes.get(RandomUtil.rng.nextInt(mapCommonTaskTypes.size())));
        }

        List<TaskInteractable> commonTasks = taskInteractables.get(TaskCategory.COMMON);
        List<TaskInteractable> longTasks = taskInteractables.get(TaskCategory.LONG);
        List<TaskInteractable> shortTasks = taskInteractables.get(TaskCategory.SHORT);

        for (Player player : playerList) {
            Role role = playerManager.getRole(player);

            if (role == null || role instanceof LobbyRole || role instanceof SpectatorRole) {
                continue;
            }

            role.clearTasks();

            List<TaskType> tasksAdded = new ArrayList<>(commonTaskCount + longTaskCount + shortTaskCount);
            boolean isCrewmate = role instanceof CrewmateRole;

            while (tasksAdded.size() < commonTaskCount) {
                TaskInteractable task = commonTasks.get(RandomUtil.rng.nextInt(commonTasks.size()));
                TaskType taskType = task.getTaskType();

                //if crewmate, check if it's a common task we want, else don't check
                //regardless of that, check if we've already handed out a task of this type
                if ((!isCrewmate || crewmateCommonTasks.contains(taskType)) && !tasksAdded.contains(taskType)) {
                    tasksAdded.add(taskType);
                    role.addTask(task.createTask());
                }
            }

            while (tasksAdded.size() < commonTaskCount + longTaskCount) {
                TaskInteractable task = longTasks.get(RandomUtil.rng.nextInt(longTasks.size()));
                TaskType taskType = task.getTaskType();

                if (!tasksAdded.contains(taskType)) {
                    tasksAdded.add(taskType);
                    role.addTask(task.createTask());
                }
            }

            while (tasksAdded.size() < commonTaskCount + longTaskCount + shortTaskCount) {
                TaskInteractable task = shortTasks.get(RandomUtil.rng.nextInt(shortTasks.size()));
                TaskType taskType = task.getTaskType();

                if (!tasksAdded.contains(taskType)) {
                    tasksAdded.add(taskType);
                    role.addTask(task.createTask());
                }
            }
        }

        task.runTaskTimer(gameManager.getPlugin(), 0, 20);
    }

    @Override
    public void onEnd() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.removePotionEffect(PotionEffectType.BLINDNESS);
        }

        task.cancel();
    }
}
