package tecci.amogus.minigame;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.phases.IntroPhase;

public class GameStartCountdownTask extends BukkitRunnable {
    private final GameManager gameManager;
    private int timer = 5;

    public GameStartCountdownTask(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        timer--;

        if (timer >= 0) {
            BaseComponent[] component = new ComponentBuilder()
                    .append(new TextComponent(ChatColor.GREEN + "Starting in " + timer))
                    .create();

            for (Player player : Bukkit.getOnlinePlayers()) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, component);
            }

            return;
        }

        gameManager.setPhase(new IntroPhase(gameManager));
    }
}
