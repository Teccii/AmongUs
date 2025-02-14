package tecci.amogus.adapters;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import tecci.amogus.managers.GameManager;
import tecci.amogus.minigame.GamePhase.GamePhaseType;

public class HeldItemAdapter extends PacketAdapter {
    private final GameManager gameManager;

    public HeldItemAdapter(GameManager gameManager) {
        super(gameManager.getPlugin(), ListenerPriority.NORMAL, PacketType.Play.Server.ENTITY_EQUIPMENT);
        this.gameManager = gameManager;
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        GamePhaseType gamePhase = gameManager.getCurrentPhase().getPhaseType();

        if (gamePhase == GamePhaseType.LOBBY || gamePhase == GamePhaseType.STARTING || gamePhase == GamePhaseType.CLEAN_UP) {
            return;
        }

        PacketContainer packet = event.getPacket();
        packet.getItemModifier().write(0, new ItemStack(Material.AIR));
        event.setPacket(packet);
    }
}
