package CloneGame.Engine.Screens;

import static CloneGame.Engine.Main.GameResources.*;
import static CloneGame.Engine.Main.GameSettings.PERSON_HEIGHT;
import static CloneGame.Engine.Main.GameSettings.PERSON_WIDTH;
import static CloneGame.Engine.Main.GameSettings.SCREEN_HEIGHT;
import static CloneGame.Engine.Main.GameSettings.SCREEN_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.List;

import CloneGame.Engine.Animated.AnimatedClone;
import CloneGame.Engine.Animated.AnimatedPlayer;
import CloneGame.Engine.Audio.MusicManager;
import CloneGame.Engine.Components.HintButton;
import CloneGame.Engine.Components.HintWindow;
import CloneGame.Engine.Components.TextButton;
import CloneGame.Engine.Levels.Level;
import CloneGame.Engine.Levels.LevelManager;
import CloneGame.Engine.Main.Main;
import CloneGame.Engine.Objects.Door;
import CloneGame.Engine.Objects.Person;
import CloneGame.Engine.Objects.Platform;
import CloneGame.Engine.Objects.Portal;
import CloneGame.Engine.Objects.PressurePlate;
import CloneGame.Engine.Recording.Record;
import CloneGame.Engine.Recording.Replay;
import CloneGame.Engine.Utils.FontManager;

public class ScreenGame extends ScreenAdapter {

    private final Main main;

    private Record record;
    private Replay replay;

    private Person person;

    private LevelManager levelManager;
    private Level level;

    private List<Platform> platforms;
    private List<PressurePlate> plates;
    private List<Door> doors;

    private Portal portal;

    private AnimatedPlayer animatedPlayer;
    private AnimatedClone animatedClone;

    private TextButton rightMoveButton;
    private TextButton leftMoveButton;
    private TextButton jumpButton;
    private TextButton recordStartButton;
    private TextButton recordEndButton;
    private TextButton replayButton;
    private TextButton menuButton;

    private Texture backgroundTexture;
    private Texture darkTexture;

    private BitmapFont uiFont;

    private boolean isRecording = false;
    private boolean isReplaying = false;

    private float gameTime = 0f;
    private HintButton hintButton;
    private HintWindow hintWindow;

    public ScreenGame(Main main, int levelNumber) {

        this.main = main;

        initUI();
        initLevel(levelNumber);
        initAnimations();

        record = new Record();

        backgroundTexture = new Texture(BACKGROUND_GAME_IMG_PATH);

        darkTexture = new Texture("img_3.png");
        uiBg = new Texture("img_3.png");

        uiFont = FontManager.font32;

        MusicManager.playGameMusic();
    }

    private void initUI() {

        leftMoveButton = new TextButton(
            50,
            50,
            120,
            120,
            "",
            LEFT_BUTTON_IMG_PATH
        );

        rightMoveButton = new TextButton(
            165,
            50,
            120,
            120,
            "",
            RIGHT_BUTTON_IMG_PATH
        );

        jumpButton = new TextButton(
            1100,
            50,
            120,
            120,
            "",
            JUMP_BUTTON_IMG_PATH
        );

        recordStartButton = new TextButton(
            20,
            600,
            130,
            90,
            "",
            BUTTON_BG_RECORD_IMG_PATH
        );

        recordEndButton = new TextButton(
            155,
            600,
            130,
            90,
            "",
            BUTTON_BG_STOP_IMG_PATH
        );

        replayButton = new TextButton(
            290,
            600,
            110,
            90,
            "",
            BUTTON_BG_REPLAY_IMG_PATH
        );

        menuButton = new TextButton(
            1040,
            620,
            180,
            100,
            "back",
            BUTTON_BG_RED_IMG_PATH
        );

        leftMoveButton.setAlpha(0.45f);
        rightMoveButton.setAlpha(0.45f);
        jumpButton.setAlpha(0.45f);
    }

