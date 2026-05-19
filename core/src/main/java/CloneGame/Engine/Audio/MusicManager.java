package CloneGame.Engine.Audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicManager {

    private static Music currentMusic;

    private static float volume = 0.3f;

    public static void playMenuMusic() {

        play("music/menu.mp3", true);
    }

    public static void playGameMusic() {

        play("music/game.mp3", true);
    }

    public static void playReplayMusic() {

        play("music/replay.mp3", true);
    }

    private static void play(String path, boolean loop) {

        stop();

        currentMusic =
            Gdx.audio.newMusic(
                Gdx.files.internal(path)
            );

        currentMusic.setLooping(loop);

        currentMusic.setVolume(volume);

        currentMusic.play();
    }

    public static void stop() {

        if (currentMusic != null) {

            currentMusic.stop();

            currentMusic.dispose();

            currentMusic = null;
        }
    }

    public static void setVolume(float newVolume) {

        volume = newVolume;

        if (currentMusic != null) {
            currentMusic.setVolume(volume);
        }
    }

    public static float getVolume() {
        return volume;
    }

    public static void dispose() {

        stop();
    }
}
