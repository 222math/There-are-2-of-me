package CloneGame.Engine.Recording;

import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.List;

public class Record {
    private float recordTimer = 0;
    private float recordInterval = 0.05f;

    List<Vector2> positions = new ArrayList<>();
    List<Integer> plateIds = new ArrayList<>();
    List<Boolean> plateStates = new ArrayList<>();
    List<Float> plateTimes = new ArrayList<>();

    public void recordingPos(float x, float y, float delta, float gameTime) {
        recordTimer += delta;
        if (recordTimer >= recordInterval) {
            recordTimer = 0;
            positions.add(new Vector2(x, y));
        }
    }

    public void recordingPlate(int id, boolean state, float gameTime) {
        // Записываем только изменения состояния
        if (!plateStates.isEmpty()) {
            int lastIdx = plateStates.size() - 1;
            if (plateIds.get(lastIdx) == id && plateStates.get(lastIdx) == state) {
                return;
            }
        }
        plateIds.add(id);
        plateStates.add(state);
        plateTimes.add(gameTime);
    }

    public List<Vector2> getPositions() { return positions; }
    public List<Integer> getPlateIds() { return plateIds; }
    public List<Boolean> getPlateStates() { return plateStates; }
    public List<Float> getPlateTimes() { return plateTimes; }

    public void deleteRecord() {
        positions.clear();
        plateIds.clear();
        plateStates.clear();
        plateTimes.clear();
        recordTimer = 0;
    }
}