    private void initAnimations() {

        Texture idleRight = new Texture(ZORO_IDLE_RIGHT);
        Texture idleLeft = new Texture(ZORO_IDLE_LEFT);

        Texture walk1R = new Texture(ZORO_WALK1_RIGHT);
        Texture walk2R = new Texture(ZORO_WALK2_RIGHT);
        Texture walk3R = new Texture(ZORO_WALK3_RIGHT);

        Texture walk1L = new Texture(ZORO_WALK1_LEFT);
        Texture walk2L = new Texture(ZORO_WALK2_LEFT);
        Texture walk3L = new Texture(ZORO_WALK3_LEFT);

        Texture jumpRight = new Texture(ZORO_JUMP_RIGHT);
        Texture jumpLeft = new Texture(ZORO_JUMP_LEFT);

        Texture[] walkFramesRight = {
            walk1R,
            walk2R,
            walk3R
        };

        Texture[] walkFramesLeft = {
            walk1L,
            walk2L,
            walk3L
        };

        animatedPlayer = new AnimatedPlayer(
            idleRight,
            idleLeft,
            walkFramesRight,
            walkFramesLeft,
            jumpRight,
            jumpLeft
        );

        animatedPlayer.setSize(PERSON_WIDTH, PERSON_HEIGHT);

        animatedClone = new AnimatedClone(
            idleRight,
            idleLeft,
            walkFramesRight,
            walkFramesLeft,
            jumpRight,
            jumpLeft,
            PERSON_WIDTH,
            PERSON_HEIGHT
        );
    }

    private void initLevel(int levelNumber) {

        main.world.dispose();

        main.world = new World(
            new Vector2(0, -20f),
            true
        );

        main.world.setContactListener(
            main.contactListener
        );

        levelManager = new LevelManager();

        level = levelManager.loadLevel(
            levelNumber,
            main.world
        );

        hintButton = new HintButton();


        String hintText = getHintForLevel(levelNumber);
        hintWindow = new HintWindow(hintText);

        platforms = level.getPlatforms();
        plates = level.getPlates();
        doors = level.getDoors();

        portal = level.getPortal();

        portal.setInPortal(false);

        person = new Person(
            PERSON_IMG_PATH,
            (int) level.getPlayerSpawnX(),
            (int) level.getPlayerSpawnY(),
            PERSON_WIDTH,
            PERSON_HEIGHT,
            main.world
        );
    }

    private String getHintForLevel(int level) {
        switch (level) {
            case 1:
                return "You need to press both plates, use a clone for this";
            case 2:
                return "To open the door you need both plates, use the clone for this";
            case 3:
                return "To complete the level, you need to use the clone's actions recording and playback simultaneously. Yes, this is possible.";
            case 4:
                return "";
            default:
                return "Нет подсказки.";
        }
    }

    @Override
    public void render(float delta) {

        gameTime += delta;

        updateGame(delta);

        handleInput();

        draw();
    }

    private void updateGame(float delta) {

        main.stepWorld();

        updateDoorsPhysics();

        person.update();

        boolean isWalking =
            Math.abs(person.getVelocityX()) > 0.1f;

        boolean isJumping =
            !person.isOnGround();

        boolean isMovingRight =
            person.getVelocityX() > 0;

        updateReplay(delta);

        updatePressurePlates();

        recordPlayerState(
            delta,
            isWalking,
            isJumping,
            isMovingRight
        );

        updateDoors();

        updateAnimations(
            delta,
            isWalking,
            isJumping,
            isMovingRight
        );

        checkPortal();
    }

    private void updateReplay(float delta) {

        if (
            isReplaying
                && replay != null
                && replay.isPlaying()
        ) {

            replay.updPlate(gameTime);

            replay.update(delta);
        }
        else if (
            isReplaying
                && replay != null
                && !replay.isPlaying()
        ) {

            isReplaying = false;

            MusicManager.playGameMusic();
        }
    }

    private void updatePressurePlates() {

        for (PressurePlate plate : plates) {

            plate.updatePlayerState(person);

            if (
                isReplaying
                    && replay != null
                    && replay.isPlaying()
            ) {

                Vector2 clonePos =
                    replay.getCurrentPosition();

                if (clonePos != null) {

                    plate.updateCloneState(
                        clonePos.x,
                        clonePos.y,
                        PERSON_WIDTH,
                        PERSON_HEIGHT
                    );
                }
            }
            else {

                plate.setPressedByClone(false);
            }

            if (
                isRecording
                    && plate.wasJustChanged()
            ) {

                record.recordingPlate(
                    plate.getId(),
                    plate.isActivated(),
                    gameTime
                );

                plate.resetJustChanged();
            }
        }
    }

