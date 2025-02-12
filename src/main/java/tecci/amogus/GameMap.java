package tecci.amogus;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import tecci.amogus.minigame.Interactable;
import tecci.amogus.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class GameMap {
    private final File sourceWorldFolder;
    private final Set<Interactable> interactables;
    private File activeWorldFolder;
    private World world;

    public GameMap(File worldFolder, String worldName, boolean loadOnInit, Function<Set<Interactable>, Set<Interactable>> addInteractables) {
        this.sourceWorldFolder = new File(worldFolder, worldName);
        this.interactables = addInteractables.apply(new HashSet<>());

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
            world.setAutoSave(false);
        }

        return isLoaded();
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
    }

    public boolean restoreFromSource() {
        unload();

        return load();
    }

    public boolean isLoaded() {
        return getWorld() != null;
    }

    public World getWorld() {
        return world;
    }
}
