package tecci.amogus.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import tecci.amogus.minigame.Task;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

import java.util.function.Consumer;

public class FlickyItem extends AbstractItem {
    private final Consumer<Task> taskSubmit;
    private final Task task;

    public FlickyItem(Consumer<Task> taskSubmit, Task task) {
        this.taskSubmit = taskSubmit;
        this.task = task;
    }

    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(Material.NAUTILUS_SHELL).setDisplayName("Flicky");
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        taskSubmit.accept(task);
    }
}
