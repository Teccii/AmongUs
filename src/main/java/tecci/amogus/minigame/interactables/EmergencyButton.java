package tecci.amogus.minigame.interactables;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.GuiInteractable;
import tecci.amogus.minigame.Role;
import xyz.xenondevs.invui.gui.Gui;

public class EmergencyButton extends GuiInteractable {
    public EmergencyButton(GameManager gameManager, Location location) {
        super(gameManager, location);
    }

    @Override
    public Gui getGui(Player player) {
        Role role = gameManager.getPlayerManager().getRole(player);

        if (role != null && role.getMeetingsLeft() > 0) {

        }
    }

    @Override
    public void interact(Player player) {

    }
}
