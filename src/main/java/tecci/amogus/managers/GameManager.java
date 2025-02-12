package tecci.amogus.managers;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import tecci.amogus.AmongUsPlugin;
import tecci.amogus.minigame.GamePhase;
import tecci.amogus.minigame.MinigameConfig;
import tecci.amogus.minigame.phases.CleanUpPhase;

public class GameManager {
    private final AmongUsPlugin plugin;
    private MinigameConfig config;
    private GamePhase currentPhase;

    //Other Managers
    private final MapManager mapManager;
    private final GlowingManager glowingManager;
    private final PlayerManager playerManager;
    private final ProtocolManager protocolManager;

    public GameManager(AmongUsPlugin plugin) {
        this.plugin = plugin;
        config = new MinigameConfig();

        mapManager = new MapManager(this);
        glowingManager = new GlowingManager(this);
        playerManager = new PlayerManager(this);
        protocolManager = ProtocolLibrary.getProtocolManager();

        setPhase(new CleanUpPhase(this));
    }

    public AmongUsPlugin getPlugin() { return plugin; }

    public MinigameConfig getConfig() { return config; }
    public void setConfig(MinigameConfig config) {
        this.config = config;
    }

    public MapManager getMapManager() { return mapManager; }
    public GlowingManager getGlowingManager() { return glowingManager; }
    public PlayerManager getPlayerManager() { return playerManager; }
    public ProtocolManager getProtocolManager() { return protocolManager; }

    public GamePhase getCurrentPhase() { return currentPhase; }
    public void setPhase(GamePhase nextPhase) {
        if (currentPhase != null && !currentPhase.isValidTransition(nextPhase.getPhaseType())) {
            return;
        }

        if (currentPhase != null) {
            currentPhase.onEnd();
        }

        currentPhase = nextPhase;
        currentPhase.onStart();
    }

    public void cleanUp() {
        mapManager.cleanUp();
    }
}
