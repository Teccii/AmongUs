package tecci.amogus.managers;

import com.google.common.collect.ImmutableList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import tecci.amogus.minigame.Role;
import java.util.*;

public class PlayerManager {
    private GameManager gameManager;

    private final Set<UUID> deadPlayers = new HashSet<>();
    private final Map<UUID, Role> playerRoles = new HashMap<>();
    private UUID hostId = null;
    private boolean hostExists = false;

    public PlayerManager(GameManager gameManager) {
        this.gameManager = gameManager;

        findHost();
    }

    public Role getRole(Player player) {
        return playerRoles.get(player.getUniqueId());
    }

    public boolean isDead(Player player) {
        return deadPlayers.contains(player.getUniqueId());
    }

    public boolean isHost(Player player) {
        return hostExists() && hostId.equals(player.getUniqueId());
    }

    public boolean hostExists() { return hostExists; }

    public void findHost() {
        List<Player> players = ImmutableList.copyOf(Bukkit.getOnlinePlayers());

        if (players.isEmpty()) {
            hostId = null;
            hostExists = false;
        } else {
            hostId = players.getFirst().getUniqueId();
            hostExists = true;
        }
    }
}
