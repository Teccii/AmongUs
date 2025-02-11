package tecci.amogus.minigame;

import org.bukkit.entity.Player;
import tecci.amogus.managers.GameManager;

public abstract class Interactable {
    protected final GameManager gameManager;

    public Interactable(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    // may need something like "interactable type"

    public abstract void interact(Player player);
}
