package audioManipulation;

import javax.sound.sampled.*;
import java.io.*;
import java.io.IOException;

public class AudioPlayer {
    private Clip audioClip;

    public void startPlayback(String filepath) throws IOException , UnsupportedAudioFileException, LineUnavailableException {
        File audioFile = new File(filepath);

        if (!audioFile.exists()){
            throw new IOException("file does not exist:" + filepath);
        }

        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

        audioClip = AudioSystem.getClip();
        audioClip.open(audioStream);

        audioClip.start();
        System.out.println("playing: " + filepath);
    }

    public void stopPlayback() {
        if (audioClip != null && audioClip.isRunning()) {
            audioClip.stop();
            audioClip.close();
            System.out.println("stopped playback");
        }
    }

    public void pausePlayback() {
        if (audioClip != null && audioClip.isRunning()) {
            audioClip.stop();
            System.out.println("Playback paused.");
        }
    }

    public void resumePlayback() {
        if (audioClip != null && !audioClip.isRunning()) {
            audioClip.start();
            System.out.println("Playback resumed.");
        }
    }
}