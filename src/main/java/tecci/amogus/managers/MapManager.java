package tecci.amogus.managers;

import tecci.amogus.GameMap;

public class MapManager {
    private final GameManager gameManager;
    private GameMap gameMap = null;

    public MapManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }
}
