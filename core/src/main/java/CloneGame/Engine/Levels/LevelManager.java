package CloneGame.Engine.Levels;

import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.List;

public class LevelManager {
    List<Class<? extends Level>> levelClasses;
    private int currentLevelIndex;
    private  Level currentLevel;

    public LevelManager(){
        levelClasses = new ArrayList<>();


        levelClasses.add(Level1.class);
        levelClasses.add(Level2.class);
        levelClasses.add(Level3.class);
    }

    public Level loadLevel(int index , World world){
        if (index < 0 || index > levelClasses.size()) return null;
        try {
            currentLevel = levelClasses.get(index - 1).getDeclaredConstructor().newInstance();
            currentLevel.create(world);
            currentLevelIndex = index;
            return currentLevel;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Level loadNextLevel(World world) {
        return loadLevel(currentLevelIndex + 1, world);
    }

    public List<Class<? extends Level>> getLevelClasses() {
        return levelClasses;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public int getCurrentLevelIndex() {
        return currentLevelIndex;
    }
}
