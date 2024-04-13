public class Ramanujan {
    // Is n a Ramanujan number?
    public static boolean isRamanujan(long n) {
        final long MIN_RAMANUJAN = 1729;
        if (n < MIN_RAMANUJAN) {
            return false;
        }

        final int bound = (int) Math.ceil(Math.cbrt(n));
        long[] cubes = new long[bound - 1];
        for (int i = 0; i < cubes.length; i++) {
            final long x = i + 1;
            cubes[i] = x * x * x;
        }

        boolean found = false;
        for (int i = 0; i < cubes.length; i++) {
            final long required = n - cubes[i];

            // Binary search
            int left = i + 1;
            int right = cubes.length - 1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (cubes[mid] == required) {
                    if (found) {
                        return true;
                    }
                    found = true;
                    break;
                }

                if (cubes[mid] < required) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }

        return false;
    }

    // Takes a long integer command-line arguments n and prints true if
    // n is a Ramanujan number, and false otherwise.
    public static void main(String[] args) {
        final long n = Long.parseLong(args[0]);
        System.out.println(isRamanujan(n));
    }
}
