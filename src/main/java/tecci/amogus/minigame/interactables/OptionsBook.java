package tecci.amogus.minigame.interactables;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.GuiInteractable;
import xyz.xenondevs.invui.gui.Gui;

public class OptionsBook extends GuiInteractable {
    public OptionsBook(GameManager gameManager, Location location) {
        super(gameManager, location);
    }

    @Override
    public Gui getGui(Player player) {
        return null;
    }

    @Override
    public void interact(Player player) {
        Bukkit.broadcastMessage("red is sus");
    }
}
