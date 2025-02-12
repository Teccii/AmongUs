package tecci.amogus.managers;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BukkitConverters;
import com.comphenix.protocol.wrappers.Pair;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import com.comphenix.protocol.wrappers.WrappedDataValue;
import com.comphenix.protocol.wrappers.WrappedDataWatcher.Registry;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import tecci.amogus.util.LocationUtil;

import java.util.*;

public class GlowingManager {
    private GameManager gameManager;

    private final Map<UUID, GlowingSet<Location>> glowingBlocks = new HashMap<>();
    private final Map<UUID, GlowingSet<UUID>> glowingEntities = new HashMap<>();

    public GlowingManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void setGlowingBlock(Player receiver, Location location, BlockData blockData) {
        Location loc = LocationUtil.normalize(location);
        UUID uuid = receiver.getUniqueId();

        var blockSet = this.glowingBlocks.get(uuid);

        if (blockSet != null && blockSet.has(loc)) {
            return;
        }

        if (blockSet == null) {
            blockSet = new GlowingSet<>();
        }

        ProtocolManager protocolManager = gameManager.getProtocolManager();
        int entityId = new Random().nextInt();

        PacketContainer spawnPacket = new PacketContainer(PacketType.Play.Server.SPAWN_ENTITY);
        spawnPacket.getIntegers().write(0, entityId);
        spawnPacket.getUUIDs().write(0, UUID.randomUUID());
        spawnPacket.getEntityTypeModifier().write(0, EntityType.BLOCK_DISPLAY);
        spawnPacket.getDoubles().write(0, loc.getX());
        spawnPacket.getDoubles().write(1, loc.getY());
        spawnPacket.getDoubles().write(2, loc.getZ());

        protocolManager.sendServerPacket(receiver, spawnPacket);

        WrappedBlockData wrappedBlockData = WrappedBlockData.createData(blockData);
        List<WrappedDataValue> dataValues = List.of(
                new WrappedDataValue(0, Registry.get(Byte.class), (byte)0x40),
                new WrappedDataValue(23, Registry.getBlockDataSerializer(false), BukkitConverters.getWrappedBlockDataConverter().getGeneric(wrappedBlockData))
        );

        PacketContainer metadataPacket = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
        metadataPacket.getIntegers().write(0, entityId);
        metadataPacket.getDataValueCollectionModifier().write(0, dataValues);

        protocolManager.sendServerPacket(receiver, metadataPacket);

        blockSet.add(loc, entityId);

        this.glowingBlocks.put(uuid, blockSet);
    }

    public void setGlowingBlocks(Player receiver, Pair<Location, BlockData>[] blocks) {
        var uuid = receiver.getUniqueId();
        var blockSet = this.glowingBlocks.get(uuid);

        if (blockSet == null) {
            blockSet = new GlowingSet<>();
        }

        for (Pair<Location, BlockData> pair : blocks) {
            Location loc = LocationUtil.normalize(pair.getFirst());

            if (blockSet.has(loc)) {
                continue;
            }

            WrappedBlockData wrappedBlockData = WrappedBlockData.createData(pair.getSecond());
            ProtocolManager protocolManager = gameManager.getProtocolManager();
            int entityId = new Random().nextInt();

            PacketContainer spawnPacket = new PacketContainer(PacketType.Play.Server.SPAWN_ENTITY);
            spawnPacket.getIntegers().write(0, entityId);
            spawnPacket.getUUIDs().write(0, UUID.randomUUID());
            spawnPacket.getEntityTypeModifier().write(0, EntityType.BLOCK_DISPLAY);
            spawnPacket.getDoubles().write(0, loc.getX());
            spawnPacket.getDoubles().write(1, loc.getY());
            spawnPacket.getDoubles().write(2, loc.getZ());

            protocolManager.sendServerPacket(receiver, spawnPacket);

            List<WrappedDataValue> dataValues = List.of(
                    new WrappedDataValue(0, Registry.get(Byte.class), (byte)0x40),
                    new WrappedDataValue(23, Registry.getBlockDataSerializer(false), BukkitConverters.getWrappedBlockDataConverter().getGeneric(wrappedBlockData))
            );

            PacketContainer metadataPacket = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
            metadataPacket.getIntegers().write(0, entityId);
            metadataPacket.getDataValueCollectionModifier().write(0, dataValues);

            protocolManager.sendServerPacket(receiver, metadataPacket);

            blockSet.add(loc, entityId);
        }

        this.glowingBlocks.put(uuid, blockSet);
    }

