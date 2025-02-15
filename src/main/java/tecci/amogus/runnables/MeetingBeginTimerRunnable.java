package tecci.amogus.runnables;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.GamePhase;
import tecci.amogus.minigame.phases.DiscussionPhase;

import java.util.stream.Collectors;

public class MeetingBeginTimerRunnable extends BukkitRunnable {
    private final GameManager gameManager;
    private int timer = 6;

    public MeetingBeginTimerRunnable(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        if (gameManager.getCurrentPhase().getPhaseType() == GamePhase.GamePhaseType.OVER) {
            this.cancel();
            return;
        }

        if (timer == 3) {
            String deadBodyList = gameManager.getMeetingManager().getNewDeadBodies().stream()
                    .map(uuid -> Bukkit.getPlayer(uuid).getName())
                    .collect(Collectors.joining(", "));

            for (Player player : Bukkit.getOnlinePlayers()) {
                String title = new TextComponent(ChatColor.RED + "Dead Bodies").toLegacyText();
                String subtitle = new TextComponent(ChatColor.WHITE + deadBodyList).toLegacyText();

                player.sendTitle(title, subtitle, 10, 50, 10);
            }
        }

        timer--;

        if (timer <= 0) {
            gameManager.setPhase(new DiscussionPhase(gameManager));
        }
    }
}
