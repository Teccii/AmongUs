package tecci.amogus.runnables;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.GamePhase;
import tecci.amogus.minigame.phases.DiscussionPhase;

import java.util.stream.Collectors;

public class MeetingBeginTimerRunnable extends PhaseTimerRunnable {
    public MeetingBeginTimerRunnable(GameManager gameManager) {
        super(gameManager);
        timer = 6;
    }

    @Override
    protected GamePhase.GamePhaseType getPhaseType() {
        return GamePhase.GamePhaseType.MEETING_BEGIN;
    }

    @Override
    public void timerTick() {
        if (timer == 3) {
            String deadBodyList = gameManager.getMeetingManager().getNewDeadBodies().stream()
                    .map(uuid -> Bukkit.getPlayer(uuid).getName())
                    .collect(Collectors.joining(", "));

            for (Player player : Bukkit.getOnlinePlayers()) {
                String title = new TextComponent(ChatColor.RED + "Dead Bodies").toLegacyText();
                String subtitle = new TextComponent(ChatColor.WHITE + deadBodyList).toLegacyText();

                player.sendTitle(title, subtitle, 10, 40, 10);
            }
        }
    }

    @Override
    public void timerEnd() {
        gameManager.setPhase(new DiscussionPhase(gameManager));
    }
}