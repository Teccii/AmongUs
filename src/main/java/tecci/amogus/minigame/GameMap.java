package tecci.amogus.minigame;

import org.bukkit.*;
import tecci.amogus.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;

public class GameMap {
    private final File sourceWorldFolder;
    private final Function<World, Set<Interactable>> nonTaskInteractableProvider;
    private final Function<World,  List<TaskInteractable>> taskInteractableProvider;
    private final double spawnX, spawnY, spawnZ;
    private File activeWorldFolder;
    private Set<Interactable> activeNonTaskInteractables;
    private Map<TaskCategory, List<TaskInteractable>> activeTaskInteractables;
    private List<TaskType> commonTaskTypes;
    private World world;

    public GameMap(
            File worldFolder,
            String worldName,
            boolean loadOnInit,
            double spawnX,
            double spawnY,
            double spawnZ,
            Function<World, Set<Interactable>> nonTaskInteractableProvider,
            Function<World, List<TaskInteractable>> taskInteractableProvider
    ) {
        this.sourceWorldFolder = new File(worldFolder, worldName);
        this.nonTaskInteractableProvider = nonTaskInteractableProvider;
        this.taskInteractableProvider = taskInteractableProvider;
        this.spawnX = spawnX;
        this.spawnY = spawnY;
        this.spawnZ = spawnZ;

        if (loadOnInit) {
            this.load();
        }
    }

    public boolean load() {
        if (isLoaded()) {
            return true;
        }

        this.activeWorldFolder = new File(
                Bukkit.getWorldContainer().getParentFile(),
                sourceWorldFolder.getName() + "_active_" + System.currentTimeMillis()
        );

        try {
            FileUtil.copy(sourceWorldFolder, activeWorldFolder);
        } catch (IOException e) {
            Bukkit.getLogger().severe("Failed to load GameMap from source folder " + sourceWorldFolder.getName());
            e.printStackTrace();
            return false;
        }

        this.world = Bukkit.createWorld(new WorldCreator(activeWorldFolder.getName()));

        if (world != null) {
            this.activeNonTaskInteractables = nonTaskInteractableProvider.apply(world);
            populateTaskMap();

            world.setGameRule(GameRule.COMMAND_BLOCK_OUTPUT, false);
            world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
            world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
            world.setGameRule(GameRule.MOB_GRIEFING, false);
            world.setGameRule(GameRule.DO_MOB_LOOT, false);
            world.setGameRule(GameRule.DO_TILE_DROPS, false);
            world.setGameRule(GameRule.DO_ENTITY_DROPS, false);
            world.setGameRule(GameRule.DO_PATROL_SPAWNING, false);
            world.setGameRule(GameRule.DO_TRADER_SPAWNING, false);
            world.setGameRule(GameRule.DO_WARDEN_SPAWNING, false);
            world.setGameRule(GameRule.NATURAL_REGENERATION, false);
            world.setGameRule(GameRule.FALL_DAMAGE, false);
            world.setGameRule(GameRule.DO_FIRE_TICK, false);
            world.setGameRule(GameRule.KEEP_INVENTORY, true);
            world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);

            world.setAutoSave(false);
        }

        return isLoaded();
    }

    private void populateTaskMap() {
        List<TaskInteractable> taskInteractables = taskInteractableProvider.apply(world);
        List<TaskType> commonTaskTypes = new ArrayList<>();
        activeTaskInteractables = new HashMap<>();

        for (TaskInteractable taskInteractable : taskInteractables) {
            for (TaskCategory category : taskInteractable.getTaskCategories()) {
                activeTaskInteractables.computeIfAbsent(category, k -> new ArrayList<>()).add(taskInteractable);

                if (category == TaskCategory.COMMON && !commonTaskTypes.contains(taskInteractable.getTaskType())) {
                    commonTaskTypes.add(taskInteractable.getTaskType());
                }
            }
        }
    }

    public void unload() {
        if (world != null) {
            Bukkit.unloadWorld(world, true);
        }

        if (activeWorldFolder != null) {
            FileUtil.delete(activeWorldFolder);
        }

        world = null;
        activeWorldFolder = null;
        activeNonTaskInteractables = null;
        activeTaskInteractables = null;
    }

    public boolean restoreFromSource() {
        unload();

        return load();
    }

    public boolean isLoaded() {
        return getWorld() != null;
    }

    public Set<Interactable> getActiveNonTaskInteractables() {
        return activeNonTaskInteractables;
    }
    public Map<TaskCategory, List<TaskInteractable>> getActiveTaskInteractables() { return activeTaskInteractables; }
    public List<TaskType> getCommonTaskTypes() { return commonTaskTypes; }

    public World getWorld() {
        return world;
    }

    public Location getSpawnLocation() {
        return new Location(world, spawnX, spawnY, spawnZ);
    }
}
