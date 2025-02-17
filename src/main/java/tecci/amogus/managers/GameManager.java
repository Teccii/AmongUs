package tecci.amogus.managers;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.Bukkit;
import tecci.amogus.AmongUsPlugin;
import tecci.amogus.adapters.HeldItemAdapter;
import tecci.amogus.minigame.*;
import tecci.amogus.minigame.phases.CleanUpPhase;
import tecci.amogus.minigame.phases.OverPhase;
import tecci.amogus.minigame.roles.*;

import java.util.*;

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
            return;
        }

        if (currentPhase != null) {
            currentPhase.onEnd();
        }

        currentPhase = nextPhase;
        currentPhase.onStart();
    }

    public boolean checkWinConditions() {
        Collection<Role> roles = playerManager.getRoleMap().values();

        //Jester Win Condition
        for (JesterRole jester : roles.stream().filter(JesterRole.class::isInstance).map(JesterRole.class::cast).toList()) {
            if (jester.isDead() && jester.getDeathReason() == DeathReason.EJECTED) {
                submitWin(WinCondition.JESTER);
                return true;
            }
        }

        //Impostor Win Condition
        //might need to be redone if something like jackal gets added
        long impostorCount = roles.stream().filter(ImpostorRole.class::isInstance).count();
        long nonImpostorCount = roles.stream().filter(r -> !(r instanceof ImpostorRole)).count();

        if (impostorCount >= nonImpostorCount) {
            submitWin(WinCondition.IMPOSTOR);
            return true;
        }

        //Crewmate Win Conditions
        long aliveImpostorCount = roles.stream().filter(r -> r instanceof ImpostorRole && !r.isDead()).count();

        if (aliveImpostorCount == 0) {
            submitWin(WinCondition.CREWMATE);
            return true;
        }

        if (roles.stream().filter(CrewmateRole.class::isInstance).allMatch(Role::hasCompletedAllTasks)) {
            submitWin(WinCondition.CREWMATE);
            return true;
        }

        return false;
    }

    private void submitWin(WinCondition winCondition) {
        setPhase(new OverPhase(this, winCondition));
    }

    public void onDisable() {
        mapManager.onDisable();
    }
}
