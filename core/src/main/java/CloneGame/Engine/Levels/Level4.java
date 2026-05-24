package CloneGame.Engine.Levels;

import static CloneGame.Engine.Main.GameResources.*;
import static CloneGame.Engine.Main.GameSettings.SCREEN_HEIGHT;
import static CloneGame.Engine.Main.GameSettings.SCREEN_WIDTH;

import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

import CloneGame.Engine.Objects.Door;
import CloneGame.Engine.Objects.Platform;
import CloneGame.Engine.Objects.Portal;
import CloneGame.Engine.Objects.PressurePlate;

public class Level4 extends Level {

    public Level4() {
        super(4);
    }

    @Override
    public void create(World world) {

        playerSpawnX = 80;
        playerSpawnY = 60;

        platforms = new ArrayList<>();
        doors = new ArrayList<>();
        plates = new ArrayList<>();

        platforms.add(new Platform(PLATFORM_IMG_PATH, -10, SCREEN_HEIGHT / 2, 20, SCREEN_HEIGHT + 100, world));
        platforms.add(new Platform(PLATFORM_IMG_PATH, SCREEN_WIDTH + 10, SCREEN_HEIGHT / 2, 20, SCREEN_HEIGHT + 100, world));
        platforms.add(new Platform(PLATFORM_IMG_PATH, SCREEN_WIDTH / 2, SCREEN_HEIGHT + 10, SCREEN_WIDTH + 100, 20, world));
        platforms.add(new Platform(PLATFORM_IMG_PATH, SCREEN_WIDTH / 2, -10, SCREEN_WIDTH + 100, 20, world));
        platforms.add(new Platform(PLATFORM_IMG_PATH, SCREEN_WIDTH / 2, 12, SCREEN_WIDTH, 25, world));
        platforms.add(new Platform(PLATFORM_IMG_PATH, 260, 120, 140, 25, world));
        platforms.add(new Platform(PLATFORM_IMG_PATH, 470, 220, 140, 25, world));
        platforms.add(new Platform(PLATFORM_IMG_PATH, 730, 300, 120, 25, world));
        //platforms.add(new Platform(PLATFORM_IMG_PATH, 900, 400, 120, 25, world));
        //platforms.add(new Platform(PLATFORM_IMG_PATH, 1090, 470, 120, 25, world));
        platforms.add(new Platform(PLATFORM_IMG_PATH, 850, 500, 300, 25, world));
        platforms.add(new Platform(PLATFORM_IMG_PATH, 540, 410, 120, 25, world));
        platforms.add(new Platform(PLATFORM_IMG_PATH, 1000, 250, 20, 500, world));
        platforms.add(new Platform(PLATFORM_IMG_PATH, 1100, 250, 20, 500, world));

        plates.add(new PressurePlate(PLATE_NOT_ACTIVATED_IMG_PATH, PLATE_ACTIVATED_IMG_PATH, 510, 238, 50, 12, world, 1));
        plates.add(new PressurePlate(PLATE_NOT_ACTIVATED_IMG_PATH, PLATE_ACTIVATED_IMG_PATH, 970, 510, 70, 12, world, 2));

        doors.add(new Door(DOOR_IMG_PATH, 540, 320, 30, 180, world, 1, new int[]{1}));
        doors.add(new Door(DOOR_IMG_PATH, 980, 620, 30, 220, world, 2, new int[]{1, 2}));

        portal = new Portal(PORTAL_IMG_PATH, 1050, 50, 60, 80, world);

        portal.setInPortal(false);
    }
}
