package tecci.amogus.util;

import org.bukkit.Location;

public final class LocationUtil {
    public class LocationNoWorld {
        private double x;
        private double y;
        private double z;

        public LocationNoWorld(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    public static Location normalize(Location location) {
        return new Location(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }
}
