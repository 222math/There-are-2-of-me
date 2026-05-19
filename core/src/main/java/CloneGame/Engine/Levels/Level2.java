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

public class Level2 extends Level {

    public Level2() {
        super(2);
    }

    @Override
    public void create(World world) {

        playerSpawnX = 60;
        playerSpawnY = 40;

        platforms = new ArrayList<>();
        doors = new ArrayList<>();
        plates = new ArrayList<>();

        platforms.add(new Platform(PLATFORM_IMG_PATH, -20, SCREEN_HEIGHT / 2, 20, SCREEN_HEIGHT + 100, world));

        platforms.add(new Platform(PLATFORM_IMG_PATH, SCREEN_WIDTH, SCREEN_HEIGHT / 2, 20, SCREEN_HEIGHT + 100, world));

        platforms.add(new Platform(PLATFORM_IMG_PATH, SCREEN_WIDTH / 2, SCREEN_HEIGHT + 20, SCREEN_WIDTH + 100, 20, world));

        platforms.add(new Platform(PLATFORM_IMG_PATH, SCREEN_WIDTH / 2, -20, SCREEN_WIDTH + 100, 20, world));


        // ========== ЗЕМЛЯ ==========
        platforms.add(new Platform(PLATFORM_IMG_PATH, SCREEN_WIDTH/2, 0, SCREEN_WIDTH+20, 25, world));



        platforms.add(new Platform(PLATFORM_IMG_PATH, 300, 160, 650, 25, world)); //l
        platforms.add(new Platform(PLATFORM_IMG_PATH, 450, 220, 80, 25, world));








        platforms.add(new Platform(PLATFORM_IMG_PATH, 150, 300, 325, 25, world)); //l
        platforms.add(new Platform(PLATFORM_IMG_PATH, 75, 420, 250, 25, world));
        platforms.add(new Platform(PLATFORM_IMG_PATH, 600, 460, 100, 25, world));



        plates.add(new PressurePlate(PLATE_NOT_ACTIVATED_IMG_PATH, PLATE_ACTIVATED_IMG_PATH,
            35, 15, 50, 12, world, 1));
        plates.add(new PressurePlate(PLATE_NOT_ACTIVATED_IMG_PATH, PLATE_ACTIVATED_IMG_PATH,
            1050, 274, 50, 12, world, 2));
        plates.add(new PressurePlate(PLATE_NOT_ACTIVATED_IMG_PATH, PLATE_ACTIVATED_IMG_PATH,
            35, 175, 50, 12, world, 3));

        doors.add(new Door(DOOR_IMG_PATH, 560, 80, 20, 170, world, 1, new int[]{1}));
        doors.add(new Door(DOOR_IMG_PATH, 250, 225, 20, 140, world, 2, new int[]{2}));
        doors.add(new Door(DOOR_IMG_PATH, 150, 350, 20, 120, world, 3, new int[]{3}));


        platforms.add(new Platform(PLATFORM_IMG_PATH, 750, 100, 100, 25, world));
        platforms.add(new Platform(PLATFORM_IMG_PATH, 900, 180, 100, 25, world));
        platforms.add(new Platform(PLATFORM_IMG_PATH, 1050, 260, 100, 25, world));

        portal = new Portal(PORTAL_IMG_PATH, 40, 360, 50, 70, world);
        portal.setInPortal(false);
    }
}
