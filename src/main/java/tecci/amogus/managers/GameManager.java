package tecci.amogus.managers;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import tecci.amogus.AmongUsPlugin;
import tecci.amogus.adapters.HeldItemAdapter;
import tecci.amogus.minigame.GamePhase;
import tecci.amogus.minigame.MinigameConfig;
import tecci.amogus.minigame.Role;
import tecci.amogus.minigame.WinCondition;
import tecci.amogus.minigame.phases.CleanUpPhase;
import tecci.amogus.minigame.phases.GameOverPhase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameManager {
    private final AmongUsPlugin plugin;
    private MinigameConfig config;
    private GamePhase currentPhase;

    //Other Managers
    private final ItemManager itemManager;
    private final MapManager mapManager;
    private final MeetingManager meetingManager;
    private final GlowingManager glowingManager;
    private final PlayerManager playerManager;
    private final ProtocolManager protocolManager;

    public GameManager(AmongUsPlugin plugin) {
        this.plugin = plugin;
        config = new MinigameConfig();

        itemManager = new ItemManager(this);
        mapManager = new MapManager(this);
        meetingManager = new MeetingManager(this);
        glowingManager = new GlowingManager(this);
        playerManager = new PlayerManager(this);
        protocolManager = ProtocolLibrary.getProtocolManager();

        protocolManager.addPacketListener(new HeldItemAdapter(this));

        setPhase(new CleanUpPhase(this));
    }

    public AmongUsPlugin getPlugin() { return plugin; }

    public MinigameConfig getConfig() { return config; }
    public void setConfig(MinigameConfig config) {
        this.config = config;
    }

    public ItemManager getItemManager() { return itemManager; }
    public MapManager getMapManager() { return mapManager; }
    public MeetingManager getMeetingManager() { return meetingManager; }
    public GlowingManager getGlowingManager() { return glowingManager; }
    public PlayerManager getPlayerManager() { return playerManager; }
    public ProtocolManager getProtocolManager() { return protocolManager; }

    public GamePhase getCurrentPhase() { return currentPhase; }
    public void setPhase(GamePhase nextPhase) {
        if (currentPhase != null && !currentPhase.isValidTransition(nextPhase.getPhaseType())) {
            Bukkit.getLogger().warning(String.format("%s -> %s is not a valid phase transition.", currentPhase.getPhaseType(), nextPhase.getPhaseType()));
            return;
        }

        if (currentPhase != null) {
            currentPhase.onEnd();
        }

        currentPhase = nextPhase;
        currentPhase.onStart();
    }

    public boolean checkVictory() {
        Set<WinCondition> winConditions = new HashSet<>();
        List<Player> winners = new ArrayList<>();

        for (Player player : Bukkit.getOnlinePlayers()) {
            Role role = playerManager.getRole(player);

            if (role != null && !role.isNonPlayingRole()) {
                WinCondition winCondition = role.getWinCondition();

                if ((!winConditions.contains(winCondition) || role.requiresRecheck()) && role.checkVictory()) {
                    winConditions.add(winCondition);
                    winners.add(player);
                } else if (winConditions.contains(winCondition) && !role.requiresRecheck()) {
                    winners.add(player);
                }
            }
        }

        boolean isEmpty = winners.isEmpty();

        if (!isEmpty) {
            setPhase(new GameOverPhase(this, winConditions, winners));
        }

        return isEmpty;
    }

    public void onDisable() {
        mapManager.onDisable();
    }
}
