import java.awt.Color;

public class Checkerboard {
    public static void main(String[] args) {
        final int size = Integer.parseInt(args[0]);

        StdDraw.setScale(0.0, size);

        final double squareHalfSize = 0.5;
        for (int row = 0; row < size; row++) {
            Color cellColor = (row % 2 == 0) ? StdDraw.BLUE : StdDraw.LIGHT_GRAY;
            for (int col = 0; col < size; col++) {
                StdDraw.setPenColor(cellColor);
                StdDraw.filledSquare(col + squareHalfSize, row + squareHalfSize, squareHalfSize);

                cellColor = (cellColor == StdDraw.BLUE) ? StdDraw.LIGHT_GRAY : StdDraw.BLUE;
            }
        }
    }
}
