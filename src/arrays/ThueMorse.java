public class ThueMorse {
    public static void main(String[] args) {
        final int n = Integer.parseInt(args[0]);

        boolean[] sequence = new boolean[n];
        for (int i = 0; i < n; i++) {
            sequence[i] = Integer.bitCount(i) % 2 == 0;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                char symbol = sequence[i] == sequence[j] ? '+' : '-';
                System.out.print(symbol + "  ");
            }
            System.out.println();
        }
    }
}