    private void recordPlayerState(
        float delta,
        boolean isWalking,
        boolean isJumping,
        boolean isMovingRight
    ) {

        if (!isRecording) {
            return;
        }

        record.recordingPos(
            person.getX(),
            person.getY(),
            delta,
            gameTime,
            isWalking,
            isJumping,
            isMovingRight
        );
    }

    private void updateDoors() {

        if (doors == null) {
            return;
        }

        for (Door door : doors) {
            door.checkAndUpdate(plates);
        }
    }

    private void updateDoorsPhysics() {

        if (doors == null) {
            return;
        }

        for (Door door : doors) {
            door.applyPhysicsChanges();
        }
    }

    private void updateAnimations(
        float delta,
        boolean isWalking,
        boolean isJumping,
        boolean isMovingRight
    ) {

        animatedPlayer.update(
            delta,
            isWalking,
            isJumping,
            isMovingRight
        );

        animatedPlayer.setPosition(
            person.getX() - PERSON_WIDTH / 2f,
            person.getY() - PERSON_HEIGHT / 2f
        );
    }

    private void checkPortal() {

        if (portal.getInPortal()) {

            main.setScreen(
                new ScreenMenu(main)
            );
        }
    }

    private boolean hintPressedLastFrame = false;

    private void handleInput() {

        Vector3 touchPos = new Vector3();


        if (hintWindow.isVisible()) {

            if (Gdx.input.justTouched()) {
                touchPos.set(
                    Gdx.input.getX(),
                    Gdx.input.getY(),
                    0
                );

                main.camera.unproject(touchPos);

                if (hintButton.isClicked(touchPos.x, touchPos.y)) {
                    hintWindow.toggle(); // закрыть
                }
            }

            return;
        }

        boolean anyButtonPressed = false;
        boolean hintPressedNow = false;


        if (Gdx.input.isTouched()) {

            for (int i = 0; i < 10; i++) {

                if (!Gdx.input.isTouched(i)) continue;

                touchPos.set(
                    Gdx.input.getX(i),
                    Gdx.input.getY(i),
                    0
                );

                main.camera.unproject(touchPos);

                float worldX = touchPos.x;
                float worldY = touchPos.y;


                if (rightMoveButton.IsHit(worldX, worldY)) {
                    person.moveRight();
                    anyButtonPressed = true;
                }

                if (leftMoveButton.IsHit(worldX, worldY)) {
                    person.moveLeft();
                    anyButtonPressed = true;
                }

                if (jumpButton.IsHit(worldX, worldY)) {
                    person.jump();
                }


                if (recordStartButton.IsHit(worldX, worldY)) {
                    if (!isRecording) {
                        startRecording();
                    }
                }

                if (recordEndButton.IsHit(worldX, worldY)) {
                    if (isRecording) {
                        stopRecording();
                    }
                }

                if (replayButton.IsHit(worldX, worldY)
                    && !isRecording
                    && !isReplaying
                    && record.getPositions().size() > 0) {

                    startReplay();
                }

                if (menuButton.IsHit(worldX, worldY)) {
                    main.setScreen(new ScreenMenu(main));
                }


                if (hintButton.isClicked(worldX, worldY)) {
                    hintPressedNow = true;

                    if (!hintPressedLastFrame) {
                        hintWindow.toggle();
                    }
                }
            }
        }

        if (!anyButtonPressed) {
            person.stop();
        }

        hintPressedLastFrame = hintPressedNow;
    }

    private void startRecording() {

        isRecording = true;

        record.deleteRecord();

        gameTime = 0;

        System.out.println(
            "Recording started"
        );
    }

    private void stopRecording() {

        isRecording = false;

        System.out.println(
            "Recording stopped. Total positions: "
                + record.getPositions().size()
        );
    }

    private void startReplay() {

        MusicManager.playReplayMusic();

        isReplaying = true;

        gameTime = 0;

        replay = new Replay(
            record.getPositions(),
            plates,
            record.getPlateIds(),
            record.getPlateStates(),
            record.getPlateTimes(),
            record.getFacingRightList(),
            record.getIsWalkingList(),
            record.getIsJumpingList(),
            PERSON_IMG_PATH
        );

        System.out.println(
            "Replay created with "
                + record.getPositions().size()
                + " positions"
        );
    }

