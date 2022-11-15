/******************************************************************************
 *  Compilation:  javac Tone.java
 *  Execution:    java Tone hz duration
 *
 *  Play a note of the given freqency for the given duration.
 *
 *  % java Tone 440 1.5
 *
 ******************************************************************************/

public class Buzzer {

    public Buzzer() {
    }

    // create a pure tone of the given frequency for the given duration
    public static double[] tone(double hz, double duration) {
        int n = (int) (StdAudio.SAMPLE_RATE * duration);
        double[] a = new double[n+1];
        for (int i = 0; i <= n; i++) {
            a[i] = Math.sin(2 * Math.PI * i * hz / StdAudio.SAMPLE_RATE);
        }
        return a;
    }


    public static void playTone(double hz, double duration) {

        // create the array
        double[] a = tone(hz, duration);

        // play it using standard audio
        StdAudio.play(a);
    }
}
