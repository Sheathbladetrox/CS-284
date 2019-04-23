/*
 * Piano.java
 * Andrew Chuah
 * I pledge my honor that I have abided by the Stevens Honor System.
 */

package assign3;


public class Piano extends Instrument{

    private static InstString[][] pStrings; //2D array of strings
    
    public Piano(int numNotes){
        pStrings = new InstString[numNotes][3];
        for(int i = 0; i < numNotes; i++){
            for(int j = 0; j < 3; j++){
                if(j == 0)
                    pStrings[i][j] = new PianoString((440*(Math.pow(2,(double)(i-24)/12)) - 0.45));
                else if(j == 1)
                    pStrings[i][j] = new PianoString((440*(Math.pow(2,(double)(i-24)/12))));
                else
                    pStrings[i][j] = new PianoString((440*(Math.pow(2,(double)(i-24)/12)) + 0.45));
            }
        }
    }
  
    public void playNote(int index){
        for(int i = 0; i < 3; i++){
            pStrings[index][i].pluck();
        }
    }
    
    public double ringNotes(){
        double sample = 0;
        for(int i = 0; i < pStrings.length; i++){
            for(int j = 0; j < 3; j++){
                pStrings[i][j].tic();
                sample += pStrings[i][j].sample();
            }
        }
        return sample;
    }
			    

}
