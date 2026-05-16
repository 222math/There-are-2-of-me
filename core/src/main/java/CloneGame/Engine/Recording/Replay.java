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

    public Replay(List<Vector2> positions, List<PressurePlate> plates,
                  List<Integer> plateIds, List<Boolean> plateStates,
                  List<Float> plateTimes, String pathToTexture) {
        // КОПИРУЕМ списки, чтобы не зависеть от Record
        this.positions = new ArrayList<>(positions);
        this.texture = new Texture(pathToTexture);
        this.plates = plates;
        this.plateIds = new ArrayList<>(plateIds);
        this.plateStates = new ArrayList<>(plateStates);
        this.plateTimes = new ArrayList<>(plateTimes);
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

    public void updPlate(float currentTime) {
        for (PressurePlate plate : plates) {
            boolean state = false;
            float lastTime = -1;

            for (int i = 0; i < plateTimes.size(); i++) {
                if (plateIds.get(i) == plate.getId() && plateTimes.get(i) <= currentTime) {
                    if (plateTimes.get(i) > lastTime) {
                        state = plateStates.get(i);
                        lastTime = plateTimes.get(i);
                    }
                }
            }

            plate.setActivated(state);
        }
    }

    public Vector2 getCurrentPosition() {
        if (currentFrame >= 0 && currentFrame < positions.size()) {
            return positions.get(currentFrame);
        }
        return null;
    }

    public Texture getTexture() { return texture; }
    public boolean isPlaying() { return playing; }

    public void dispose() {
        if (texture != null) texture.dispose();
    }
}
