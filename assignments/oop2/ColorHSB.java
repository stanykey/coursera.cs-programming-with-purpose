public class ColorHSB {
    private static final int MAX_HUE = 359;
    private static final int MAX_PERCENTAGE = 100;

    private final int mHue;
    private final int mSaturation;
    private final int mBrightness;

    // Creates a color with hue h, saturation s, and brightness b.
    public ColorHSB(int h, int s, int b) {
        if (h < 0 || h > MAX_HUE) {
            throw new IllegalArgumentException("Invalid value (" + h + ") for hue. Valid range is [0, " + MAX_HUE + "]");
        }

        if (s < 0 || s > MAX_PERCENTAGE) {
            throw new IllegalArgumentException("Invalid value (" + s + ") for saturation. Valid range is [0, " + MAX_PERCENTAGE + "]");
        }

        if (b < 0 || b > MAX_PERCENTAGE) {
            throw new IllegalArgumentException("Invalid value (" + b + ") for brightness. Valid range is [0, " + MAX_PERCENTAGE + "]");
        }

        mHue = h;
        mSaturation = s;
        mBrightness = b;
    }

    // Returns a string representation of this color, using the format (h, s, b).
    public String toString() {
        return String.format("(%d, %d, %d)", mHue, mSaturation, mBrightness);
    }

    // Is this color a shade of gray?
    public boolean isGrayscale() {
        return (mSaturation == 0) || (mBrightness == 0);
    }

    // Returns the squared distance between the two colors.
    public int distanceSquaredTo(ColorHSB other) {
        if (other == null) {
            throw new IllegalArgumentException("Argument is null");
        }

        final int hueDiff = mHue - other.mHue;
        final int invHueDiff = 360 - Math.abs(hueDiff);
        final int satDiff = mSaturation - other.mSaturation;
        final int brightDiff = mBrightness - other.mBrightness;
        return Math.min(hueDiff * hueDiff, invHueDiff * invHueDiff) + satDiff * satDiff + brightDiff * brightDiff;
    }

    // Sample client (see below).
    public static void main(String[] args) {
        final int hue = Integer.parseInt(args[0]);
        final int saturation = Integer.parseInt(args[1]);
        final int brightness = Integer.parseInt(args[2]);

        final ColorHSB unknownColor = new ColorHSB(hue, saturation, brightness);

        String colorName = "Black";
        ColorHSB namedColor = new ColorHSB(0, 0, 0);

        int min = Integer.MAX_VALUE;
        while (!StdIn.isEmpty()) {
            final String name = StdIn.readString();

            final int h = StdIn.readInt();
            final int s = StdIn.readInt();
            final int b = StdIn.readInt();
            final ColorHSB color = new ColorHSB(h, s, b);

            final int distance = unknownColor.distanceSquaredTo(color);
            if (min > distance) {
                min = distance;
                colorName = name;
                namedColor = color;
            }
        }

        StdOut.println(colorName + " " + namedColor);
    }
}
