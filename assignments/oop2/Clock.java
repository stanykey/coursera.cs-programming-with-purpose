public class Clock {
    private static final int HOURS_PER_DAY = 24;
    private static final int MINUTES_PER_HOUR = 60;

    private int mHours = 0;
    private int mMinutes = 0;

    // Creates a clock whose initial time with hours and minutes.
    public Clock(int hours, int minutes) {
        if (hours < 0 || hours >= HOURS_PER_DAY) {
            throw new IllegalArgumentException("Invalid value (" + hours + ") for hours. Valid range is [0, " + HOURS_PER_DAY + ")");
        }

        if (minutes < 0 || minutes >= MINUTES_PER_HOUR) {
            throw new IllegalArgumentException("Invalid value (" + minutes + ") for minutes. Valid range is [0, " + MINUTES_PER_HOUR + ")");
        }

        mHours = hours;
        mMinutes = minutes;
    }

    // Creates a clock whose initial time is specified as a string, using the format HH:MM.
    public Clock(String s) {
        final int delimiterPos = s.indexOf(':');
        if (delimiterPos == -1) {
            throw new IllegalArgumentException("Invalid format: missing delimiter");
        }

        final String hoursString = s.substring(0, delimiterPos);
        final String minutesString = s.substring(delimiterPos + 1);
        if (hoursString.length() != 2 || minutesString.length() != 2) {
            throw new IllegalArgumentException("Invalid time format");
        }

        final int hours = Integer.parseInt(hoursString);
        if (hours < 0 || hours >= HOURS_PER_DAY) {
            throw new IllegalArgumentException("Invalid value (" + hours + ") for hours. Valid range is [0, " + HOURS_PER_DAY + ")");
        }

        final int minutes = Integer.parseInt(minutesString);
        if (minutes < 0 || minutes >= MINUTES_PER_HOUR) {
            throw new IllegalArgumentException("Invalid value (" + minutes + ") for minutes. Valid range is [0, " + MINUTES_PER_HOUR + ")");
        }

        mHours = hours;
        mMinutes = minutes;
    }

    // Returns a string representation of this clock, using the format HH:MM.
    public String toString() {
        final String hours = ((mHours < 10) ? "0" : "") + mHours;
        final String minutes = ((mMinutes < 10) ? "0" : "") + mMinutes;
        return hours + ":" + minutes;
    }

    // Is the time on this clock earlier than the time on that one?
    public boolean isEarlierThan(Clock other) {
        if (mHours == other.mHours) {
            return mMinutes < other.mMinutes;
        }
        return mHours < other.mHours;
    }

    // Adds 1 minute to the time on this clock.
    public void tic() {
        mMinutes = (mMinutes + 1) % MINUTES_PER_HOUR;
        if (mMinutes == 0) {
            mHours = (mHours + 1) % HOURS_PER_DAY;
        }
    }

    // Adds Δ minutes to the time on this clock.
    public void toc(int delta) {
        if (delta < 0) {
            throw new IllegalArgumentException("Invalid delta value: must be positive");
        }

        final int totalMinutes = mHours * MINUTES_PER_HOUR + mMinutes + delta;
        mHours = (totalMinutes / MINUTES_PER_HOUR) % HOURS_PER_DAY;
        mMinutes = totalMinutes % MINUTES_PER_HOUR;
    }

    // Test client (see below).
    public static void main(String[] args) {
        final Clock clock = new Clock(13, 30);
        StdOut.println("Original time: " + clock);
        StdOut.println();

        StdOut.println("Create copy and tic once");
        Clock other1 = new Clock(clock.mHours, clock.mMinutes);
        other1.tic();
        StdOut.println("Is earlier than " + other1 + ": " + clock.isEarlierThan(other1));
        StdOut.println();

        StdOut.println("Create copy and toc for 95mins");
        Clock other2 = new Clock(clock.mHours, clock.mMinutes);
        other2.toc(95);
        StdOut.println("Is earlier than " + other2 + ": " + clock.isEarlierThan(other2));
    }
}
