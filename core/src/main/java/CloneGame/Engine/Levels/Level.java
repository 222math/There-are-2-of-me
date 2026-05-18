package CloneGame.Engine.Levels;

import com.badlogic.gdx.physics.box2d.World;

import java.util.List;

import CloneGame.Engine.Objects.Door;
import CloneGame.Engine.Objects.Platform;
import CloneGame.Engine.Objects.Portal;
import CloneGame.Engine.Objects.PressurePlate;

public class Level {
    protected int levelNumber;
    protected float playerSpawnX;
    protected float playerSpawnY;
    protected List<Platform> platforms;
    protected List<PressurePlate> plates;
    protected List<Door> doors;
    protected Portal portal;
    protected float worldWidth;
    protected float worldHeight;

    public Level(int levelNumber){
        this.levelNumber = levelNumber;
    }
    public void create(World world){}

    public float getPlayerSpawnX() {
        return playerSpawnX;
    }

    public float getPlayerSpawnY() {
        return playerSpawnY;
    }

    public int getLevelNumber() {
        return levelNumber;
    }
    public List<Platform> getPlatforms() {
        return platforms;
    }

    public List<PressurePlate> getPlates() {
        return plates;
    }

    public List<Door> getDoors() {
        return doors;
    }

    public Portal getPortal() {
        return portal;
    }
}
