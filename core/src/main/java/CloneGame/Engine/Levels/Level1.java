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
        playerSpawnX = 60;
        playerSpawnY = 50;

        platforms = new ArrayList<>();
        doors = new ArrayList<>();
        plates = new ArrayList<>();

        // ========== ГРАНИЦЫ ==========
        platforms.add(new Platform(PLATFORM_IMG_PATH, -20, 0, 20, SCREEN_HEIGHT + 100, world));
        platforms.add(new Platform(PLATFORM_IMG_PATH, SCREEN_WIDTH, 0, 20, SCREEN_HEIGHT + 100, world));
        platforms.add(new Platform(PLATFORM_IMG_PATH, 0, SCREEN_HEIGHT + 20, SCREEN_WIDTH + 100, 20, world));
        platforms.add(new Platform(PLATFORM_IMG_PATH, 0, -20, SCREEN_WIDTH + 100, 20, world));

        // ========== ЗЕМЛЯ ==========
        platforms.add(new Platform(PLATFORM_IMG_PATH, 0, 0, SCREEN_WIDTH, 25, world));

        // ========== ПУТЬ 1 (для записи клона) ==========
        platforms.add(new Platform(PLATFORM_IMG_PATH, 200, 100, 80, 25, world));
        platforms.add(new Platform(PLATFORM_IMG_PATH, 350, 180, 80, 25, world));
        platforms.add(new Platform(PLATFORM_IMG_PATH, 500, 260, 80, 25, world));

        // Плита A (для клона)
        plates.add(new PressurePlate(PLATE_NOT_ACTIVATED_IMG_PATH, PLATE_ACTIVATED_IMG_PATH,
            530, 275, 50, 12, world, 1));

        platforms.add(new Platform(PLATFORM_IMG_PATH, 650, 180, 80, 25, world));
        platforms.add(new Platform(PLATFORM_IMG_PATH, 800, 100, 80, 25, world));

        // ========== ПУТЬ 2 (для игрока) ==========
        platforms.add(new Platform(PLATFORM_IMG_PATH, 200, 350, 100, 25, world));
        platforms.add(new Platform(PLATFORM_IMG_PATH, 400, 420, 100, 25, world));
        platforms.add(new Platform(PLATFORM_IMG_PATH, 600, 490, 100, 25, world));

        // Плита B (для игрока)
        plates.add(new PressurePlate(PLATE_NOT_ACTIVATED_IMG_PATH, PLATE_ACTIVATED_IMG_PATH,
            630, 505, 50, 12, world, 2));

        platforms.add(new Platform(PLATFORM_IMG_PATH, 800, 420, 100, 25, world));
        platforms.add(new Platform(PLATFORM_IMG_PATH, 1000, 350, 100, 25, world));

        // ========== ФИНАЛЬНЫЙ ПУТЬ (открывается когда нажаты ОБЕ плиты) ==========
        // Платформы, которые появляются только после открытия двери
        platforms.add(new Platform(PLATFORM_IMG_PATH, 850, 250, 100, 25, world));
        platforms.add(new Platform(PLATFORM_IMG_PATH, 1050, 200, 100, 25, world));
        platforms.add(new Platform(PLATFORM_IMG_PATH, 1200, 150, 100, 25, world));

        // ========== ДВЕРИ ==========
        // Дверь 1 - блокирует путь к финалу (требует плиту 1 И плиту 2)
        doors.add(new Door(DOOR_IMG_PATH, 820, 100, 40, 200, world, 1, new int[]{1, 2}));

        // Дверь 2 - блокирует путь к плите 2 (нужен клон)
        doors.add(new Door(DOOR_IMG_PATH, 580, 350, 40, 150, world, 2, new int[]{1}));

        // ========== ПОРТАЛ ==========
        portal = new Portal(PORTAL_IMG_PATH, 1280, 120, 50, 80, world);
        portal.setInPortal(false);
    }
}
