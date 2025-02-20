package tecci.amogus.util;

public final class MathUtil {
    public static int clamp(int value, int min, int max) {
        if (value < min) {
            return min;
        }  else if (value > max) {
            return max;
        }

        return value;
    }
}
