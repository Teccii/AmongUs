package tecci.amogus.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import tecci.amogus.GameMap;
import tecci.amogus.minigame.Interactable;
import tecci.amogus.minigame.interactables.OptionsBook;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class MapManager {
    public enum MapId {
        LOBBY,
        SKELD,
    }

    private final GameManager gameManager;
    private final Map<MapId, GameMap> maps = new HashMap<>();
    private MapId currentMapId = null;

    public MapManager(GameManager gameManager) {
        this.gameManager = gameManager;

        File mapsFolder = new File(gameManager.getPlugin().getDataFolder(), "gameMaps");
        if (!mapsFolder.exists()) {
            mapsFolder.mkdirs();
        }

        maps.put(MapId.LOBBY, new GameMap(mapsFolder, "Lobby", false, world -> {
            HashSet<Interactable> interactables = new HashSet<>();

            interactables.add(new OptionsBook(gameManager, new Location(world, 4.0, 65.0, 0.0)));

            return interactables;
        }));

        maps.put(MapId.SKELD, new GameMap(mapsFolder, "Skeld", false, set -> {
            HashSet<Interactable> interactables = new HashSet<>();

            return interactables;
        }));
    }

    public Interactable tryFindInteractable(Location location) {
        GameMap map = getCurrentMap();

        if (map == null) {
            return null;
        }

        for (Interactable interactable : map.getActiveInteractables()) {
            if (interactable.getLocation().equals(location)) {
                return interactable;
            }
        }

        return null;
    }

    public GameMap getCurrentMap() {
        return maps.get(currentMapId);
    }

    public void changeMap(MapId newMapId) {
        if (currentMapId == newMapId) {
            return;
        }

        if (currentMapId != null) {
            GameMap currentMap = maps.get(currentMapId);

            if (currentMap.isLoaded()) {
                currentMap.unload();
            }
        }

        currentMapId = newMapId;
        maps.get(currentMapId).load();
    }

    public void cleanUp() {
        for (GameMap map : maps.values()) {
            if (map.isLoaded()) {
                map.unload();
            }
        }
    }
}
