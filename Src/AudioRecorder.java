import javax.sound.sampled.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AudioRecorder {
    private TargetDataLine line;
    private Thread recordingThread;
    private AudioFileFormat.Type targetType = AudioFileFormat.Type.WAVE;
    private AudioFormat format;
    private File tempFile;

    public AudioRecorder() {
        // 44.1kHz, 16-bit, mono, signed, little-endian
        format = new AudioFormat(44100f, 16, 1, true, false);
    }

    public File startRecording() throws IOException, LineUnavailableException {
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        if (!AudioSystem.isLineSupported(info)) {
            throw new IOException("Microphone line not supported");
        }

        line = (TargetDataLine) AudioSystem.getLine(info);
        line.open(format);
        line.start();

//        String timestamp = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date());
//        File outFile = new File("recording_" + timestamp + ".wav");

        // pentru optiunea de salvare/stergere
        tempFile = new File("tempRec.wav");

        recordingThread = new Thread(() -> {
            try (AudioInputStream ais = new AudioInputStream(line)) {
                AudioSystem.write(ais, targetType, tempFile);
//                System.out.println("Recording saved at: " + outFile.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("Error while writing file: " + e.getMessage());
            }
        });

        recordingThread.start();
        System.out.println("Recording started...");
//        return outFile;
        return tempFile;
    }

    public void stopRecording() {
        if (line != null) {
            line.stop();
            line.close();
            line = null;
        }

        if (recordingThread != null) {
            try {
                recordingThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            recordingThread = null;
        }

        System.out.println("Recording stopped.");
    }

    public File getTempFile() {
        return tempFile;
    }
}
