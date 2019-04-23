/*
 * RockBand.java
 * Andrew Chuah
 * I pledge my honor that I have abided by the Stevens Honor System.
 */

package assign3;
import cos126.StdDraw;
import cos126.StdAudio;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class RockBand {

    String guitarBassKeyboard ="`1234567890-=qwertyuiop[]\\asdfghjkl;'";
    String pianoKeyboard = "~!@#$%^&*()_+QWERTYUIOP{}|ASDFGHJKL:\"";
    String drumKeyboard = "ZXCVBNM<>?zxcvbnm,.";

    private static Scanner song;

    public static void main(String[] args) {
        Instrument[] orchestra = new Instrument[4];
        orchestra[0] = new Guitar(37);
        orchestra[1] = new Piano(37);
        orchestra[2] = new Bass(37);
        orchestra[3] = new Drum(19);

        RockBand string = new RockBand();
        boolean LowMode = false;
        int speed = 11000;

        //If no file was passed as input/argument:
        if(args.length == 0){
            while(true){
                if(StdDraw.hasNextKeyTyped()){
                    char key = StdDraw.nextKeyTyped();
                    int GBK = string.guitarBassKeyboard.indexOf(key);
                    int DK = string.drumKeyboard.indexOf(key);
                    int PK = string.pianoKeyboard.indexOf(key);

                    if(Character.toString(key).equals("\n"))
                        LowMode = !LowMode;

                    if(GBK != -1 && DK == -1 && PK == -1){
                        if(LowMode == true) {
                            orchestra[2].playNote(GBK);
                        }
                        else {
                            orchestra[0].playNote(GBK);
                        }
                    }
                    else if(GBK == -1 && DK != -1 && PK == -1){
                        orchestra[3].playNote(DK);
                    }
                    else if(GBK == -1 && DK == -1 && PK != -1){
                        orchestra[1].playNote(PK);
                    }
                }
                double sumOfAllInstrumentSamples = 0;
                sumOfAllInstrumentSamples += orchestra[2].ringNotes();
                sumOfAllInstrumentSamples += orchestra[0].ringNotes();
                sumOfAllInstrumentSamples += orchestra[3].ringNotes();
                sumOfAllInstrumentSamples += orchestra[1].ringNotes();

                StdAudio.play(sumOfAllInstrumentSamples);
            }
        }
        //If a file was passed as input/argument:
        else if(args[0].equals("-play_from_file")){
            try{
                String fileName = args[1];
                song = new Scanner(new File(fileName));
            }
            catch(IOException e){
                System.err.println("Cannot read file.");
                System.err.println(e.getMessage());
                System.exit(1);
            }

            while(song.hasNext()){
                String note = song.next();

                //Enables LowMode
                if(note.contains("LL")) {
                    LowMode = true;
                }

                //Outputs any lyrics/titles
                if(note.contains("@@")){
                    System.out.println(note.substring(2));
                }

                //Kinda already checks for rests ("/")
                for(int i = 0; i < note.length(); i++){
                    char key = note.charAt(i);
                    int GBK = string.guitarBassKeyboard.indexOf(key);
                    int DK = string.drumKeyboard.indexOf(key);
                    int PK = string.pianoKeyboard.indexOf(key);

                    //Disables LowMode if the character is a "/" in a LL word
                    if(Character.toString(key).equals("/") && note.contains("LL")){
                        LowMode = false;
                    }

                    if(!(note.contains("##") || note.contains("@@"))) {
                        if (GBK != -1 && DK == -1 && PK == -1) {
                            if (LowMode == true) {
                                orchestra[2].playNote(GBK);
                            } else {
                                orchestra[0].playNote(GBK);
                            }
                        } else if (GBK == -1 && DK != -1 && PK == -1) {
                            orchestra[3].playNote(DK);
                        } else if (GBK == -1 && DK == -1 && PK != -1) {
                            orchestra[1].playNote(PK);
                        }
                    }
                }

                //Changes tempo speed
                if(note.contains("##")){
                    int tempo = Integer.parseInt(note.substring(2));
                    speed = tempo;
                }

                for (int i = 0; i < speed; i++) {
                    double sumOfAllInstrumentSamples = 0;

                    sumOfAllInstrumentSamples += orchestra[2].ringNotes();
                    sumOfAllInstrumentSamples += orchestra[0].ringNotes();
                    sumOfAllInstrumentSamples += orchestra[1].ringNotes();
                    sumOfAllInstrumentSamples += orchestra[3].ringNotes();
                    StdAudio.play(sumOfAllInstrumentSamples);
                }
            }
        }
    }
}
