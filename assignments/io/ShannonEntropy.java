public class ShannonEntropy {
    public static void main(String[] args) {
        final int m = Integer.parseInt(args[0]);

        int totalCount = 0;
        int[] values = new int[m];
        while (!StdIn.isEmpty()) {
            final int value = StdIn.readInt();
            values[value - 1]++;
            totalCount++;
        }

        if (totalCount == 0) {
            System.out.println("No data provided, cannot compute Shannon entropy");
            return;
        }

        double entropy = 0.0;
        for (int count : values) {
            final double probability = (double) count / totalCount;
            final double plog2p = (count == 0) ? 0.0 : (Math.log(probability) / Math.log(2));
            entropy -= (probability * plog2p);
        }
        System.out.printf("%.4f%n", entropy);
    }
}
