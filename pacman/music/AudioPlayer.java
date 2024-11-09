package pacman.music;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer {

    private final Clip clip;
    String absolutePath;
    boolean audioPicked;
    boolean noMusic;

    public AudioPlayer(String absolute, boolean picked, boolean noMusic) {
        absolutePath = absolute;
        audioPicked = picked;
        this.noMusic = noMusic;
        clip = playAudio(absolutePath);
    }

    /**
     * Calls pickAudio() and uses the obtained AudioInputStream to update the clip.
     */
    public Clip playAudio(String absolutePath){
        //early return in case the choice is no music.
        if(noMusic){
        audioPicked = true;
        return null;
        }
        AudioInputStream chosen = pickAudio(absolutePath);
        Clip clip = null;

        try {
            clip = AudioSystem.getClip();
            try {
                clip.open(chosen);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } catch (LineUnavailableException e) {
            JOptionPane.showMessageDialog(null, "LineUnavailableException, be sure to not be playing another audio");
        }
        return clip;
    }

    /**
     * Is called by play audio to get AudioInputStream
     * @param absolutePath The absolute path of the chosen song
     * @return the converted AudioInputStream
     */
    private AudioInputStream pickAudio(String absolutePath){
        AudioInputStream inputAudio = null;
        try {
            inputAudio = AudioSystem.getAudioInputStream(new File(absolutePath).getAbsoluteFile());
            audioPicked = true;

        } catch (UnsupportedAudioFileException u) {
            JOptionPane.showMessageDialog(null,"UnsupportedAudioFile, make sure that the audio file is in .wav format");

        } catch (IOException | NullPointerException i) {
            return null;

        }

        return inputAudio;
    }

    public Clip getClip() {
        return clip;
    }
}

