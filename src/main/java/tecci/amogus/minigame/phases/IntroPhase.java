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
import tecci.amogus.minigame.roles.CrewmateRole;
import tecci.amogus.minigame.roles.ImpostorRole;
import tecci.amogus.minigame.roles.JesterRole;
import tecci.amogus.runnables.IntroTimerRunnable;
import tecci.amogus.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

        Player[] playerList = Bukkit.getOnlinePlayers().toArray(new Player[0]);
        int playerCount = playerList.length;

        //distribute roles
        playerManager.clearRoles();

        int impostorsLeft = config.getImpostorCount();

        if (impostorsLeft == 3 && playerCount < 9) {
            impostorsLeft--;
        }

        if (impostorsLeft == 2 && playerCount < 7) {
            impostorsLeft--;
        }

        while (impostorsLeft > 0) {
            Player player = playerList[RandomUtil.rng.nextInt(playerList.length)];

            if (playerManager.getRole(player) == null) {
                playerManager.setRole(player, new ImpostorRole(gameManager, player));
                impostorsLeft--;
            }
        }

        int jestersLeft = (config.getJesterEnabled() && RandomUtil.probability((double)config.getJesterChance() / 10.0)) ? 1 : 0;

        while (jestersLeft > 0) {
            Player player = playerList[RandomUtil.rng.nextInt(playerList.length)];

            if (playerManager.getRole(player) == null) {
                playerManager.setRole(player, new JesterRole(gameManager, player));
                jestersLeft--;
            }
        }

        String title = new TextComponent(ChatColor.WHITE + "Your role is").toLegacyText();
        for (Player player : playerList) {
            Role role = playerManager.getRole(player);

            if (role == null || role.isNonPlayingRole()) {
                role = new CrewmateRole(gameManager, player);
                playerManager.setRole(player, role);
            }

            //give them blindness and show their role while we're here ig
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

            if (role == null || role.isNonPlayingRole()) {
                continue;
            }

            role.clearTasks();

            List<TaskType> tasksAdded = new ArrayList<>(commonTaskCount + longTaskCount + shortTaskCount);
            boolean isCrewmate = role instanceof CrewmateRole;
            int whileLoops = 0;

            while (tasksAdded.size() < commonTaskCount && whileLoops < 100) {
                TaskInteractable task = commonTasks.get(RandomUtil.rng.nextInt(commonTasks.size()));
                TaskType taskType = task.getTaskType();

                //if crewmate, check if it's a common task we want, else don't check
                //regardless of that, check if we've already handed out a task of this type
                if ((!isCrewmate || crewmateCommonTasks.contains(taskType)) && !tasksAdded.contains(taskType)) {
                    tasksAdded.add(taskType);
                    role.addTask(task.createTask());
                }

                whileLoops++;
            }

            whileLoops = 0;
            while (tasksAdded.size() < commonTaskCount + longTaskCount && whileLoops < 100) {
                TaskInteractable task = longTasks.get(RandomUtil.rng.nextInt(longTasks.size()));
                TaskType taskType = task.getTaskType();

                if (!tasksAdded.contains(taskType)) {
                    tasksAdded.add(taskType);
                    role.addTask(task.createTask());
                }

                whileLoops++;
            }

            whileLoops = 0;
            while (tasksAdded.size() < commonTaskCount + longTaskCount + shortTaskCount && whileLoops < 100) {
                TaskInteractable task = shortTasks.get(RandomUtil.rng.nextInt(shortTasks.size()));
                TaskType taskType = task.getTaskType();

                if (!tasksAdded.contains(taskType)) {
                    tasksAdded.add(taskType);
                    role.addTask(task.createTask());
                }

                whileLoops++;
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
