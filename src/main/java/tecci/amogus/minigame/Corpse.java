package tecci.amogus.minigame;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tecci.amogus.util.RandomUtil;

import java.util.*;

public class Corpse {
    private final Set<UUID> seeingPlayers = new HashSet<>();
    private final WrappedGameProfile gameProfile;
    private final CorpsePacketLoader packetLoader;
    private final ItemStack[] armorContents;
    private final Location location;
    private final String name;
    private final UUID playerUuid;
    private final UUID uuid;
    private final int entityId;

    public Corpse(Player player) {
        this.location = player.getLocation();
        this.armorContents = player.getInventory().getArmorContents();
        this.playerUuid = player.getUniqueId();
        this.name = player.getName();

        this.entityId = RandomUtil.rng.nextInt();
        this.uuid = UUID.randomUUID();

        this.gameProfile = new WrappedGameProfile(this.uuid, this.name);
        gameProfile.getProperties().get("textures").forEach(p -> this.gameProfile.getProperties().put("textures", p));

        this.packetLoader = new CorpsePacketLoader(this);
    }

    public void show(Player player) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

        protocolManager.sendServerPacket(player, packetLoader.getPlayerInfoPacket());
        protocolManager.sendServerPacket(player, packetLoader.getSpawnPacket());
        protocolManager.sendServerPacket(player, packetLoader.getMetadataPacket());

        if (armorContents != null && (armorContents[0] != null || armorContents[1] != null || armorContents[2] != null || armorContents[3] != null)) {
            protocolManager.sendServerPacket(player, packetLoader.getEquipmentPacket());
        }

        seeingPlayers.add(player.getUniqueId());
    }

    public void hide(Player player) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.sendServerPacket(player, packetLoader.getDestroyPacket());

        seeingPlayers.remove(player.getUniqueId());
    }

    public Set<UUID> getSeeingPlayers() {
        return seeingPlayers;
    }

    public boolean isVisibleTo(Player player) {
        return seeingPlayers.contains(player.getUniqueId());
    }

    public int getEntityId() {
        return entityId;
    }

    public UUID getPlayerUuid() {
        return playerUuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public ItemStack[] getArmorContents() {
        return armorContents;
    }

    public WrappedGameProfile getGameProfile() {
        return gameProfile;
    }
}
