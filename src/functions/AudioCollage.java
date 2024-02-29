public class AudioCollage {
    // Returns a new array that rescales source[] by source multiplicative factor of alpha.
    public static double[] amplify(double[] source, double alpha) {
        double[] amplified = new double[source.length];
        for (int i = 0; i < source.length; i++) {
            amplified[i] = source[i] * alpha;
        }
        return amplified;
    }

    // Returns a new array that is the reverse of source[].
    public static double[] reverse(double[] source) {
        double[] reversed = new double[source.length];
        for (int i = 0; i < source.length; i++) {
            reversed[i] = source[source.length - i - 1];
        }
        return reversed;
    }

    // Returns a new array that is the clamped version of source[].
    private static double[] clamp(double[] source) {
        double[] clamped = new double[source.length];
        for (int i = 0; i < source.length; i++) {
            final double x = source[i];
            if      (x < -1.0) { clamped[i] = -1.0; }
            else if (x > 1.0)  { clamped[i] = 1.0;  }
            else               { clamped[i] = x;    }
        }
        return clamped;
    }

    // Returns a new array that is the concatenation of a[] and b[].
    public static double[] merge(double[] a, double[] b) {
        double[] merged = new double[a.length + b.length];

        // copy first array
        for (int i = 0; i < a.length; i++) {
            merged[i] = a[i];
        }

        // copy second array
        for (int i = 0; i < b.length; i++) {
            merged[a.length + i] = b[i];
        }

        return merged;
    }

    // Returns a new array that is the sum of a[] and b[],
    // padding the shorter arrays with trailing 0s if necessary.
    public static double[] mix(double[] a, double[] b) {
        double[] mixed = new double[Math.max(a.length, b.length)];
        for (int i = 0; i < mixed.length; i++) {
            mixed[i]  = (i < a.length) ? a[i] : 0.0;
            mixed[i] += (i < b.length) ? b[i] : 0.0;
        }
        return mixed;
    }

    // Returns a new array that changes the speed by the given factor.
    public static double[] changeSpeed(double[] source, double alpha) {
        final int newSize = (int) (source.length / alpha);
        double[] result = new double[newSize];
        for (int i = 0; i < newSize; i++) {
            int index = (int) (i * alpha);
            result[i] = source[index];
        }
        return result;
    }

    // Creates an audio collage and plays it on standard audio.
    // See below for the requirements.
    public static void main(String[] args) {
        final int SAMPLE_RATE = 44100;
        final int duration = 10 + StdRandom.uniform(50);
        final int samplesCount = duration * SAMPLE_RATE;

        final String[] wavFiles = {"beatbox.wav", "chimes.wav", "harp.wav", "piano.wav", "singer.wav"};
        double[][] sources = new double[wavFiles.length][];
        for (int i = 0; i < wavFiles.length; i++) {
            sources[i] = StdAudio.read(wavFiles[i]);
            while (sources[i].length < samplesCount) {
                sources[i] = merge(sources[i], reverse(sources[i]));
            }
        }

        double[] audioTrack = new double[samplesCount];
        for (double[] source : sources) {
            audioTrack = mix(audioTrack, source);
        }

        audioTrack = amplify(audioTrack, 0.5 + Math.random() * 2.5);
        audioTrack = changeSpeed(audioTrack, 0.5 + Math.random() * 2.5);
        while (audioTrack.length < samplesCount) {
            audioTrack = merge(audioTrack, audioTrack);
        }
        audioTrack = clamp(audioTrack);
        StdAudio.play(audioTrack);
    }
}
