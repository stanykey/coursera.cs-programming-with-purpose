public class DiscreteDistribution {
    public static void main(String[] args) {
        final int M = Integer.parseInt(args[0]);

        int sum = 0;
        int[] sequence = new int[args.length - 1];
        for (int i = 0; i < sequence.length; i++) {
            sequence[i] = Integer.parseInt(args[i + 1]);
            sum += sequence[i];
        }

        for (int j = 0; j < M; j++) {
            final int r = (int) (Math.random() * sum);

            int index = 0;
            int cumulativeSum = 0;
            while (r >= cumulativeSum + sequence[index]) {
                cumulativeSum += sequence[index];
                index++;
            }

            System.out.print((index + 1) + " ");
        }
    }
}
