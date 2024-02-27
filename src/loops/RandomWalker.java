public class RandomWalker {
    public static void main(String[] args) {
        final int distance = Integer.parseInt(args[0]);

        int x = 0;
        int y = 0;
        int steps = 0;
        while (Math.abs(x) + Math.abs(y) < distance) {
            System.out.println("(" + x + ", " + y + ")");
            final int direction = (int) (Math.random() * 4);

            // Move walker in the chosen direction
            if      (direction == 0) y++;
            else if (direction == 1) y--;
            else if (direction == 2) x++;
            else                     x--;

            steps++;
        }
        System.out.println("(" + x + ", " + y + ")");
        System.out.println("steps = " + steps);
    }
}
