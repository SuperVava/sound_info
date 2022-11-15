import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;


public class PitchDetector {

    private volatile double freq;
    private volatile double closestFreq;
    private volatile String closestFreqName;
    private volatile static boolean run = true;

    public boolean started = false;

    private float sampleRate = 44100;
    private int sampleSizeInBits = 16;
    private int channels = 1;
    private boolean signed = true;
    private boolean bigEndian = false;
    private AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
    private DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, format);
    private TargetDataLine targetDataLine;

    private byte[] buffer = new byte[2*1200];
    private int[] a = new int[buffer.length/2];
    private int n = -1;

    public static float ERROR_MARGIN = 1;
    public static final double[] FREQUENCIES = {329.62 ,246.94,196.00,146.83,110.00, 82.41};
    private static final String[] NAME        = { "E",  "B",  "G", "D",  "A",  "E"};

    public PitchDetector() {
        try {
            targetDataLine = (TargetDataLine)AudioSystem.getLine(dataLineInfo);
            targetDataLine.open(format, (int)sampleRate);
            targetDataLine.start();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("Error with sound card : cannot open sound !");
            //e.printStackTrace();
        }


    }

    private static double normaliseFreq(double hz) {
        // get hz into a standard range to make things easier to deal with
        while ( hz < 82.41 ) {
            hz = 2*hz;
        }
        while ( hz > 340 ) {
            hz = 0.5*hz;
        }
        return hz;
    }

    private static int closestNote(double hz) {
        double minDist = Double.MAX_VALUE;
        int minFreq = -1;
        for ( int i = 0; i < FREQUENCIES.length; i++ ) {
            double dist = Math.abs(FREQUENCIES[i]-hz);
            if ( dist < minDist ) {
                minDist=dist;
                minFreq=i;
            }
        }

        return minFreq;
    }

    public void start() {
        Thread t = new Thread(new Runnable() {
            public void run() {
                while(run) {
                    while ( (n = targetDataLine.read(buffer, 0, buffer.length)) > 0 ) {

                        for ( int i = 0; i < n; i+=2 ) {
                            // convert two bytes into single value
                            int value = (short)((buffer[i]&0xFF) | ((buffer[i+1]&0xFF) << 8));
                            a[i >> 1] = value;
                        }

                        double prevDiff = 0;
                        double prevDx = 0;
                        double maxDiff = 0;

                        int sampleLen = 0;



                        int len = a.length/2;
                        for ( int i = 0; i < len; i++ ) {
                            double diff = 0;
                            for ( int j = 0; j < len; j++ ) {
                                diff += Math.abs(a[j]-a[i+j]);
                            }



                            double dx = prevDiff-diff;

                            // change of sign in dx
                            if ( dx < 0 && prevDx > 0 ) {
                                // only look for troughs that drop to less than 10% of peak
                                if ( diff < (0.1*maxDiff) ) {

                                    if ( sampleLen == 0 ) {
                                        sampleLen=i-1;
                                    }
                                }
                            }

                            prevDx = dx;
                            prevDiff=diff;
                            maxDiff=Math.max(diff,maxDiff);
                        }


                        if ( sampleLen > 0 )
                        {

                            double frequency = (format.getSampleRate()/sampleLen);


                            frequency = normaliseFreq(frequency);
                            int note = closestNote(frequency);
                            closestFreq = FREQUENCIES[note];
                            freq = frequency;
                            closestFreqName = NAME[note];
                            started = true;
                        }
                        else {
                            closestFreqName = "Unknow";
                        }


                        try {
                            Thread.sleep(100);
                        } catch(InterruptedException e){
                            e.printStackTrace();
                        }

                    }
                }
            }
        });
        t.start();
    }

    public synchronized double getFreq() {
        return this.freq;
    }

    public synchronized double getClosestFreq() {
        return closestFreq;
    }

    public synchronized String getClosestFreqName() {
        return closestFreqName;
    }

    public static void stop() {
        run = false;
        System.out.println("STOPPED !");
    }

}