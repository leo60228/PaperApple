package space.leo60228.paperapple;

import io.netty.buffer.Unpooled;
import net.minecraft.server.v1_15_R1.MinecraftKey;
import net.minecraft.server.v1_15_R1.PacketDataSerializer;
import net.minecraft.server.v1_15_R1.PacketPlayOutCustomPayload;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SyncHandler {
    private static final MinecraftKey EXHAUSTION_SYNC = new MinecraftKey("appleskin", "exhaustion_sync");
    private static final MinecraftKey SATURATION_SYNC = new MinecraftKey("appleskin", "saturation_sync");

    private static PacketPlayOutCustomPayload makeSyncPacket(MinecraftKey identifier, float val) {
        PacketDataSerializer buf = new PacketDataSerializer(Unpooled.buffer());
        buf.writeFloat(val);
        return new PacketPlayOutCustomPayload(identifier, buf);
    }

    private static final Map<UUID, Float> lastSaturationLevels = new HashMap<UUID, Float>();
    private static final Map<UUID, Float> lastExhaustionLevels = new HashMap<UUID, Float>();

    public static void onPlayerUpdate(Player player) {
        if (!getEnabled(player)) return;

        Float lastSaturationLevel = lastSaturationLevels.get(player.getUniqueId());
        Float lastExhaustionLevel = lastExhaustionLevels.get(player.getUniqueId());

        float saturation = player.getSaturation();
        if (lastSaturationLevel == null || lastSaturationLevel != saturation) {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(makeSyncPacket(SATURATION_SYNC, saturation));
            lastSaturationLevels.put(player.getUniqueId(), saturation);
        }

        float exhaustionLevel = player.getExhaustion();
        if (lastExhaustionLevel == null || Math.abs(lastExhaustionLevel - exhaustionLevel) >= 0.01f) {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(makeSyncPacket(EXHAUSTION_SYNC, exhaustionLevel));
            lastExhaustionLevels.put(player.getUniqueId(), exhaustionLevel);
        }
    }

    public static void onPlayerLoggedIn(Player player) {
        lastSaturationLevels.remove(player.getUniqueId());
        lastExhaustionLevels.remove(player.getUniqueId());
    }

    public static boolean getEnabled(Player player) {
        return player.getPersistentDataContainer().getOrDefault(PaperApple.IS_ENABLED, PersistentDataType.BYTE, (byte) 0) != 0;
    }

    public static void setEnabled(Player player, boolean enabled) {
        player.getPersistentDataContainer().set(PaperApple.IS_ENABLED, PersistentDataType.BYTE, enabled ? (byte) 1 : (byte) 0);
    }
}
