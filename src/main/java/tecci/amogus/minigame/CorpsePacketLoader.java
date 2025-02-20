package tecci.amogus.minigame;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.*;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class CorpsePacketLoader {
    private final Corpse corpse;
    private PacketContainer entitySpawn;
    private PacketContainer entityDestroy;
    private PacketContainer entityEquipment;
    private PacketContainer entityMetadata;
    private PacketContainer playerInfo;

    public CorpsePacketLoader(Corpse corpse) {
        this.corpse = corpse;

        initEntitySpawn();
        initEntityDestroy();
        initEntityEquipment();
        initEntityMetadata();
        initPlayerInfo();
    }

    private void initEntitySpawn() {
        entitySpawn = new PacketContainer(PacketType.Play.Server.SPAWN_ENTITY);

        entitySpawn.getIntegers().write(0, corpse.getEntityId());
        entitySpawn.getUUIDs().write(0, corpse.getUuid());
        entitySpawn.getEntityTypeModifier().write(0, EntityType.PLAYER);

        Location location = corpse.getLocation();
        entitySpawn.getDoubles()
                .write(0, location.getX())
                .write(1, location.getY())
                .write(2, location.getZ());

        entitySpawn.getBytes()
                .write(0, (byte) (location.getYaw() * 256.0 / 360.0))
                .write(1, (byte) (location.getPitch() * 256.0 / 360.0));
    }

    private void initEntityDestroy() {
        entityDestroy = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
        entityDestroy.getIntLists().write(0, List.of(corpse.getEntityId()));
    }

    private void initEntityEquipment() {
        entityEquipment = new PacketContainer(PacketType.Play.Server.ENTITY_EQUIPMENT);
        entityEquipment.getIntegers().write(0, corpse.getEntityId());

        ItemStack[] armorContents = corpse.getArmorContents();
        List<Pair<EnumWrappers.ItemSlot, ItemStack>> equipment = new ArrayList<>();

        if (armorContents[0] != null) {
            equipment.add(new Pair<>(EnumWrappers.ItemSlot.FEET, armorContents[0]));
        }

        if (armorContents[1] != null) {
            equipment.add(new Pair<>(EnumWrappers.ItemSlot.LEGS, armorContents[1]));
        }

        if (armorContents[2] != null) {
            equipment.add(new Pair<>(EnumWrappers.ItemSlot.CHEST, armorContents[2]));
        }

        if (armorContents[3] != null) {
            equipment.add(new Pair<>(EnumWrappers.ItemSlot.HEAD, armorContents[3]));
        }

        entityEquipment.getSlotStackPairLists().write(0, equipment);
    }

    private void initEntityMetadata() {
        List<WrappedDataValue> dataValues = List.of(
                new WrappedDataValue(6, WrappedDataWatcher.Registry.get(EnumWrappers.getEntityPoseClass()), EnumWrappers.EntityPose.SLEEPING), //Entity Pose
                new WrappedDataValue(17, WrappedDataWatcher.Registry.get(Byte.class), 0x01 | 0x02 | 0x04 | 0x08 | 0x10 | 0x20 | 0x40) //Skin Layers
        );

        entityMetadata = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
        entityMetadata.getIntegers().write(0, corpse.getEntityId());
        entityMetadata.getDataValueCollectionModifier().write(0, dataValues);
    }

    private void initPlayerInfo() {
        PlayerInfoData data = new PlayerInfoData(
                corpse.getGameProfile(),
                1,
                EnumWrappers.NativeGameMode.CREATIVE,
                WrappedChatComponent.fromText(corpse.getName())
        );

        playerInfo = new PacketContainer(PacketType.Play.Server.PLAYER_INFO);
        playerInfo.getPlayerInfoActions().write(0, EnumSet.of(EnumWrappers.PlayerInfoAction.ADD_PLAYER));
        playerInfo.getPlayerInfoDataLists().write(0, List.of(data));
    }

    public PacketContainer getSpawnPacket() {
        return entitySpawn;
    }

    public PacketContainer getDestroyPacket() {
        return entityDestroy;
    }

    public PacketContainer getEquipmentPacket() {
        return entityEquipment;
    }

    public PacketContainer getMetadataPacket() {
        return entityMetadata;
    }

    public PacketContainer getPlayerInfoPacket() {
        return playerInfo;
    }
}
