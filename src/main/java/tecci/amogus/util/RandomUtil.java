package tecci.amogus.util;

import java.security.SecureRandom;

public final class RandomUtil {
    public static final SecureRandom rng = new SecureRandom();

    public static int nextInt(int min, int max) {
        return min + rng.nextInt(max - min);
    }
    public static double nextDouble(double min, double max) {
        return min + rng.nextDouble() * (max - min);
    }
    public static<E extends Enum<E>> E nextEnum(Class<E> enumClass) {
        E[] variants = enumClass.getEnumConstants();
        int i = rng.nextInt(variants.length);

        return variants[i];
    }
}
