public class TrinomialDP {
    // Returns the trinomial coefficient T(n, k).
    public static long trinomial(int n, int k) {
        if (n == 0 && k == 0) {
            return 1;
        }

        if (k < -n || k > n) {
            return 0;
        }

        long[][] dp = new long[n + 1][2 * n + 3];

        dp[0][n + 1] = 1; // Base case
        for (int i = 1; i <= n; i++) {
            for (int j = -i; j <= i; j++) {
                dp[i][j + n + 1] = dp[i - 1][j - 1 + n + 1] + dp[i - 1][j + n + 1] + dp[i - 1][j + 1 + n + 1];
            }
        }

        return dp[n][k + n + 1];
    }

    // Takes two integer command-line arguments n and k and prints T(n, k).
    public static void main(String[] args) {
        final int n = Integer.parseInt(args[0]);
        final int k = Integer.parseInt(args[1]);
        System.out.println(trinomial(n, k));
    }
}
