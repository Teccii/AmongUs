package tecci.amogus.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import tecci.amogus.items.CustomItem;
import tecci.amogus.managers.GameManager;
import tecci.amogus.managers.PlayerManager;
import tecci.amogus.minigame.GamePhase.GamePhaseType;
import tecci.amogus.minigame.Interactable;
import tecci.amogus.minigame.Role;
import tecci.amogus.minigame.phases.LobbyPhase;
import tecci.amogus.minigame.roles.LobbyRole;
import tecci.amogus.minigame.roles.SpectatorRole;

public class PlayerEventListener implements Listener {
    private final GameManager gameManager;

    public PlayerEventListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) {
            return;
        }

        event.setCancelled(true);

        Player player = (Player)event.getDamager();

        ItemStack heldItem = player.getEquipment().getItem(EquipmentSlot.HAND);
        CustomItem customItem = gameManager.getItemManager().getItem(heldItem);

        if (customItem != null) {
            customItem.onClickEntity(ClickType.LEFT, player, event.getEntity());
        }
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();

        ItemStack heldItem = player.getEquipment().getItem(EquipmentSlot.HAND);
        CustomItem customItem = gameManager.getItemManager().getItem(heldItem);

        if (customItem != null) {
            customItem.onClickEntity(ClickType.RIGHT, player, event.getRightClicked());
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        //Event triggers once for each hand, this prevents it from triggering twice.
        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        Player player = event.getPlayer();
        Action action = event.getAction();

        ItemStack heldItem = player.getEquipment().getItem(EquipmentSlot.HAND);
        CustomItem customItem = gameManager.getItemManager().getItem(heldItem);

        if (customItem != null) {
            if (action == Action.LEFT_CLICK_AIR) {
                customItem.onClick(ClickType.LEFT, player);
            } else if (action == Action.RIGHT_CLICK_AIR) {
                customItem.onClick(ClickType.RIGHT, player);
            }
        }

        if (action == Action.LEFT_CLICK_BLOCK || action == Action.RIGHT_CLICK_BLOCK) {
            Interactable interactable = gameManager.getMapManager().tryFindInteractable(event.getClickedBlock().getLocation());

            if (interactable != null && gameManager.getPlayerManager().getRole(player).canInteract(interactable)) {
                interactable.interact(player);
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        GamePhaseType gamePhase = gameManager.getCurrentPhase().getPhaseType();
        PlayerManager playerManager = gameManager.getPlayerManager();
        boolean receivedRole = false;

        if (gamePhase == GamePhaseType.LOBBY || gamePhase == GamePhaseType.STARTING) {
            playerManager.setRole(player, new LobbyRole(gameManager, player));
            receivedRole = true;
        }

        //i.e. the game is still going on
        if (!receivedRole && gamePhase != GamePhaseType.CLEAN_UP) {
            playerManager.setRole(player, new SpectatorRole(gameManager, player));
        }

        if (gamePhase == GamePhaseType.STARTING) {
            gameManager.setPhase(new LobbyPhase(gameManager));
        }

        if (!playerManager.hostExists()) {
            playerManager.tryFindNewHost();
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        GamePhaseType gamePhase = gameManager.getCurrentPhase().getPhaseType();
        PlayerManager playerManager = gameManager.getPlayerManager();
        Role role = playerManager.getRole(player);

        if (playerManager.isHost(player)) {
            playerManager.tryFindNewHost();
        }

        if (role != null) {
            playerManager.removeRole(player);

            if (role instanceof LobbyRole || role instanceof SpectatorRole) {
                return;
            }

            //TODO: Recheck win conditions
        }

        if (gamePhase == GamePhaseType.STARTING) {
            gameManager.setPhase(new LobbyPhase(gameManager));
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }
}
