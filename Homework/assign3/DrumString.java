/*
 * DrumString.java
 * Andrew Chuah
 * I pledge my honor that I have abided by the Stevens Honors System.
 */

package assign3;
import java.lang.Math;


public class DrumString extends InstString{

   
    public DrumString(double frequency) {
        count = 0;
        int N = (int)Math.round(44100 / frequency);
        newBuffer = new RingBuffer(N);
        while(!newBuffer.isFull()){
            newBuffer.enqueue(0);
        }
    }

    public DrumString(double[] init) {
        count = 0;
        newBuffer = new RingBuffer(init.length);
        for(int i = 0; i < init.length; i++){
            newBuffer.enqueue(init[i]);
        }
    }

    public void pluck() {
        double N = newBuffer.getSize();
        while(!newBuffer.isEmpty()){
            newBuffer.dequeue();
        }
        for(int i = 0; i < N; i++){
            if(Math.sin(i) > 0){
                newBuffer.enqueue(1);
            }
            else{
                newBuffer.enqueue(-1);
            }
        }
    }
    
    public void tic() {
        double s1 = newBuffer.dequeue();
        double s2 = newBuffer.peek();
        double average = (s1 + s2)/2;
        double randomIntOuter = Math.random();
        double randomIntInner = Math.random();

        if(randomIntOuter <= 0.6){
            if(randomIntInner <= 0.5){
                newBuffer.enqueue(average * decayFactor);
                count++;
            }
            else if(randomIntInner > 0.5){
                newBuffer.enqueue(-(average * decayFactor));
                count++;
            }
        }
        else if(randomIntOuter > 0.6){
            if(randomIntInner <= 0.5){
                newBuffer.enqueue(s1);
                count++;
            }
            else if(randomIntInner > 0.5){
                newBuffer.enqueue(-s1);
                count++;
            }
        }
    }

    /*
    public static void main (String[] args) {
        int N = Integer.parseInt(args[0]);
        double[] samples = {.2,.4,.5,.3,-.2,.4,.3,.0,-.1,-.3};
        DrumString testString = new DrumString(samples);
        for(int i = 0; i < N; i++) {
            int t = testString.time();
            double sample = testString.sample();
            System.out.printf("%6d %8.4f\n", t, sample);
            testString.tic();
        }
    }
    */
}
