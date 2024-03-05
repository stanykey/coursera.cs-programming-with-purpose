public class Huntingtons {
    private static final String NOT_HUMAN    = "not human";
    private static final String NORMAL      = "normal";
    private static final String HIGH_RISK    = "high risk";
    private static final String HUNTINGTONS = "Huntington's";

    // Returns the maximum number of consecutive repeats of CAG in the DNA string.
    public static int maxRepeats(String dna) {
        int maxRepeats = 0;

        final String cag = "CAG";

        int currentStreak = 0;
        int currentCagPosition = dna.indexOf(cag);
        while (currentCagPosition != -1) {
            currentStreak++;

            final int nextCagPosition = dna.indexOf(cag, currentCagPosition + cag.length());
            if (nextCagPosition == -1) {
                return Math.max(maxRepeats, currentStreak);
            }

            if (nextCagPosition != (currentCagPosition + cag.length())) {
                maxRepeats = Math.max(maxRepeats, currentStreak);
                currentStreak = 0;
            }
            currentCagPosition = nextCagPosition;
        }
        return maxRepeats;
    }

    // Returns a copy of s, with all whitespace (spaces, tabs, and newlines) removed.
    public static String removeWhitespace(String s) {
        if (s.isEmpty()) {
            return "";
        }

        return s.replace(" ", "").replace("\t", "").replace("\n", "");
    }

    // Returns one of these diagnoses corresponding to the maximum number of repeats:
    // "not human", "normal", "high risk", or "Huntington's".
    public static String diagnose(int maxRepeats) {
        if (maxRepeats < 10 || maxRepeats > 180) {
            return NOT_HUMAN;
        }

        if (maxRepeats < 36) {
            return NORMAL;
        }

        if (maxRepeats < 40) {
            return HIGH_RISK;
        }

        return HUNTINGTONS;
    }

    // Sample client (see below).
    public static void main(String[] args) {
        final String filename = args[0];

        final In file = new In(filename);
        final String dna = removeWhitespace(file.readAll());

        final int maxRepeats = maxRepeats(dna);
        System.out.println("max repeats = " + maxRepeats);
        System.out.println(diagnose(maxRepeats));
    }
}
