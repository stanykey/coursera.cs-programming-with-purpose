public class RevesPuzzle {
    private static void hanoi(int n, int k, String from, String temp, String to) {
        if (n == 0) {
            System.out.println();
            return;
        }
        hanoi(n - 1, k, from, to, temp);
        System.out.print("Move disc " + (n + k) + " from " + from + " to " + to);
        hanoi(n - 1, k, temp, from, to);
    }

    private static void reves(int n, String from, String temp1, String temp2, String to) {
        final int k = (int) (n + 1 - Math.round(Math.sqrt(2 * n + 1)));
        if (k == 0) {
            System.out.print("Move disc " + 1 + " from " + from + " to " + to);
            return;
        }

        reves(k, from, to, temp2, temp1);
        hanoi(n - k, k, from, temp2, to);
        reves(k, temp1, from, temp2, to);
    }

    public static void main(String[] args) {
        final int n = Integer.parseInt(args[0]);

        reves(n, "A", "B", "C", "D");
    }
}