    private void draw() {

        main.camera.update();

        main.batch.setProjectionMatrix(
            main.camera.combined
        );

        ScreenUtils.clear(Color.CLEAR);

        main.batch.begin();

        drawBackground();

        main.batch.setColor(
            0f,
            0f,
            0f,
            0.3f
        );

        main.batch.draw(
            darkTexture,
            0,
            0,
            SCREEN_WIDTH,
            SCREEN_HEIGHT
        );

        main.batch.setColor(
            1f,
            1f,
            1f,
            1f
        );

        drawWorld();

        drawReplay();

        portal.draw(main.batch);

        drawUI();

        main.batch.end();


        main.batch.setProjectionMatrix(
            new Matrix4().setToOrtho2D(
                0,
                0,
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight()
            )
        );

        main.batch.begin();

//        hintButton.render(main.batch);
          hintWindow.render(main.batch);

        main.batch.end();
    }

    private void drawBackground() {

        main.batch.draw(
            backgroundTexture,
            0,
            -50,
            1280,
            770
        );
    }

    private void drawWorld() {

        if (doors != null) {

            for (Door door : doors) {
                door.draw(main.batch);
            }
        }

        animatedPlayer.draw(main.batch);

        for (Platform platform : platforms) {
            platform.draw(main.batch);
        }

        for (PressurePlate plate : plates) {
            plate.draw(main.batch);
        }
    }

    private Texture uiBg;

    private void drawUI() {

        menuButton.draw(main.batch);

        leftMoveButton.draw(main.batch);

        rightMoveButton.draw(main.batch);

        jumpButton.draw(main.batch);

        recordStartButton.draw(main.batch);

        recordEndButton.draw(main.batch);

        replayButton.draw(main.batch);

        uiFont.getData().setScale(0.68f);

        if (isRecording) {

            main.batch.setColor(
                0f,
                0f,
                0f,
                0.60f
            );

            main.batch.draw(
                uiBg,
                15,
                540,
                240,
                38
            );

            main.batch.setColor(
                1f,
                1f,
                1f,
                1f
            );

            uiFont.setColor(Color.RED);

            uiFont.draw(
                main.batch,
                "RECORDING  " + String.format("%.1f", gameTime),
                30,
                565
            );
        }

        if (isReplaying) {

            main.batch.setColor(
                0f,
                0f,
                0f,
                0.60f
            );

            main.batch.draw(
                uiBg,
                15,
                495,
                240,
                38
            );

            main.batch.setColor(
                1f,
                1f,
                1f,
                1f
            );

            uiFont.setColor(Color.CYAN);

            uiFont.draw(
                main.batch,
                "REPLAY  " + String.format("%.1f", gameTime),
                30,
                520
            );
        }

        uiFont.getData().setScale(1f);

        uiFont.setColor(Color.WHITE);
        hintButton.render(main.batch);
        //hintWindow.render(main.batch);
    }

    private void drawReplay() {

        if (
            isReplaying
                && replay != null
                && replay.isPlaying()
        ) {

            Vector2 pos =
                replay.getCurrentPosition();

            if (pos == null) {
                return;
            }

            int frame =
                replay.getCurrentFrame();

            animatedClone.setState(
                replay.getIsWalking(frame),
                replay.getIsJumping(frame),
                replay.getFacingRight(frame)
            );

            animatedClone.setPosition(
                pos.x,
                pos.y
            );

            animatedClone.update(
                Gdx.graphics.getDeltaTime()
            );

            animatedClone.draw(main.batch);
        }
        System.out.println("hint visible: " + hintWindow.isVisible());
    }

    @Override
    public void dispose() {

        if (backgroundTexture != null) {
            backgroundTexture.dispose();
        }

        if (darkTexture != null) {
            darkTexture.dispose();
        }

        leftMoveButton.dispose();
        rightMoveButton.dispose();
        jumpButton.dispose();

        recordStartButton.dispose();
        recordEndButton.dispose();
        replayButton.dispose();

        menuButton.dispose();

        if (person != null) {
            person.dispose();
        }

        for (Platform platform : platforms) {
            platform.dispose();
        }

        for (PressurePlate plate : plates) {
            plate.dispose();
        }

        if (portal != null) {
            portal.dispose();
        }

        if (replay != null) {
            replay.dispose();
        }

        if (animatedPlayer != null) {
            animatedPlayer.dispose();
        }

        if (uiBg != null) {
            uiBg.dispose();
        }

        record.deleteRecord();
    }
}
