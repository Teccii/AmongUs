package tecci.amogus.minigame.tasks;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import tecci.amogus.gui.EmergencyButtonItem;
import tecci.amogus.gui.ItemUtil;
import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.CrewmateTask;
import tecci.amogus.minigame.Role;
import tecci.amogus.minigame.TaskType;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

public class EmergencyButtonTask extends CrewmateTask {
    public EmergencyButtonTask(GameManager gameManager) {
        super(gameManager);
    }

    @Override
    public TaskType getTaskType() {
        return TaskType.EMERGENCY;
    }

    @Override
    public Gui getGui(Role role) {
        if (role.getMeetingsLeft() > 0) {
            return Gui.normal()
                .setStructure(
                    "G . . .",
                    ". X X X",
                    ". X X X",
                    ". X X X"
                )
                .addIngredient('X', new EmergencyButtonItem())
                .addIngredient('G', new SimpleItem(ItemUtil.hideTooltip(new ItemBuilder(Material.NAUTILUS_SHELL).setCustomModelData(3))))
                .build();
        }

        return Gui.normal()
            .setStructure(
                "G . . .",
                ". X X X",
                ". X X X",
                ". X X X"
            )
            .addIngredient('X', new SimpleItem(ItemUtil.hideTooltip(new ItemBuilder(Material.NAUTILUS_SHELL).setCustomModelData(2))))
            .addIngredient('G', new SimpleItem(ItemUtil.hideTooltip(new ItemBuilder(Material.NAUTILUS_SHELL).setCustomModelData(4))))
            .build();
    }

    @Override
    public void perform(Player player, Role role) {
        Window.single()
            .setGui(getGui(role))
            .setTitle("Emergency Button")
            .setCloseable(true)
            .open(player);
    }
}
