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

public class Level3 extends Level {

    public Level3() {
        super(2);
    }

    @Override
    public void create(World world) {

        playerSpawnX = 60;
        playerSpawnY = 40;

        platforms = new ArrayList<>();
        doors = new ArrayList<>();
        plates = new ArrayList<>();

        // ===== ГРАНИЦЫ =====
        platforms.add(new Platform(PLATFORM_IMG_PATH, -20, 0, 20, SCREEN_HEIGHT + 100, world));
        platforms.add(new Platform(PLATFORM_IMG_PATH, SCREEN_WIDTH, 0, 20, SCREEN_HEIGHT + 100, world));
        platforms.add(new Platform(PLATFORM_IMG_PATH, 0, SCREEN_HEIGHT + 20, SCREEN_WIDTH + 100, 20, world));
        platforms.add(new Platform(PLATFORM_IMG_PATH, 0, -20, SCREEN_WIDTH + 100, 20, world));


        platforms.add(new Platform(PLATFORM_IMG_PATH, 0, 0, SCREEN_WIDTH, 25, world));


        platforms.add(new Platform(PLATFORM_IMG_PATH, 150, 100, 80, 25, world));
        platforms.add(new Platform(PLATFORM_IMG_PATH, 300, 160, 80, 25, world));
        platforms.add(new Platform(PLATFORM_IMG_PATH, 450, 220, 80, 25, world));


        plates.add(new PressurePlate(PLATE_NOT_ACTIVATED_IMG_PATH, PLATE_ACTIVATED_IMG_PATH,
            470, 235, 50, 12, world, 1));


        doors.add(new Door(DOOR_IMG_PATH, 560, 100, 30, 160, world, 1, new int[]{1}));


        platforms.add(new Platform(PLATFORM_IMG_PATH, 200, 300, 100, 25, world));
        platforms.add(new Platform(PLATFORM_IMG_PATH, 400, 380, 100, 25, world));
        platforms.add(new Platform(PLATFORM_IMG_PATH, 600, 460, 100, 25, world));


        plates.add(new PressurePlate(PLATE_NOT_ACTIVATED_IMG_PATH, PLATE_ACTIVATED_IMG_PATH,
            620, 475, 50, 12, world, 2));


        doors.add(new Door(DOOR_IMG_PATH, 750, 200, 30, 320, world, 2, new int[]{1, 2}));


        platforms.add(new Platform(PLATFORM_IMG_PATH, 850, 300, 100, 25, world));
        platforms.add(new Platform(PLATFORM_IMG_PATH, 1000, 380, 100, 25, world));
        platforms.add(new Platform(PLATFORM_IMG_PATH, 1150, 460, 100, 25, world));


        portal = new Portal(PORTAL_IMG_PATH, 1220, 480, 50, 70, world);
        portal.setInPortal(false);
    }
}
