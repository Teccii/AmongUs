package tecci.amogus.minigame.interactables;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import tecci.amogus.gui.EmergencyButtonElement;
import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.GuiInteractable;
import tecci.amogus.minigame.Role;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

public class EmergencyButton extends GuiInteractable {
    public EmergencyButton(GameManager gameManager, Location location) {
        super(gameManager, location);
    }

    @Override
    public Gui getGui(Player player) {
        Role role = gameManager.getPlayerManager().getRole(player);
        boolean emergencyButtonDisabled = gameManager.getSabotageManager().isEmergencyButtonDisabled();

        if (role != null && role.getMeetingsLeft() > 0 && emergencyButtonDisabled) {
            return Gui.normal().setStructure(
                    ". . . X X X . . .",
                    ". . . X X X . . .",
                    ". . . X X X . . ."
            ).addIngredient('X', new EmergencyButtonElement(gameManager, player)).build();
        } else {
            return Gui.normal().setStructure(
                    ". . . X X X . . .",
                    ". . . X X X . . .",
                    ". . . X X X . . ."
            ).addIngredient('X', new SimpleItem(new ItemBuilder(Material.NAUTILUS_SHELL)
                    .addAllItemFlags()
                    .setDisplayName(emergencyButtonDisabled ? "Emergency Meeting Unavailable During Crises" : "No Emergency Meetings Left"))
            ).build();
        }
    }

    @Override
    public String getTitle() {
        return "Emergency Button";
    }

    @Override
    public void closeHandler() { }
}
