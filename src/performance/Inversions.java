public class Inversions {
    // Return the number of inversions in the permutation a[].
    public static long count(int[] a) {
        long counter = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = 1; j < a.length; j++) {
                if (i < j && a[i] > a[j]) {
                    counter++;
                }
            }
        }
        return counter;
    }

    // Return a permutation of length n with exactly k inversions.
    public static int[] generate(int n, long k) {
        int shifts = 0;
        while (k > 0 && k >= (n - shifts - 1)) {
            shifts++;
            k -= (n - shifts);
        }

        int[] data = new int[n];
        for (int i = 0; i < shifts; i++) {
            data[i] = n - i - 1;
        }

        for (int i = shifts; i < n; i++) {
            data[i] = i - shifts;
        }

        if (k > 0) {
            final int left = (int) (n - k - 1);
            final int count = n - left - 1;
            final int temp = data[n - 1];
            for (int i = 1; i <= count; i++) {
                data[n - i] = data[n - i - 1];
            }
            data[left] = temp;
        }

        return data;
    }

    // Takes an integer n and a long k as command-line arguments,
    // and prints a permutation of length n with exactly k inversions.
    public static void main(String[] args) {
        final int lenght = Integer.parseInt(args[0]);
        final long inversions = Long.parseLong(args[1]);

        final int[] data = generate(lenght, inversions);
        for (int i = 0; i < data.length; i++) {
            if (i > 0) {
                System.out.print(' ');
            }
            System.out.print(data[i]);
        }
        System.out.println();
    }
}
