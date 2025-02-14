package tecci.amogus.managers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import tecci.amogus.items.CustomItem;
import tecci.amogus.items.ItemTickTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ItemManager {
    public static NamespacedKey UUID_MOST;
    public static NamespacedKey UUID_LEAST;

    private final GameManager gameManager;
    private final Map<UUID, CustomItem> registeredItems = new HashMap<>();
    private final ItemTickTask tickTask;

    public ItemManager(GameManager gameManager) {
        this.gameManager = gameManager;

        tickTask = new ItemTickTask(this);
        tickTask.runTaskTimer(gameManager.getPlugin(), 0, 1);
    }

    public void giveItem(Player player, CustomItem item) {
        registerIfAbsent(item);

        player.getInventory().addItem(item.getItemStack());
    }

    public boolean removeItem(Player player, CustomItem item) {
        PlayerInventory inventory = player.getInventory();
        UUID itemUuid = player.getUniqueId();

        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack stack = inventory.getItem(i);

            if (stack == null || !stack.hasItemMeta()) {
                continue;
            }

            ItemMeta meta = stack.getItemMeta();
            PersistentDataContainer data = meta.getPersistentDataContainer();

            if (data.has(UUID_MOST, PersistentDataType.LONG) && data.has(UUID_LEAST, PersistentDataType.LONG)) {
                long most = data.get(ItemManager.UUID_MOST, PersistentDataType.LONG);
                long least = data.get(ItemManager.UUID_LEAST, PersistentDataType.LONG);

                if (itemUuid.equals(new UUID(most, least))) {
                    inventory.setItem(i, null);
                    unregisterItem(item);
                    return true;
                }
            }
        }

        return false;
    }

    public void setItem(Player player, int slotIndex, CustomItem item) {
        registerIfAbsent(item);

        player.getInventory().setItem(slotIndex, item.getItemStack());
    }

    public void clearInventory(Player player) {
        PlayerInventory inventory = player.getInventory();

        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack item = inventory.getItem(i);
            CustomItem customItem = getItem(item);

            if (customItem != null) {
                unregisterItem(customItem);
            }
        }

        inventory.clear();
    }

    public CustomItem getItem(UUID uuid) {
        return registeredItems.get(uuid);
    }

    public CustomItem getItem(ItemStack item) {
        if (item == null || !item.hasItemMeta()) {
            return null;
        }

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();

        if (data.has(ItemManager.UUID_MOST, PersistentDataType.LONG) && data.has(ItemManager.UUID_LEAST, PersistentDataType.LONG)) {
            long most = data.get(ItemManager.UUID_MOST, PersistentDataType.LONG);
            long least = data.get(ItemManager.UUID_LEAST, PersistentDataType.LONG);
            UUID itemUuid = new UUID(most, least);

            return getItem(itemUuid);
        }

        return null;
    }

    public boolean isRegistered(CustomItem item) {
        return registeredItems.containsKey(item.getUuid());
    }

    public void registerItem(CustomItem item) {
        registeredItems.put(item.getUuid(), item);
    }

    public void registerIfAbsent(CustomItem item) {
        if (!isRegistered(item)) {
            registerItem(item);
        }
    }

    public void unregisterItem(CustomItem item) {
        registeredItems.remove(item.getUuid());
    }

    public static void initialize(Plugin plugin) {
        UUID_MOST = new NamespacedKey(plugin, "CUSTOM_ITEM_UUID_MOST");
        UUID_LEAST = new NamespacedKey(plugin, "CUSTOM_ITEM_UUID_LEAST");
    }
}
