package tecci.amogus.items;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.window.Window;

public abstract class GuiItem extends CustomItem {
    public abstract Gui getGui(Player player);

    @Override
    public void onClick(ClickType clickType, Player player) {
        Window.single()
                .setGui(getGui(player))
                .setTitle("Teleport")
                .open(player);
    }
}
