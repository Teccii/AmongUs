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
import tecci.amogus.minigame.phases.EjectingPhase;

public class VotingTimerRunnable extends PhaseTimerRunnable {
    public VotingTimerRunnable(GameManager gameManager) {
        super(gameManager);
        timer = (int)gameManager.getConfig().getVotingTimeSeconds() + 3; //3 seconds to show players vote result
    }

    @Override
    protected GamePhase.GamePhaseType getPhaseType() {
        return GamePhase.GamePhaseType.VOTING;
    }

    @Override
    public void timerTick() {
        if (timer > 3) {
            BaseComponent[] component = new ComponentBuilder()
                    .append(new TextComponent(ChatColor.GOLD + "Voting: " + (timer - 3) + "s"))
                    .create();

            for (Player player : Bukkit.getOnlinePlayers()) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, component);
            }
            //show the timer somewhere (subtract 3 seconds from it)
        } else {
            if (gameManager.getMeetingManager().canVote()) {
                gameManager.getMeetingManager().setCanVote(false);
            }

            //show all the votes
        }
    }

    @Override
    public void timerEnd() {
        gameManager.setPhase(new EjectingPhase(gameManager, gameManager.getMeetingManager().getVoteResult()));
    }
}
