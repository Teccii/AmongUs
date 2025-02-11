package tecci.amogus.managers;

import org.bukkit.entity.Player;
import tecci.amogus.minigame.Role;
import java.util.*;

public class PlayerManager {
    private GameManager gameManager;

    private final Set<UUID> deadPlayers = new HashSet<>();
    private final Map<UUID, Role> playerRoles = new HashMap<>();
    private UUID hostId = UUID.randomUUID();

    public PlayerManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public Role getRole(Player player) {
        return playerRoles.get(player.getUniqueId());
    }

    public boolean isDead(Player player) {
        return deadPlayers.contains(player.getUniqueId());
    }

    public boolean isHost(Player player) {
        return hostId.equals(player.getUniqueId());
    }
}
