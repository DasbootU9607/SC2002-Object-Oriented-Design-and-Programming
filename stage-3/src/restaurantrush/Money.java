package restaurantrush;

/** All prices are integer cents. Percentage rounding is half up to one cent. */
public final class Money {
    private Money() {}
    public static int percent(int cents, int percent) {
        if (cents < 0 || percent < 0) throw new IllegalArgumentException("Negative money or percentage.");
        return Math.toIntExact(((long) cents * percent + 50) / 100);
    }
    public static String format(int cents) {
        return String.format(java.util.Locale.ROOT, "$%d.%02d", cents / 100, cents % 100);
    }
}
