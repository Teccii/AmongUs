package tecci.amogus.items;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import tecci.amogus.managers.ItemManager;
import xyz.xenondevs.invui.item.ItemProvider;

import java.util.UUID;

public abstract class CustomItem {
    private final UUID uuid = UUID.randomUUID();

    public UUID getUuid() {
        return uuid;
    }

    protected abstract ItemProvider getItemProvider();
    public ItemStack getItemStack() {
        ItemStack item = getItemProvider().get();
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            PersistentDataContainer data = meta.getPersistentDataContainer();

            data.set(ItemManager.UUID_MOST, PersistentDataType.LONG, uuid.getMostSignificantBits());
            data.set(ItemManager.UUID_LEAST, PersistentDataType.LONG, uuid.getLeastSignificantBits());

            item.setItemMeta(meta);
        }

        return item;
    }

    public void onTick(Player player) { }

    public void onClick(ClickType clickType, Player player) { }
    public void onClickEntity(ClickType clickType, Player player, Entity target) { }
}
