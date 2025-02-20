package tecci.amogus.managers;

import org.bukkit.Location;
import tecci.amogus.minigame.GameMap;
import tecci.amogus.minigame.Interactable;
import tecci.amogus.minigame.TaskInteractable;
import tecci.amogus.minigame.interactables.EmergencyButton;
import tecci.amogus.minigame.interactables.OptionsBook;
import tecci.amogus.minigame.interactables.Vent;

import java.io.File;
import java.util.*;

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

        maps.put(MapId.LOBBY, new GameMap(mapsFolder, "Lobby", false, 0.0, 65.0, 0.0, world -> {
            HashSet<Interactable> interactables = new HashSet<>();

            interactables.add(new OptionsBook(gameManager, new Location(world, 4.0, 65.0, 0.0)));

            return interactables;
        }, world -> {
            List<TaskInteractable> taskInteractables = new ArrayList<>();

            return taskInteractables;
        }));

        maps.put(MapId.SKELD, new GameMap(mapsFolder, "Skeld", false, -28.0, 64.0, 46.0, world -> {
            HashSet<Interactable> interactables = new HashSet<>();

            interactables.add(new EmergencyButton(gameManager, new Location(world, -28.0, 65.0, 46.0)));

            Vent electricalVent = new Vent(gameManager, new Location(world, 5.0, 64.0, 15.0));
            Vent medBayVent = new Vent(gameManager, new Location(world, 9.0, 64.0, 25.0));
            Vent securityVent = new Vent(gameManager, new Location(world, 18.0, 64.0, 19.0));

            electricalVent.connectVents(medBayVent, securityVent);
            medBayVent.connectVents(electricalVent, securityVent);
            securityVent.connectVents(electricalVent, medBayVent);

            interactables.add(electricalVent);
            interactables.add(medBayVent);
            interactables.add(securityVent);

            Vent adminVent = new Vent(gameManager, new Location(world, -39.0, 64.0, 10.0));
            Vent cafeteriaVent = new Vent(gameManager, new Location(world, -45.0, 64.0, 41.0));
            Vent navHallwayVent = new Vent(gameManager, new Location(world, -65.0, 64.0, 21.0));

            adminVent.connectVents(cafeteriaVent, navHallwayVent);
            cafeteriaVent.connectVents(adminVent, navHallwayVent);
            navHallwayVent.connectVents(adminVent, cafeteriaVent);

            interactables.add(adminVent);
            interactables.add(cafeteriaVent);
            interactables.add(navHallwayVent);

            Vent weaponsVent = new Vent(gameManager, new Location(world, -62.0, 64.0, 57.0));
            Vent navTopVent = new Vent(gameManager, new Location(world, -83.0, 64.0, 31.0));
            Vent navBottomVent = new Vent(gameManager, new Location(world, -83.0, 64.0, 19.0));
            Vent shieldVent = new Vent(gameManager, new Location(world, -65.0, 64.0, -6.0));

            weaponsVent.connectVent(navTopVent);
            shieldVent.connectVent(navBottomVent);
            navTopVent.connectVent(weaponsVent);
            navBottomVent.connectVent(shieldVent);

            interactables.add(weaponsVent);
            interactables.add(shieldVent);
            interactables.add(navTopVent);
            interactables.add(navBottomVent);

            Vent reactorTopVent = new Vent(gameManager, new Location(world, 44.0, 64.0, 34.0));
            Vent reactorBottomVent = new Vent(gameManager, new Location(world, 44.0, 64.0, 15.0));
            Vent upperEngineVent = new Vent(gameManager, new Location(world, 24.0, 64.0, 51.0));
            Vent lowerEngineVent = new Vent(gameManager, new Location(world, 24.0, 64.0, -2.0));

            reactorTopVent.connectVent(upperEngineVent);
            reactorBottomVent.connectVent(lowerEngineVent);
            upperEngineVent.connectVent(reactorTopVent);
            lowerEngineVent.connectVent(reactorBottomVent);

            interactables.add(reactorTopVent);
            interactables.add(reactorBottomVent);
            interactables.add(upperEngineVent);
            interactables.add(lowerEngineVent);

            return interactables;
        }, world -> {
            List<TaskInteractable> taskInteractables = new ArrayList<>();

            return taskInteractables;
        }));
    }

    public Interactable tryFindInteractable(Location location) {
        GameMap map = getCurrentMap();

        if (map == null) {
            return null;
        }

        for (Interactable interactable : map.getActiveNonTaskInteractables()) {
            if (interactable.getLocation().equals(location)) {
                return interactable;
            }
        }

        return null;
    }

    public GameMap getCurrentMap() {
        return maps.get(currentMapId);
    }

    public MapId getCurrentMapId() {
        return currentMapId;
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

    public void onDisable() {
        for (GameMap map : maps.values()) {
            if (map.isLoaded()) {
                map.unload();
            }
        }
    }
}
