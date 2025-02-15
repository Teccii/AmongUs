package tecci.amogus.gui;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Material;
import xyz.xenondevs.invui.gui.TabGui;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.controlitem.TabItem;

public class OptionsBookTabElement extends TabItem {
    private final int tabIndex;

    public OptionsBookTabElement(int tabIndex) {
        super(tabIndex);
        this.tabIndex = tabIndex;
    }

    @Override
    public ItemProvider getItemProvider(TabGui gui) {
        ItemBuilder itemBuilder = new ItemBuilder(Material.NAUTILUS_SHELL).addAllItemFlags();

        switch (tabIndex) {
            case 0:
                itemBuilder.setDisplayName(new ComponentBuilder("Impostors").color(ChatColor.WHITE).italic(false).build());
                break;
            case 1:
                itemBuilder.setDisplayName(new ComponentBuilder("Meetings").color(ChatColor.WHITE).italic(false).build());
                break;
            case 2:
                itemBuilder.setDisplayName(new ComponentBuilder("Tasks").color(ChatColor.WHITE).italic(false).build());
                break;
            case 3:
                itemBuilder.setDisplayName(new ComponentBuilder("Other Roles").color(ChatColor.WHITE).italic(false).build());
        }

        return itemBuilder;
    }
}
