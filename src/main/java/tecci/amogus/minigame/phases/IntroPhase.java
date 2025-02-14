package tecci.amogus.minigame.phases;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import tecci.amogus.managers.GameManager;
import tecci.amogus.managers.MapManager;
import tecci.amogus.managers.PlayerManager;
import tecci.amogus.minigame.GamePhase;
import tecci.amogus.minigame.roles.CrewmateRole;
import tecci.amogus.minigame.roles.ImpostorRole;
import tecci.amogus.minigame.roles.JesterRole;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IntroPhase extends GamePhase {
    public IntroPhase(GameManager gameManager) {
        super(gameManager);
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

        mapManager.changeMap(gameManager.getConfig().getSelectedMap());
        playerManager.teleportAllRadial(mapManager.getCurrentMap().getSpawnLocation(), 5.0);

        //distribute roles
        playerManager.clearRoles();

        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        Collections.shuffle(players);
        int playerCount = players.size();

        int impostorsLeft = gameManager.getConfig().getImpostorCount();

        if (impostorsLeft == 3 && playerCount < 9) {
            impostorsLeft = 2;
        }

        if (impostorsLeft == 2 && playerCount < 7) {
            impostorsLeft = 1;
        }

        int jestersLeft = 1;

        for (Player player : players) {
            if (impostorsLeft > 0) {
                playerManager.setRole(player, new ImpostorRole(gameManager, player));
                impostorsLeft--;
                continue;
            }

            if (jestersLeft > 0) {
                playerManager.setRole(player, new JesterRole(gameManager, player));
                jestersLeft--;
                continue;
            }

            playerManager.setRole(player, new CrewmateRole(gameManager, player));
        }

        //distribute tasks

        //multi-second task: blindness, send title to players showing their roles
    }

    @Override
    public void onEnd() {
        //get rid of blindness
    }
}
