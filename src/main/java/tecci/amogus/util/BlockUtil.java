package tecci.amogus.util;

import org.bukkit.Location;
import org.bukkit.World;

public final class BlockUtil {
    //returns true if there are blocks obstructing the path between the start and the end locations
    public static boolean isObstructed(Location start, Location end) {
        World world = start.getWorld();

        if (world.getUID() != end.getWorld().getUID()) {
            return true;
        }

        if (!world.getBlockAt(start).isEmpty() || !world.getBlockAt(end).isEmpty()) {
            return true;
        }

        return world.rayTraceBlocks(start, end.toVector().subtract(start.toVector()).normalize(), start.distance(end)) != null;
    }
}
