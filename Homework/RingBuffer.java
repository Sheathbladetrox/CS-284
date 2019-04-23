/*****************************************************************************
 *
 *  This is a template file for RingBuffer.java. It lists the constructors and
 *  methods you need, along with descriptions of what they're supposed to do.
 *  
 *  Note: it won't compile until you fill in the constructors and methods
 *        (or at least comment out the ones whose return type is non-void).
 *
 *****************************************************************************/

// Andrew Chuah
// I pledge my honor that I have abided by the Stevens Honor System.

public class RingBuffer {
    private int first;            // index of first item in buffer
    private int last;             // index of last item in buffer
    private int size;             // current number of items of buffer
    private double[] buffer;

    // create an empty buffer, with given max capacity
    public RingBuffer(int capacity) {
        buffer = new double[capacity];
        first = 0;
        last = 0;
        size = 0;
    }

    // return number of items currently in the buffer
    public int getSize() {
        return size;
    }

    // is the buffer empty (size equals zero)?
    public boolean isEmpty() {
        return size == 0;
    }

    // is the buffer full (size equals array capacity)?
    public boolean isFull() {
        return size == buffer.length;
    }

    // add item x to the end
    public void enqueue(double x) {
        if (isFull()) { throw new RuntimeException("Ring buffer overflow"); }
        buffer[last] = x;
        last = (last + 1) % buffer.length;
        size++;
    }

    // delete and return item from the front
    public double dequeue() {
        if (isEmpty()) { throw new RuntimeException("Ring buffer underflow"); }
        double item = buffer[first];
        first = (first + 1) % buffer.length;
        size--;
        return item;
    }

    // return (but do not delete) item from the front
    public double peek() {
        if (isEmpty()) { throw new RuntimeException("Ring buffer underflow"); }
        return buffer[first];
    }

    // a simple test of the constructor and methods in RingBuffer
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        RingBuffer buffer = new RingBuffer(N);
        for (int i = 1; i <= N; i++) {
            buffer.enqueue(i);
        }
		double t = buffer.dequeue();
		buffer.enqueue(t);
		System.out.println("Size after wrap-around is " 
				   + buffer.getSize());
        while (buffer.getSize() >= 2) {
            double x = buffer.dequeue();
            double y = buffer.dequeue();
            buffer.enqueue(x + y);
        }
        System.out.println(buffer.peek());
    }
}



