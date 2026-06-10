package CloneGame.Engine.Audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {

    private static Sound jumpSound;
    private static Sound plateSound;
    private static Sound doorSound;
    private static float volume = 1f;
    private static Sound portalSound;

    public static void load() {

        jumpSound =
            Gdx.audio.newSound(
                Gdx.files.internal("sounds/jump.mp3")
            );

        plateSound =
            Gdx.audio.newSound(
                Gdx.files.internal("sounds/plate.mp3")
            );

        doorSound =
            Gdx.audio.newSound(
                Gdx.files.internal("sounds/door.mp3")
            );

//        portalSound =
//            Gdx.audio.newSound(
//                Gdx.files.internal("sounds/portal.mp3")
//            );
    }

    public static void playJump() {

        if (jumpSound != null) {
            jumpSound.play(volume);
        }
    }

    public static void playPlate() {

        if (plateSound != null) {
            plateSound.play(0.5f*volume);
        }
    }

    public static void playDoor() {

        if (doorSound != null) {
            doorSound.play(0.7f*volume);
        }
    }

    public static void playPortal() {

        if (portalSound != null) {
            portalSound.play(0.5f*volume);
        }
    }
    public static void setVolume(float newVolume){
        volume = newVolume;
    }

    public static float getVolume(){
        return volume;
    }

    public static void dispose() {

        if (jumpSound != null) {
            jumpSound.dispose();
        }

        if (plateSound != null) {
            plateSound.dispose();
        }

        if (doorSound != null) {
            doorSound.dispose();
        }

        if (portalSound != null) {
            portalSound.dispose();
        }
    }
}
