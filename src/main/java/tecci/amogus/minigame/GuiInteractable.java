package tecci.amogus.minigame;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import tecci.amogus.managers.GameManager;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.window.Window;

public abstract class GuiInteractable extends Interactable {
    public GuiInteractable(GameManager gameManager, Location location) {
        super(gameManager, location);
    }

    public abstract Gui getGui(Player player);
    public abstract String getTitle();
    public abstract void closeHandler();

    @Override
    public void interact(Player player) {
        Window.single()
                .setGui(getGui(player))
                .setTitle(getTitle())
                .addCloseHandler(this::closeHandler)
                .open(player);
    }
}
