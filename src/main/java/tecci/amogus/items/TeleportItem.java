package tecci.amogus.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import tecci.amogus.gui.TeleportElement;
import tecci.amogus.managers.GameManager;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;

import java.util.List;
import java.util.stream.Collectors;

public class TeleportItem extends GuiItem {
    private final GameManager gameManager;

    public TeleportItem(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public Gui getGui(Player player) {
        List<Item> content = Bukkit.getOnlinePlayers()
                .stream()
                .filter(p -> p.getUniqueId() != player.getUniqueId() && !gameManager.getPlayerManager().isDead(p))
                .map(TeleportElement::new)
                .collect(Collectors.toList());

        return PagedGui.items()
                .setStructure(
                        ". . . . . . . . .",
                        ". x x x x x x x .",
                        ". x x x x x x x .",
                        ". x x x x x x x .",
                        ". x x x x x x x .",
                        ". . . . . . . . ."
                )
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                .setContent(content)
                .build();
    }

    @Override
    protected ItemProvider getItemProvider() {
        return new ItemBuilder(Material.NAUTILUS_SHELL).addAllItemFlags().setDisplayName("Teleport");
    }
}
