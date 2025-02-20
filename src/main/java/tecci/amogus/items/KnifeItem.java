package tecci.amogus.items;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import tecci.amogus.managers.GameManager;
import tecci.amogus.managers.PlayerManager;
import tecci.amogus.minigame.Role;
import tecci.amogus.minigame.roles.ImpostorRole;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;

public class KnifeItem extends CustomItem {
    private final GameManager gameManager;
    private final int maxCooldown;
    private int cooldownTicks;

    public KnifeItem(GameManager gameManager) {
        this.gameManager = gameManager;
        this.maxCooldown = (int)(gameManager.getConfig().getImpostorKillCooldownSeconds() * 20.0);
        cooldownTicks = maxCooldown;
    }

    @Override
    public void onTick(Player player) {
        if (cooldownTicks > 0) {
            cooldownTicks--;
        }
    }

    @Override
    protected ItemProvider getItemProvider() {
        if (cooldownTicks > 0) {
            return new ItemBuilder(Material.NAUTILUS_SHELL).addAllItemFlags().setDisplayName(String.format("Knife (%ds)", Math.ceilDiv(cooldownTicks, 20)));
        } else {
            return new ItemBuilder(Material.NAUTILUS_SHELL).addAllItemFlags().setDisplayName("Knife");
        }
    }

    @Override
    public void onClickEntity(ClickType clickType, Player player, Entity target) {
        if (clickType != ClickType.LEFT || cooldownTicks > 0 || !(target instanceof Player targetPlayer)) {
            return;
        }

        PlayerManager playerManager = gameManager.getPlayerManager();
        Role playerRole = playerManager.getRole(player);

        if (playerRole instanceof ImpostorRole && playerRole.canInteractWithPlayer(targetPlayer)) {
            playerRole.interactWithPlayer(targetPlayer);
            cooldownTicks = maxCooldown;
        }
    }
}
