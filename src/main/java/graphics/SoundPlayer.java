package graphics;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class SoundPlayer {
    public static Clip backgroundMusic;

    public static synchronized void playSound(final String url) {
        new Thread(new Runnable() {
            // The wrapper thread is unnecessary, unless it blocks on the
            // Clip finishing; see comments.
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    String cwd = System.getProperty("user.dir");
                    System.out.println("Current working directory : " + cwd);
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            new File("src/main/resources/sounds/" + url));
                    clip.open(inputStream);
                    clip.start();
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        }).start();
    }

    public static synchronized void playInfinite(final String url) {
        // The wrapper thread is unnecessary, unless it blocks on the
// Clip finishing; see comments.
        new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                String cwd = System.getProperty("user.dir");
                System.out.println("Current working directory : " + cwd);
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                        new File("src/main/resources/sounds/" + url));
                clip.open(inputStream);
                if (backgroundMusic != null) {
                    backgroundMusic.stop();
                }
                backgroundMusic = clip;
                clip.loop(Integer.MAX_VALUE);
            } catch (Exception e) {
                System.err.println(e);
            }
        }).start();
    }

    public static void stopBackground() {
        if (backgroundMusic != null) {
            System.out.println("stopping");
            backgroundMusic.stop();
            backgroundMusic = null;
        }
    }
}