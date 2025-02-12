package tecci.amogus.minigame.interactables;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import tecci.amogus.gui.EmergencyButtonItem;
import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.GuiInteractable;
import tecci.amogus.minigame.Role;
import tecci.amogus.util.GuiUtil;
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

        if (role.getMeetingsLeft() > 0) {
            return Gui.normal()
                .setStructure(
                    "G . . .",
                    ". X X X",
                    ". X X X",
                    ". X X X"
                )
                .addIngredient('X', new EmergencyButtonItem(gameManager))
                .addIngredient('G', new SimpleItem(GuiUtil.hideTooltip(new ItemBuilder(Material.NAUTILUS_SHELL))))
                .build();
        }

        return Gui.normal()
            .setStructure(
                "G . . .",
                ". X X X",
                ". X X X",
                ". X X X"
            )
            .addIngredient('X', new SimpleItem(GuiUtil.hideTooltip(new ItemBuilder(Material.NAUTILUS_SHELL))))
            .addIngredient('G', new SimpleItem(GuiUtil.hideTooltip(new ItemBuilder(Material.NAUTILUS_SHELL))))
            .build();
    }

    @Override
    public void interact(Player player) {
        Window.single()
                .setGui(getGui(player))
                .setTitle("Emergency Button")
                .setCloseable(true)
                .open(player);
    }
}
