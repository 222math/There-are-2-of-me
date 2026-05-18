package CloneGame.Engine.Levels;

import static CloneGame.Engine.Main.GameResources.PLATE_ACTIVATED_IMG_PATH;
import static CloneGame.Engine.Main.GameResources.PLATE_NOT_ACTIVATED_IMG_PATH;
import static CloneGame.Engine.Main.GameResources.PLATFORM_IMG_PATH;
import static CloneGame.Engine.Main.GameResources.PORTAL_IMG_PATH;
import static CloneGame.Engine.Main.GameSettings.SCREEN_WIDTH;

import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

import CloneGame.Engine.Objects.Door;
import CloneGame.Engine.Objects.Platform;
import CloneGame.Engine.Objects.Portal;
import CloneGame.Engine.Objects.PressurePlate;

public class Level0 extends Level{

    public Level0() {
        super(1);
    }

    @Override
    public  void create(World world){
        playerSpawnX = 600;
        playerSpawnY = 25;

        platforms = new ArrayList<>();
        platforms.add(new Platform(PLATFORM_IMG_PATH , 0 , 0  , SCREEN_WIDTH , 25 , world));
        platforms.add(new Platform(PLATFORM_IMG_PATH , 200 , 125  , 400 , 25 , world));

        plates = new ArrayList<>();
        plates.add(new PressurePlate(PLATE_NOT_ACTIVATED_IMG_PATH , PLATE_ACTIVATED_IMG_PATH , 300 , 140, 50 , 10 , world , 1));

        doors = new ArrayList<>();
        //doors.add(new Door(PLATFORM_IMG_PATH , 0 , 0  , SCREEN_WIDTH , 25 , world , 1));

        portal = new Portal(PORTAL_IMG_PATH , 0 , 25 , 25 , 100 , world);
    }
}
