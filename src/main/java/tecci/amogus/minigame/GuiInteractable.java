package tecci.amogus.minigame;

import org.bukkit.entity.Player;
import tecci.amogus.managers.GameManager;
import xyz.xenondevs.invui.gui.Gui;

public abstract class GuiInteractable extends Interactable {
    public GuiInteractable(GameManager gameManager) {
        super(gameManager);
    }

    public abstract Gui getGui(Player player);
}
