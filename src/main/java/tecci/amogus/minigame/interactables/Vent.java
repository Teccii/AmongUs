package tecci.amogus.minigame.interactables;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.Interactable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Vent extends Interactable {
    private final Set<Vent> connectedVents = new HashSet<>();
    private final Set<UUID> playersInside = new HashSet<>();

    public Vent(GameManager gameManager, Location location) {
        super(gameManager, location);
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

    public boolean isPlayerInVent(Player player) {
        return playersInside.contains(player.getUniqueId());
    }

    public boolean isEmpty() {
        return connectedVents.isEmpty();
    }

    public void addPlayer(Player player) {
        playersInside.add(player.getUniqueId());
    }

    public void removePlayer(Player player) { playersInside.remove(player.getUniqueId()); }

    @Override
    public void interact(Player player) {
        //put player inside vent and stuff
    }
}
