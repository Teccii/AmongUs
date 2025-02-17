package tecci.amogus.runnables;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.GamePhase;
import tecci.amogus.minigame.phases.IntroPhase;

public class StartCountdownRunnable extends PhaseTimerRunnable {
    public StartCountdownRunnable(GameManager gameManager) {
        super(gameManager);
        timer = 5;
    }

    @Override
    protected GamePhase.GamePhaseType getPhaseType() {
        return GamePhase.GamePhaseType.STARTING;
    }

    @Override
    public void timerTick() {
        BaseComponent[] component = new ComponentBuilder()
                .append(new TextComponent(ChatColor.GREEN + "Starting in " + timer))
                .create();

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, component);
        }
    }

    @Override
    public void timerEnd() {
        gameManager.setPhase(new IntroPhase(gameManager));
    }
}
