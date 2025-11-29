import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import javax.sound.sampled.LineUnavailableException;

public class Main {
    public static void main(String[] args) {
        AudioRecorder recorder = new AudioRecorder();
        Scanner scanner = new Scanner(System.in);
        File tempFile = null;

        boolean running = true;

        while (running) {
            System.out.println("\n ===REPORTOPHONE===");
            System.out.println("1. Start Recording");
            System.out.println("2. Stop Recording");
            System.out.println("3. Exit Program");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    try {
                        tempFile = recorder.startRecording();
                    } catch (LineUnavailableException | IOException e) {
                        System.err.println(e.getMessage());
                    }
                    break;

                case "2":
                    if (tempFile != null) {
                        recorder.stopRecording();

                        // Ask user what to do with recording
                        System.out.println("Do you want to save the recording? (y/n)");
                        String choice = scanner.nextLine();
                        if (choice.equalsIgnoreCase("y")) {
                            System.out.print("Enter a filename (without extension): ");
                            String filename = scanner.nextLine().trim();
                            if (filename.isEmpty()) {
                                filename = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
                            }
                            File savedFile = new File(filename + ".wav");
                            if (tempFile.renameTo(savedFile)) {
                                System.out.println("Recording saved as: " + savedFile.getAbsolutePath());
                            } else {
                                System.err.println("Error saving file.");
                            }
                        } else {
                            // Delete temp file
                            if (tempFile.delete()) {
                                System.out.println("Recording discarded.");
                            } else {
                                System.err.println("Error deleting temp file.");
                            }
                        }

                        tempFile = null;
                    } else {
                        System.out.println("No recording in progress.");
                    }
                    break;

                case "3":
                    if (tempFile != null) {
                        recorder.stopRecording();
                        tempFile.delete();
                    }
                    running = false;
                    System.out.println("Exiting Program");
                    break;

                default:
                    System.out.println("Invalid input");
            }
        }

        scanner.close();
    }
}