    public void unsetGlowingBlock(Player receiver, Location location) {
        Location loc = LocationUtil.normalize(location);
        UUID uuid = receiver.getUniqueId();

        var glowingBlocks = this.glowingBlocks.get(uuid);

        if (glowingBlocks != null && !glowingBlocks.has(loc)) {
            return;
        }

        if (glowingBlocks == null) {
            this.glowingBlocks.put(uuid, new GlowingSet<>());
            return;
        }

        int entityId = glowingBlocks.getEntityId(loc);

        PacketContainer despawnPacket = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
        despawnPacket.getIntLists().write(0, List.of(entityId));

        gameManager.getProtocolManager().sendServerPacket(receiver, despawnPacket);

        glowingBlocks.remove(loc);

        this.glowingBlocks.put(uuid, glowingBlocks);
    }

    public void unsetAllGlowingBlocks(Player receiver) {
        UUID uuid = receiver.getUniqueId();
        var glowingBlocks = this.glowingBlocks.get(uuid);

        for (GlowingSet<Location>.GlowingData data : glowingBlocks.getRawSet()) {
            int entityId = data.getEntityId();

            PacketContainer despawnPacket = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
            despawnPacket.getIntLists().write(0, List.of(entityId));

            gameManager.getProtocolManager().sendServerPacket(receiver, despawnPacket);
        }

        this.glowingBlocks.put(uuid, new GlowingSet<>());
    }

    public void setGlowingEntity(Player receiver, Entity target) {
        UUID targetUUID = target.getUniqueId();
        UUID receiverUUID = receiver.getUniqueId();

        var playerSet = this.glowingEntities.get(receiverUUID);

        if (playerSet != null && playerSet.has(targetUUID)) {
            return;
        }

        if (playerSet == null) {
            playerSet = new GlowingSet<>();
        }

        int entityId = target.getEntityId();

        PacketContainer glowingPacket = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
        glowingPacket.getIntegers().write(0, entityId);
        glowingPacket.getDataValueCollectionModifier().write(0, List.of(new WrappedDataValue(0, Registry.get(Byte.class), (byte)0x40)));

        gameManager.getProtocolManager().sendServerPacket(receiver, glowingPacket);

        playerSet.add(targetUUID, entityId);

        this.glowingEntities.put(receiverUUID, playerSet);
    }

    public void setGlowingEntities(Player receiver, Entity[] targets) {
        UUID receiverUUID = receiver.getUniqueId();

        var playerSet = this.glowingEntities.get(receiverUUID);

        if (playerSet == null) {
            playerSet = new GlowingSet<>();
        }

        for (Entity target : targets) {
            UUID targetUUID = target.getUniqueId();

            if (playerSet.has(targetUUID)) {
                continue;
            }

            int entityId = target.getEntityId();

            PacketContainer glowingPacket = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
            glowingPacket.getIntegers().write(0, entityId);
            glowingPacket.getDataValueCollectionModifier().write(0, List.of(new WrappedDataValue(0, Registry.get(Byte.class), (byte)0x40)));

            gameManager.getProtocolManager().sendServerPacket(receiver, glowingPacket);

            playerSet.add(targetUUID, entityId);
        }

        this.glowingEntities.put(receiverUUID, playerSet);
    }

    public void unsetGlowingEntity(Player receiver, Entity target) {
        UUID targetUUID = target.getUniqueId();
        UUID receiverUUID = receiver.getUniqueId();

        var playerSet = this.glowingEntities.get(receiverUUID);

        if (playerSet != null && !playerSet.has(targetUUID)) {
            return;
        }

        if (playerSet == null) {
            this.glowingEntities.put(receiverUUID, new GlowingSet<>());
            return;
        }

        PacketContainer glowingPacket = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
        glowingPacket.getIntegers().write(0, target.getEntityId());
        glowingPacket.getDataValueCollectionModifier().write(0, List.of(new WrappedDataValue(0, Registry.get(Byte.class), (byte)0)));

        gameManager.getProtocolManager().sendServerPacket(receiver, glowingPacket);

        playerSet.remove(targetUUID);
    }

    public void unsetAllGlowingEntities(Player receiver) {
        UUID receiverUUID = receiver.getUniqueId();
        var playerSet = this.glowingEntities.get(receiverUUID);

        for (GlowingSet<UUID>.GlowingData data : playerSet.getRawSet()) {
            int entityId = data.getEntityId();

            PacketContainer glowingPacket = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
            glowingPacket.getIntegers().write(0, entityId);
            glowingPacket.getDataValueCollectionModifier().write(0, List.of(new WrappedDataValue(0, Registry.get(Byte.class), (byte)0)));

            gameManager.getProtocolManager().sendServerPacket(receiver, glowingPacket);
        }

        this.glowingEntities.put(receiverUUID, new GlowingSet<>());
    }
}

