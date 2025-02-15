package tecci.amogus.runnables;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;
import tecci.amogus.items.CustomItem;
import tecci.amogus.managers.ItemManager;

public class ItemTickRunnable extends BukkitRunnable {
    private final ItemManager itemManager;

    public ItemTickRunnable(ItemManager itemManager) {
        this.itemManager = itemManager;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerInventory inventory = player.getInventory();

            for (int i = 0; i < inventory.getSize(); i++) {
                ItemStack item = inventory.getItem(i);
                CustomItem customItem = itemManager.getItem(item);

                if (customItem != null) {
                    customItem.onTick(player);

                    inventory.setItem(i, customItem.getItemStack());
                }
            }
        }
    }
}
