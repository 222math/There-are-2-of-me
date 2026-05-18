package CloneGame.Engine.Recording;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.List;
import CloneGame.Engine.Objects.PressurePlate;

public class Replay {
    private List<Vector2> positions;
    private Texture texture;
    private int currentFrame = 0;
    private float timer = 0;
    private float frameDelay = 0.05f;
    private boolean playing = true;

    private List<Integer> plateIds;
    private List<Boolean> plateStates;
    private List<Float> plateTimes;
    private List<PressurePlate> plates;
    private List<Boolean> facingRightList;
    private List<Boolean> isWalkingList;
    private List<Boolean> isJumpingList;

    public Replay(List<Vector2> positions, List<PressurePlate> plates,
                  List<Integer> plateIds, List<Boolean> plateStates,
                  List<Float> plateTimes, List<Boolean> facingRightList,
                  List<Boolean> isWalkingList, List<Boolean> isJumpingList,
                  String pathToTexture) {
        this.positions = new ArrayList<>(positions);
        this.texture = new Texture(pathToTexture);
        this.plates = plates;
        this.plateIds = new ArrayList<>(plateIds);
        this.plateStates = new ArrayList<>(plateStates);
        this.plateTimes = new ArrayList<>(plateTimes);
        this.facingRightList = new ArrayList<>(facingRightList);
        this.isWalkingList = new ArrayList<>(isWalkingList);
        this.isJumpingList = new ArrayList<>(isJumpingList);
        this.currentFrame = 0;
        this.timer = 0;
        this.playing = true;
    }

    public void update(float delta) {
        if (!playing) return;
        timer += delta;
        if (timer >= frameDelay) {
            timer = 0;
            currentFrame++;
            if (currentFrame >= positions.size()) {
                playing = false;
            }
        }
    }

    public void updPlate(float gameTime) {

        for (PressurePlate plate : plates) {
            plate.setPressedByClone(false);
        }

        for (int i = 0; i < plateIds.size(); i++) {

            if (plateTimes.get(i) <= gameTime) {

                int id = plateIds.get(i);
                boolean state = plateStates.get(i);

                for (PressurePlate plate : plates) {

                    if (plate.getId() == id) {
                        plate.setPressedByClone(state);
                    }
                }
            }
        }
    }

    public Vector2 getCurrentPosition() {
        if (currentFrame >= 0 && currentFrame < positions.size()) {
            return positions.get(currentFrame);
        }
        return null;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public boolean getFacingRight(int frame) {
        if (frame >= 0 && frame < facingRightList.size()) return facingRightList.get(frame);
        return true;
    }

    public boolean getIsWalking(int frame) {
        if (frame >= 0 && frame < isWalkingList.size()) return isWalkingList.get(frame);
        return false;
    }

    public boolean getIsJumping(int frame) {
        if (frame >= 0 && frame < isJumpingList.size()) return isJumpingList.get(frame);
        return false;
    }

    public Texture getTexture() { return texture; }
    public boolean isPlaying() { return playing; }

    public void dispose() {
        if (texture != null) texture.dispose();
    }
}
