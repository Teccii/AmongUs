package tecci.amogus.runnables;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import tecci.amogus.managers.GameManager;
import tecci.amogus.managers.PlayerManager;
import tecci.amogus.minigame.DeathReason;
import tecci.amogus.minigame.GamePhase;
import tecci.amogus.minigame.Role;
import tecci.amogus.minigame.phases.ActivePhase;
import tecci.amogus.minigame.roles.ImpostorRole;

import java.util.UUID;

public class PlayerEjectedTimerRunnable extends PhaseTimerRunnable {
    private final Player ejectedPlayer;
    private final boolean confirmEject;

    public PlayerEjectedTimerRunnable(GameManager gameManager, Player ejectedPlayer) {
        super(gameManager);

        this.ejectedPlayer = ejectedPlayer;
        confirmEject = gameManager.getConfig().getConfirmEjects();
        timer = 8;
    }

    @Override
    protected GamePhase.GamePhaseType getPhaseType() {
        return GamePhase.GamePhaseType.EJECTING;
    }

    @Override
    public void timerTick() {
        if (timer == 7) {
            PlayerManager playerManager = gameManager.getPlayerManager();
            UUID uuid = ejectedPlayer.getUniqueId();

            long alivePlayersLeft = Bukkit.getOnlinePlayers()
                    .stream()
                    .filter(p -> !playerManager.isDead(p) && p.getUniqueId() != uuid)
                    .count();

            String title = new TextComponent(ChatColor.WHITE + ejectedPlayer.getName() + " was ejected").toLegacyText();
            String subtitle = new TextComponent(ChatColor.WHITE + Long.toString(alivePlayersLeft) + " players remain").toLegacyText();

            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendTitle(title, subtitle, 10, 40, 10);
            }
        }

        if (timer > 3) {
            ejectedPlayer.damage(ejectedPlayer.getAttribute(Attribute.MAX_HEALTH).getValue() / 3.0);
        }

        if (timer == 3) {
            ejectedPlayer.setHealth(0.0);

            PlayerManager playerManager = gameManager.getPlayerManager();
            Role role = playerManager.getRole(ejectedPlayer);
            role.setDead(true, DeathReason.EJECTED, false);

            if (confirmEject) {
                boolean wasImposter = role instanceof ImpostorRole;

                String title = new TextComponent(ChatColor.WHITE + ejectedPlayer.getName() + (wasImposter ? "was an Impostor" : "was not an Impostor")).toLegacyText();

                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendTitle(title, null, 10, 40, 10);
                }
            }
        }
    }

    @Override
    public void timerEnd() {
        if (gameManager.checkVictory()) {
            return;
        }

        gameManager.setPhase(new ActivePhase(gameManager));
    }
}
