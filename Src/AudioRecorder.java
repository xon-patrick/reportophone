import javax.sound.sampled.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AudioRecorder {
    private TargetDataLine line;
    private Thread recordingThread;
    private AudioFileFormat.Type targetType = AudioFileFormat.Type.WAVE;
    private AudioFormat format;

    public AudioRecorder() {
        // Sample rate 44kHz, 16-bit, mono, signed, little-endian
        format = new AudioFormat(44100f, 16, 1, true, false);
    }

    public File startRecording() throws IOException, LineUnavailableException {

        // testare microfon
//        Mixer.Info micInfo = AudioSystem.getMixerInfo()[7];
//        Mixer mixer = AudioSystem.getMixer(micInfo);
//
//        System.out.println("Using microphone: " + micInfo.getName());

        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

        if (!AudioSystem.isLineSupported(info)) {
            throw new IOException("Microphone line not supported");
        }

        line = (TargetDataLine) AudioSystem.getLine(info);
        line.open(format);
        line.start();

        String timestamp = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date());
        File outFile = new File("recording_" + timestamp + ".wav");

        recordingThread = new Thread(() -> {
            try (AudioInputStream ais = new AudioInputStream(line)) {
                System.out.println("Recording started...");
                AudioSystem.write(ais, targetType, outFile);
                System.out.println("Recording saved at: " + outFile.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("Error while writing file: " + e.getMessage());
            }
        });

        recordingThread.start();
        return outFile;
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


    public static void main(String[] args) {
        AudioRecorder recorder = new AudioRecorder();
        File savedFile = null;

        try {
            // START RECORDING ONCE
            savedFile = recorder.startRecording();
            Thread.sleep(5000); // record for 5 seconds
            recorder.stopRecording();

        } catch (IOException | LineUnavailableException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        if (savedFile != null && savedFile.exists()) {
            System.out.println("Saved recording to: " + savedFile.getAbsolutePath());
        } else {
            System.out.println("No file saved.");
        }
    }
}
