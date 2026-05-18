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
    List<Boolean> facingRightList = new ArrayList<>();
    List<Boolean> isWalkingList = new ArrayList<>();
    List<Boolean> isJumpingList = new ArrayList<>();

    public void recordingPos(float x, float y, float delta, float gameTime,
                             boolean isWalking, boolean isJumping, boolean facingRight) {
        recordTimer += delta;
        if (recordTimer >= recordInterval) {
            recordTimer = 0;
            positions.add(new Vector2(x, y));
            isWalkingList.add(isWalking);
            isJumpingList.add(isJumping);
            facingRightList.add(facingRight);
        }
    }

    public void recordingPlate(int id, boolean state, float gameTime) {
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
    public List<Boolean> getFacingRightList() { return facingRightList; }
    public List<Boolean> getIsWalkingList() { return isWalkingList; }
    public List<Boolean> getIsJumpingList() { return isJumpingList; }

    public void deleteRecord() {
        positions.clear();
        plateIds.clear();
        plateStates.clear();
        plateTimes.clear();
        facingRightList.clear();
        isWalkingList.clear();
        isJumpingList.clear();
        recordTimer = 0;
    }
}
