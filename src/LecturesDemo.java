public class LecturesDemo {
    //
    // Lecture 6: Recursion
    //
    public static String hanoi(int n, boolean left) {
        if (n == 0) {
            return " ";
        }

        final String move = n + (left ? "L" : "R");
        return hanoi(n - 1, !left) + move + hanoi(n - 1, !left);
    }

    public static void hanoiDemo(String[] args) {
        int n = Integer.parseInt(args[0]);
        System.out.println(hanoi(n, false));
    }

    public static void drawHTree(int n, double sz, double x, double y) {
        if (n == 0) {
            return;
        }

        final double x0 = x - sz / 2;
        final double x1 = x + sz / 2;
        final double y0 = y - sz / 2;
        final double y1 = y + sz / 2;

        StdDraw.line(x0, y, x1, y);
        StdDraw.line(x0, y0, x0, y1);
        StdDraw.line(x1, y0, x1, y1);

        drawHTree(n - 1, sz / 2, x0, y0);
        drawHTree(n - 1, sz / 2, x0, y1);
        drawHTree(n - 1, sz / 2, x1, y0);
        drawHTree(n - 1, sz / 2, x1, y1);
    }

    public static void drawHTreeDemo(String[] args) {
        int n = Integer.parseInt(args[0]);
        drawHTree(n, 0.5, 0.5, 0.5);
    }

    public static void drawBrownianCurve(double x0, double y0, double x1, double y1, double var, double s) {
        if (x1 - x0 < 0.01) {
            StdDraw.line(x0, y0, x1, y1);
            return;
        }

        final double xm = (x0 + x1) / 2;
        final double ym = (y0 + y1) / 2;
        final double stddev = Math.sqrt(var);
        final double delta = StdRandom.gaussian(0, stddev);
        drawBrownianCurve(x0, y0, xm, ym + delta, var / s, s);
        drawBrownianCurve(xm, ym + delta, x1, y1, var / s, s);
    }

    public static void drawBrownianCurveDemo(String[] args) {
        final double hurst = Double.parseDouble(args[0]);
        final double s = Math.pow(2, hurst * 2);
        drawBrownianCurve(0.0, 0.5, 1.0, 0.5, 0.01, s);
    }

    public static void main(String[] args) {
        //hanoiDemo(args);
        //drawHTreeDemo(args);
        drawBrownianCurveDemo(args);
    }
}
