public class WorldMap {
    public static void main(String[] args) {
        final int width = StdIn.readInt();
        final int height = StdIn.readInt();

        StdDraw.setCanvasSize(width, height);
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);
        StdDraw.enableDoubleBuffering();

        while (!StdIn.isEmpty()) {
            StdIn.readString(); // skip region name

            final int verticesCount = StdIn.readInt();

            double[] x = new double[verticesCount];
            double[] y = new double[verticesCount];
            for (int i = 0; i < verticesCount; i++) {
                x[i] = StdIn.readDouble();
                y[i] = StdIn.readDouble();
            }

            StdDraw.polygon(x, y);
        }

        StdDraw.show();
    }
}
