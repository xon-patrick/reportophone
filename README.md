# Reportophone - Java Audio Recorder & Player

A simple Java desktop project for recording, playing and managing audio files.

---

## Description

Reportophone is a simple desktop application written in Java that allows:

- Audio recording from microphone
- Pause and resume recording
- Save audio files in a dedicated folder
- Play existing audio files
- Delete audio files

The project is developed for a university Java course and is designed to be easily extendable with features like:

- Custom playlists
- Genres for audio files
- Favorites
- Playback and recording timers

---

## Project Structure
```bash
src/
├─ Main.java
├─ audioManipulation/
│ ├─ AudioRecorder.java
│ └─ AudioPlayer.java
└─ audioFiles/ (folder where all recordings are saved)
```

- `Main.java` - main entry point, simple terminal interface
- `audioManipulation/AudioRecorder.java` - audio recording logic
- `audioManipulation/AudioPlayer.java` - audio playback logic
- `audioFiles/` - folder for saved recordings (.wav)

---

## Requirements

- Java JDK 11 or newer
- Functional microphone on your computer

---

## Compilation and Running

1. Navigate to the `src/` folder.
2. Compile all files in the project.
3. Run the application.
```bash
cd src
javac Main.java .\audioManipulation\*.java
java Main
```
---

## Functionality - only termin atp
- **1. Start Recording**: starts recording audio into a temporary file (`tempRec.wav`)
- **2. Stop Recording**: stops recording and asks to save or discard
- **3. Play Audio File**: plays a recording from the `audioFiles/` folder
- **4. Stop Audio Playback**: stops current audio playback
- **5. Exit Program**: exits the application

---

## Saving and Managing Files

- Temporary recordings are saved in `audioFiles/tempRec.wav`.
- After stopping recording, you can give a **custom filename** for saving.  
- If no name is provided, a **timestamp** is used automatically.
- Files can be deleted immediately if not needed.

---

## Future Extensions

Planned future features:

- **GUI**: visually pleasing GUI for navigating the app
- **Playlists**: organize audio files into custom playlists
- **Genres**: categorize files by type or genre
- **Favorites**: mark and filter favorite files
- **Graphical Interface**: moving from terminal to GUI with Swing or JavaFX
- **Timer**: show recording and playback duration
- **Pause/Resume**: full pause/resume for both recording and playback

---

## Code Structure

- `AudioRecorder`:
  - `startRecording()` - starts recording to temporary file
  - `stopRecording()` - stops recording
  - `pauseRecording()` / `resumeRecording()` - pause and resume
  - `getTempFile()` - returns temporary file for saving

- `AudioPlayer`:
  - `startPlayback(String filepath)` - starts playback of a file
  - `pausePlayback()` / `resumePlayback()` - pause and resume
  - `stopPlayback()` - stops playback

- `Main`:
  - Terminal interface
  - Menu navigation
  - File saving and deletion
  - Calls methods from `AudioRecorder` and `AudioPlayer`

---

## Notes

- All audio files are saved in **WAV format**.
- Folder `audioFiles/` is created automatically if it does not exist.
- Project is modular and ready for GUI or advanced functionality.

---

