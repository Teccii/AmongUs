package tecci.amogus.managers;

import com.google.common.collect.ImmutableList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import tecci.amogus.minigame.Role;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PlayerManager {
    private GameManager gameManager;

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

    public void setRole(Player player, Role role) {
        playerRoles.put(player.getUniqueId(), role);
    }

    public void removeRole(Player player) {
        playerRoles.remove(player.getUniqueId());
    }

    public boolean isDead(Player player) {
        return playerRoles.get(player.getUniqueId()).isDead();
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
