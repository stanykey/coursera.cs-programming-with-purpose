public class RandomWalkers {
    public static void main(String[] args) {
        int distance = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        long totalSteps = 0;
        for (int i = 0; i < trials; i++) {
            int x = 0;
            int y = 0;
            int steps = 0;
            while (Math.abs(x) + Math.abs(y) < distance) {
                final int direction = (int) (Math.random() * 4);

                // Move walker in the chosen direction
                if      (direction == 0) y++;
                else if (direction == 1) y--;
                else if (direction == 2) x++;
                else                     x--;

                steps++;
            }

            totalSteps += steps;
        }

        double averageSteps = (double) totalSteps / trials;

        System.out.println("average number of steps = " + averageSteps);
    }
}
