package tecci.amogus.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import tecci.amogus.items.CustomItem;
import tecci.amogus.managers.GameManager;
import tecci.amogus.managers.PlayerManager;
import tecci.amogus.minigame.GamePhase.GamePhaseType;
import tecci.amogus.minigame.Interactable;
import tecci.amogus.minigame.Role;
import tecci.amogus.minigame.roles.LobbyRole;
import tecci.amogus.minigame.roles.SpectatorRole;

public class PlayerEventListener implements Listener {
    private final GameManager gameManager;

    public PlayerEventListener(GameManager gameManager) {
        this.gameManager = gameManager;
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

        if (heldItem != null) {
            CustomItem customItem = gameManager.getItemManager().getItem(heldItem);

            if (customItem != null) {
                if (action == Action.LEFT_CLICK_AIR) {
                    customItem.onClick(ClickType.LEFT, player, event);
                } else if (action == Action.RIGHT_CLICK_AIR) {
                    customItem.onClick(ClickType.RIGHT, player, event);
                }
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

        if (!receivedRole && gamePhase == GamePhaseType.LOBBY || gamePhase == GamePhaseType.STARTING) {
            playerManager.setRole(player, new LobbyRole(gameManager, player));
            receivedRole = true;
        }

        //i.e. the game is still going on
        if (!receivedRole && gamePhase != GamePhaseType.CLEAN_UP) {
            playerManager.setRole(player, new SpectatorRole(gameManager, player));
        }

        if (!playerManager.hostExists()) {
            playerManager.tryFindNewHost();
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerManager playerManager = gameManager.getPlayerManager();
        Role role = playerManager.getRole(player);

        if (playerManager.isHost(player)) {
            playerManager.tryFindNewHost();
        }

        if (role != null) {
            //Recheck win conditions if game going on

            playerManager.removeRole(player);
        }
    }
}
