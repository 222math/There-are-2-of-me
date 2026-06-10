package CloneGame.Engine.Levels;

import static CloneGame.Engine.Main.GameResources.*;
import static CloneGame.Engine.Main.GameSettings.SCREEN_WIDTH;
import static CloneGame.Engine.Main.GameSettings.SCREEN_HEIGHT;

import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

import CloneGame.Engine.Objects.Door;
import CloneGame.Engine.Objects.Platform;
import CloneGame.Engine.Objects.Portal;
import CloneGame.Engine.Objects.PressurePlate;

public class Level1 extends Level {

    public Level1() {
        super(1);
    }

    @Override
    public void create(World world) {
        playerSpawnX = 600;
        playerSpawnY = 50;

        platforms = new ArrayList<>();
        doors = new ArrayList<>();
        plates = new ArrayList<>();

        // ========== ГРАНИЦЫ ==========
        platforms.add(new Platform(PLATFORM_IMG_PATH, -20, SCREEN_HEIGHT / 2, 20, SCREEN_HEIGHT + 100, world));

        platforms.add(new Platform(PLATFORM_IMG_PATH, SCREEN_WIDTH, SCREEN_HEIGHT / 2, 20, SCREEN_HEIGHT + 100, world));

        platforms.add(new Platform(PLATFORM_IMG_PATH, SCREEN_WIDTH / 2, SCREEN_HEIGHT + 20, SCREEN_WIDTH + 100, 20, world));

        platforms.add(new Platform(PLATFORM_IMG_PATH, SCREEN_WIDTH / 2, -20, SCREEN_WIDTH + 100, 20, world));



        platforms.add(new Platform(PLATFORM_IMG_PATH, SCREEN_WIDTH/2, 0, SCREEN_WIDTH, 25, world));


        platforms.add(new Platform(PLATFORM_IMG_PATH, 200, 100, 80, 25, world));
        platforms.add(new Platform(PLATFORM_IMG_PATH, 350, 180, 80, 25, world));
        platforms.add(new Platform(PLATFORM_IMG_PATH, 500, 260, 80, 25, world));


        plates.add(new PressurePlate(PLATE_NOT_ACTIVATED_IMG_PATH, PLATE_ACTIVATED_IMG_PATH,
            530, 275, 50, 12, world, 1));
        plates.add(new PressurePlate(PLATE_NOT_ACTIVATED_IMG_PATH, PLATE_ACTIVATED_IMG_PATH,
            800, 20, 50, 12, world, 2));


        platforms.add(new Platform(PLATFORM_IMG_PATH, 1000, 200, 550, 25, world));



        doors.add(new Door(DOOR_IMG_PATH, 825    , 100, 18, 200, world, 1, new int[]{1, 2}));




        portal = new Portal(PORTAL_IMG_PATH, 1000, 120, 50, 80, world);
        portal.setInPortal(false);
    }
}
