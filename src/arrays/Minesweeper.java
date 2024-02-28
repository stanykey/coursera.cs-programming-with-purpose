public class Minesweeper {
    public static void main(String[] args) {
        final int height = Integer.parseInt(args[0]);
        final int width = Integer.parseInt(args[1]);
        final int mines = Integer.parseInt(args[2]);

        char[] field = new char[height * width];
        for (int i = 0; i < mines; i++) {
            int index = 0;
            do {
                index = (int) (Math.random() * field.length);
            } while (field[index] == '*'); // Ensure no duplicate mines
            field[index] = '*';
        }

        for (int index = 0; index < field.length; index++) {
            if (field[index] == '*') {
                continue; // Skip counting for mine cells
            }

            char minesCount = '0';
            final int x = index % width;
            final int y = index / width;
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    int newX = x + dx;
                    int newY = y + dy;
                    if (newX >= 0 && newX < width && newY >= 0 && newY < height) {
                        int newIndex = newY * width + newX;
                        if (field[newIndex] == '*') {
                            minesCount++;
                        }
                    }
                }
            }
            field[index] = minesCount;
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int index = y * width + x;
                System.out.printf("%-3c", field[index]);
            }
            System.out.println();
        }
    }
}
