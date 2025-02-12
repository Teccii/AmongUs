package tecci.amogus.minigame.interactables;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import tecci.amogus.gui.EmergencyButtonItem;
import tecci.amogus.util.GuiUtil;
import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.GuiInteractable;
import tecci.amogus.minigame.Role;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

public class EmergencyButton extends GuiInteractable {
    public EmergencyButton(GameManager gameManager) {
        super(gameManager);
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
                .addIngredient('G', new SimpleItem(GuiUtil.hideTooltip(new ItemBuilder(Material.NAUTILUS_SHELL).setCustomModelData(3))))
                .build();
        }

        return Gui.normal()
            .setStructure(
                "G . . .",
                ". X X X",
                ". X X X",
                ". X X X"
            )
            .addIngredient('X', new SimpleItem(GuiUtil.hideTooltip(new ItemBuilder(Material.NAUTILUS_SHELL).setCustomModelData(2))))
            .addIngredient('G', new SimpleItem(GuiUtil.hideTooltip(new ItemBuilder(Material.NAUTILUS_SHELL).setCustomModelData(4))))
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
