package tecci.amogus.items;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.GamePhase.GamePhaseType;
import tecci.amogus.minigame.phases.StartingPhase;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;

public class StartItem extends CustomItem {
    private final GameManager gameManager;

    public StartItem(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    protected ItemProvider getItemProvider() {
        return new ItemBuilder(Material.NAUTILUS_SHELL).addAllItemFlags().setDisplayName("Start");
    }

    @Override
    public void onClick(ClickType clickType, Player player) {
        int playerCount = Bukkit.getOnlinePlayers().size();

        if (playerCount < 4) {
            BaseComponent[] component = new ComponentBuilder()
                    .append(new TextComponent(ChatColor.RED + "You need at least 4 players to start the game."))
                    .create();

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, component);
        } else if (playerCount > 15) {
            BaseComponent[] component = new ComponentBuilder()
                    .append(new TextComponent(ChatColor.RED + "The maximum player limit is 15. Reduce the number of players before starting."))
                    .create();

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, component);
        }

        if (gameManager.getPlayerManager().isHost(player) && gameManager.getCurrentPhase().getPhaseType() == GamePhaseType.LOBBY) {
            gameManager.setPhase(new StartingPhase(gameManager));
        }
    }
}
