public class MaximumSquareSubmatrix {
    // Returns the size of the largest contiguous square submatrix
    // of a[][] containing only 1s.
    public static int size(int[][] a) {
        final int size = a.length;

        int maxSize = 0;
        int[][] dp = new int[size][size];
        for (int i = 0; i < size; i++) {
            dp[i][0] = a[i][0];
            maxSize = Math.max(maxSize, dp[i][0]);
        }
        for (int j = 0; j < size; j++) {
            dp[0][j] = a[0][j];
            maxSize = Math.max(maxSize, dp[0][j]);
        }

        // Build the dp table
        for (int i = 1; i < size; i++) {
            for (int j = 1; j < size; j++) {
                if (a[i][j] == 1) {
                    dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1])) + 1;
                    maxSize = Math.max(maxSize, dp[i][j]);
                }
            }
        }

        return maxSize;
    }

    // Reads an n-by-n matrix of 0s and 1s from standard input
    // and prints the size of the largest contiguous square submatrix
    // containing only 1s.
    public static void main(String[] args) {
        final int size = StdIn.readInt();

        int[][] matrix = new int[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                matrix[row][col] = StdIn.readInt();
            }
        }

        System.out.println(size(matrix));
    }
}
