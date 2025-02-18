package tecci.amogus.minigame.phases;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import tecci.amogus.managers.GameManager;
import tecci.amogus.managers.MapManager;
import tecci.amogus.managers.PlayerManager;
import tecci.amogus.minigame.GamePhase;
import tecci.amogus.minigame.WinCondition;
import tecci.amogus.runnables.GameOverTimerRunnable;

import java.util.List;
import java.util.Set;

public class GameOverPhase extends GamePhase {
    private final GameOverTimerRunnable task;
    private final Set<WinCondition> winConditions;
    private final List<Player> winners;

    public GameOverPhase(GameManager gameManager, Set<WinCondition> winConditions, List<Player> winners) {
        super(gameManager);
        this.winConditions = winConditions;
        this.winners = winners;

        task = new GameOverTimerRunnable(gameManager);
    }

    @Override
    public GamePhaseType getPhaseType() { return GamePhaseType.GAME_OVER; }

    @Override
    public boolean isValidTransition(GamePhaseType nextPhase) {
        return nextPhase == GamePhaseType.CLEAN_UP;
    }

    @Override
    public void onStart() {
        MapManager mapManager = gameManager.getMapManager();
        PlayerManager playerManager = gameManager.getPlayerManager();

        playerManager.teleportAllRadial(mapManager.getCurrentMap().getSpawnLocation(), 5.0);

        String victoryTitle = new TextComponent(ChatColor.AQUA + "Victory").toLegacyText();
        String defeatTitle = new TextComponent(ChatColor.RED + "Defeat").toLegacyText();

        Player[] winnerArray = winners.toArray(new Player[0]);

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (winners.contains(player)) {
                player.sendTitle(victoryTitle, null, 10, 40, 10);
            } else {
                player.sendTitle(defeatTitle, null, 10, 40, 10);

                gameManager.getGlowingManager().setGlowingEntities(player, winnerArray);
            }

            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10, 1, false, false));
        }

        task.runTaskTimer(gameManager.getPlugin(), 0, 20);
    }

    @Override
    public void onEnd() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.removePotionEffect(PotionEffectType.BLINDNESS);
        }
    }
}
