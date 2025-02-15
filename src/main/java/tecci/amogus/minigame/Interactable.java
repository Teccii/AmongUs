package tecci.amogus.minigame;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import tecci.amogus.managers.GameManager;

public abstract class Interactable {
    protected final GameManager gameManager;
    protected final Location location;

    public Interactable(GameManager gameManager, Location location) {
        this.gameManager = gameManager;
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public abstract void interact(Player player);
}
