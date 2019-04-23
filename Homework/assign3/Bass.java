/*
 * Bass.java
 * Andrew Chuah
 * I pledge my honor that I have abided by the Stevens Honor System.
 */
package assign3;

public class Bass extends Instrument{


    public Bass(int numNotes){
        strings = new InstString[numNotes];
        for(int i = 0; i < numNotes; i++){
            strings[i] = new GuitarString(440*(Math.pow(2,(double)(i-48)/12)));
        }
    }		    
}
