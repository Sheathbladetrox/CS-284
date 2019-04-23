/*
 * InstString.java
 * Andrew Chuah
 * I pledge my honor that I have abided by the Stevens Honor System.
 */

package assign3;

public abstract class InstString{

    /* To be implemented by subclasses*/
    public abstract void pluck();
    public abstract void tic();

    public RingBuffer newBuffer;
    public int count;
    static final double decayFactor = 0.996;

    public double sample(){
        return newBuffer.peek();
    }

    public int time(){
        return count;
    }
}
