public class GreatCircle {
    public static void main(String[] args) {
        final double x1 = Math.toRadians(Double.parseDouble(args[0]));
        final double y1 = Math.toRadians(Double.parseDouble(args[1]));
        final double x2 = Math.toRadians(Double.parseDouble(args[2]));
        final double y2 = Math.toRadians(Double.parseDouble(args[3]));

        // Haversine formula
        final double earthRadius = 6371.0;  // Radius of the Earth in kilometers
        final double a = Math.pow(Math.sin((x2 - x1) / 2), 2) + Math.cos(x1) * Math.cos(x2) * Math.pow(Math.sin((y2 - y1) / 2), 2);
        final double distance = 2 * earthRadius * Math.asin(Math.sqrt(a));

        System.out.println(distance + " kilometers");
    }
}
