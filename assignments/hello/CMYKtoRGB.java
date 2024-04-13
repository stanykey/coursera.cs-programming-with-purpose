public class CMYKtoRGB {
    public static void main(String[] args) {
        final double c = Double.parseDouble(args[0]);
        final double m = Double.parseDouble(args[1]);
        final double y = Double.parseDouble(args[2]);
        final double k = Double.parseDouble(args[3]);

        final double white = 1 - k;
        final int red = (int) Math.round(255 * white * (1 - c));
        final int green = (int) Math.round(255 * white * (1 - m));
        final int blue = (int) Math.round(255 * white * (1 - y));

        System.out.println("red   = " + red);
        System.out.println("green = " + green);
        System.out.println("blue  = " + blue);
    }
}
