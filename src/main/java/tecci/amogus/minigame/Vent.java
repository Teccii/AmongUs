package tecci.amogus.minigame;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import tecci.amogus.managers.GameManager;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Vent {
    private final GameManager gameManager;
    private final Location location;
    private final Set<Vent> connectedVents = new HashSet<>();
    private final Set<UUID> playersInside = new HashSet<>();

    public Vent(Location location, GameManager gameManager) {
        this.gameManager = gameManager;
        this.location = location;
    }

    public Set<Vent> getConnectedVents() {
        return connectedVents;
    }

    public boolean isConnectedTo(Vent vent) {
        return connectedVents.contains(vent);
    }

    public void connectVent(Vent vent) {
        connectedVents.add(vent);
    }

    public void disconnectVent(Vent vent) {
        connectedVents.remove(vent);
    }

    public Set<UUID> getPlayersInside() {
        return playersInside;
    }

    public boolean isInside(Player player) {
        return playersInside.contains(player.getUniqueId());
    }

    public boolean isEmpty() {
        return playersInside.isEmpty();
    }

    public void addPlayer(Player player) {
        playersInside.add(player.getUniqueId());
    }

    public void removePlayer(Player player) {
        playersInside.remove(player.getUniqueId());
    }
}
