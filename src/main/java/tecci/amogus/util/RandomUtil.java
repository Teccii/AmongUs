package tecci.amogus.util;

import java.util.Random;

public final class RandomUtil {
    private static final Random rng = new Random();

    public static int nextInt(int min, int max) {
        return min + rng.nextInt(max - min);
    }

    public static double nextDouble(double min, double max) {
        return min + rng.nextDouble() * (max - min);
    }
}
