package tecci.amogus.minigame.phases;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import tecci.amogus.items.CustomItem;
import tecci.amogus.items.VoteItem;
import tecci.amogus.managers.GameManager;
import tecci.amogus.managers.ItemManager;
import tecci.amogus.minigame.GamePhase;
import tecci.amogus.minigame.MeetingReason;
import tecci.amogus.runnables.MeetingBeginTimerRunnable;

import java.util.HashMap;
import java.util.Map;

public class MeetingBeginPhase extends GamePhase {
    private final MeetingBeginTimerRunnable task;
    private final Player meetingHost;
    private final MeetingReason reason;

    public MeetingBeginPhase(GameManager gameManager, Player meetingHost, MeetingReason reason) {
        super(gameManager);

        this.task = new MeetingBeginTimerRunnable(gameManager);
        this.meetingHost = meetingHost;
        this.reason = reason;
    }

    @Override
    public GamePhaseType getPhaseType() { return GamePhaseType.MEETING_BEGIN; }

    @Override
    public boolean isValidTransition(GamePhaseType nextPhase) {
        return nextPhase == GamePhaseType.DISCUSSION || nextPhase == GamePhaseType.OVER;
    }

    @Override
    public void onStart() {
        ItemManager itemManager = gameManager.getItemManager();
        gameManager.getMeetingManager().setMeetingHost(meetingHost);

        for (Player player : Bukkit.getOnlinePlayers()) {
            String title = new TextComponent(ChatColor.RED + reason.toString()).toLegacyText();

            player.sendTitle(title, null, 10, 40, 10);
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 6, 1, false, false));

            Map<Integer, CustomItem> meetingLoadout = new HashMap<>();
            meetingLoadout.put(4, new VoteItem(gameManager));

            itemManager.setLoadout(player, meetingLoadout);
        }

        gameManager.getPlayerManager().teleportAllRadial(gameManager.getMapManager().getCurrentMap().getSpawnLocation(), 5.0);

        task.runTaskTimer(gameManager.getPlugin(), 0L, 20L);
    }

    @Override
    public void onEnd() {
        task.cancel();

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.removePotionEffect(PotionEffectType.BLINDNESS);
        }
    }
}
