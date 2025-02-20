package tecci.amogus.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import tecci.amogus.minigame.Corpse;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CorpseManager {
    private final List<Corpse> activeCorpses = new ArrayList<>();
    private final GameManager gameManager;

    public CorpseManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public List<Corpse> getActiveCorpses() {
        return activeCorpses;
    }

    public void addCorpse(Player deadPlayer) {
        Corpse corpse = new Corpse(deadPlayer);
        activeCorpses.add(corpse);

        for (Player player : Bukkit.getOnlinePlayers()) {
            corpse.show(player);
        }
    }

    public void removeCorpse(Player deadPlayer) {
        for (Corpse corpse : activeCorpses) {
            if (corpse.getPlayerUuid().equals(deadPlayer.getUniqueId())) {
                for (UUID uuid : corpse.getSeeingPlayers()) {
                    corpse.hide(Bukkit.getPlayer(uuid));
                }

                activeCorpses.remove(corpse);
            }
        }
    }

    public void removeAllCorpses() {
        for (Corpse corpse : activeCorpses) {
            for (UUID uuid : corpse.getSeeingPlayers()) {
                corpse.hide(Bukkit.getPlayer(uuid));
            }
        }

        activeCorpses.clear();
    }
}
