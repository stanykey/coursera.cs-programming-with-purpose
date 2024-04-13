public class RightTriangle {
    public static void main(String[] args) {
        final int side1 = Integer.parseInt(args[0]);
        final int side2 = Integer.parseInt(args[1]);
        final int side3 = Integer.parseInt(args[2]);

        final int a = Math.min(Math.min(side1, side2), side3);
        final int c = Math.max(Math.max(side1, side2), side3);
        final int b = side1 + side2 + side3 - a - c;

        final int expected = c * c;
        final int actual = a * a + b * b;

        System.out.println(side1 > 0 && side2 > 0 && side3 > 0 && actual == expected);
    }
}
