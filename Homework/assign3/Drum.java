/*
 * Drum.java
 * Andrew Chuah
 * I pledge my honor that I have abided by the Stevens Honor System.
 */
package assign3;

public class Drum extends Instrument{

    public Drum(int numNotes){
        strings = new InstString[numNotes];
        for(int i = 0; i < numNotes; i++){
            strings[i] = new DrumString(440*(Math.pow(2,(double)(i-24)/12)));
        }
    }		    
}
