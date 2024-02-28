public class Birthday {
    public static void main(String[] args) {
        final int n = Integer.parseInt(args[0]);
        final int trials = Integer.parseInt(args[1]);

        int[] counters = new int[n + 1];
        for (int i = 0; i < trials; i++) {
            int peopleCount = 0;
            boolean[] birthdays = new boolean[n];
            while (true) {
                int birthday = (int) (Math.random() * n);
                if (birthdays[birthday]) {
                    break;
                }

                birthdays[birthday] = true;
                peopleCount++;
            }

            counters[peopleCount]++;
        }

        double peopleCount = 0;
        double fraction = 0.0;
        for (int i = 0; fraction < 0.5; i++) {
            peopleCount += counters[i];
            fraction = peopleCount / trials;

            System.out.printf("%d\t%d\t%f%n", i + 1, counters[i], fraction);
        }
    }
}
