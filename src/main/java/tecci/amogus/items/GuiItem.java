package tecci.amogus.items;

import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.Gui;

public abstract class GuiItem extends CustomItem {
    public abstract Gui getGui(Player player);
}
