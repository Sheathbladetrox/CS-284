/*
 * Instrument.java
 * Andrew Chuah
 * I pledge my honor that I have abided by the Stevens Honor System.
 */
package assign3;

public abstract class Instrument {
    
    protected InstString[] strings;
    
    public void playNote(int i){
        strings[i].pluck();
    }

    public double ringNotes(){
        double sample = 0;
        for(int i = 0; i < strings.length; i++){
            strings[i].tic();
            sample += strings[i].sample();
        }
        return sample;
    }

}
