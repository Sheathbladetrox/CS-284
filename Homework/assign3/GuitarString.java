/*
 * GuitarString.java
 * Andrew Chuah
 * I pledge my honor that I have abided by the Stevens Honor System.
 */

package assign3;
import java.lang.Math;

public class GuitarString extends InstString{

    public GuitarString(double frequency) {
        count = 0;
        int N = (int)Math.round(44100 / frequency);
        newBuffer = new RingBuffer(N);
        while(!newBuffer.isFull()){
            newBuffer.enqueue(0);
        }
    }

    public GuitarString(double[] init) {
        count = 0;
        newBuffer = new RingBuffer(init.length);
        for(int i = 0; i < init.length; i++){
            newBuffer.enqueue(init[i]);
        }
    }
   
    public void pluck() {	
        while(!newBuffer.isEmpty()){
            newBuffer.dequeue();
        }
        while(!newBuffer.isFull()){
            newBuffer.enqueue(Math.random()*(1) - 0.5);
        }
    }
   
    public void tic() {
        double first = newBuffer.dequeue();
        double second = newBuffer.peek();
        double average = (first + second)/2;

        newBuffer.enqueue(average * decayFactor);
        count++;
    }

    /*
    public static void main (String[] args) {
        int N = Integer.parseInt(args[0]);
        double[] samples = {.2,.4,.5,.3,-.2,.4,.3,.0,-.1,-.3};
        GuitarString testString = new GuitarString(samples);
        for(int i = 0; i < N; i++) {
            int t = testString.time();
            double sample = testString.sample();
            System.out.printf("%6d %8.4f\n", t, sample);
            testString.tic();
        }
    }
    */
}
