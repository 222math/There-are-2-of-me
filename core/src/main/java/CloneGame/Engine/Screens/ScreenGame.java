package CloneGame.Engine.Screens;

import static CloneGame.Engine.Main.GameResources.*;
import static CloneGame.Engine.Main.GameSettings.PERSON_HEIGHT;
import static CloneGame.Engine.Main.GameSettings.PERSON_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.List;

import CloneGame.Engine.AnimatedClone;
import CloneGame.Engine.AnimatedPlayer;
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

    private Texture backgroundTexture;

    private boolean isRecording = false;
    private boolean isReplaying = false;

    private float gameTime = 0f;

    public ScreenGame(Main main, int levelNumber) {

        this.main = main;

        initUI();
        initLevel(levelNumber);
        initAnimations();

        record = new Record();

        backgroundTexture = new Texture(BACKGROUND_IMG_PATH);
    }

    private void initUI() {
        leftMoveButton   = new TextButton(50, 50, 75, 75, "", LEFT_BUTTON_IMG_PATH);
        rightMoveButton  = new TextButton(135, 50, 75, 75, "", RIGHT_BUTTON_IMG_PATH);
        jumpButton       = new TextButton(1200, 50, 75, 75, "", JUMP_BUTTON_IMG_PATH);
        recordStartButton = new TextButton(400, 600, 100, 100, "recS", BUTTON_BG_IMG_PATH);
        recordEndButton   = new TextButton(600, 600, 100, 100, "recE", BUTTON_BG_IMG_PATH);
        replayButton      = new TextButton(720, 600, 100, 100, "rep", BUTTON_BG_IMG_PATH);
    }

    private void initAnimations() {

        Texture idleRight = new Texture(ZORO_IDLE_RIGHT);
        Texture idleLeft  = new Texture(ZORO_IDLE_LEFT);

        Texture walk1R = new Texture(ZORO_WALK1_RIGHT);
        Texture walk2R = new Texture(ZORO_WALK2_RIGHT);
        Texture walk3R = new Texture(ZORO_WALK3_RIGHT);

        Texture walk1L = new Texture(ZORO_WALK1_LEFT);
        Texture walk2L = new Texture(ZORO_WALK2_LEFT);
        Texture walk3L = new Texture(ZORO_WALK3_LEFT);

        Texture jumpRight = new Texture(ZORO_JUMP_RIGHT);
        Texture jumpLeft  = new Texture(ZORO_JUMP_LEFT);


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

        levelManager = new LevelManager();

        level = levelManager.loadLevel(levelNumber, main.world);

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

        if (isReplaying && replay != null && replay.isPlaying()) {



            replay.updPlate(gameTime);

            replay.update(delta);
        }
        else if (isReplaying && replay != null && !replay.isPlaying()) {

            isReplaying = false;
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

                Vector2 clonePos = replay.getCurrentPosition();

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

            if (isRecording && plate.wasJustChanged()) {

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
            main.setScreen(new ScreenMenu(main));
        }
    }

    private void handleInput() {

        boolean anyButtonPressed = false;

        Vector3 touchPos = new Vector3();

        if (Gdx.input.isTouched()) {

            for (int i = 0; i < 10; i++) {

                if (!Gdx.input.isTouched(i)) {
                    continue;
                }

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

                if (
                    replayButton.IsHit(worldX, worldY)
                        && !isRecording
                        && !isReplaying
                        && record.getPositions().size() > 0
                ) {
                    startReplay();
                }
            }
        }

        if (!anyButtonPressed) {
            person.stop();
        }
    }

    private void startRecording() {

        isRecording = true;

        record.deleteRecord();

        gameTime = 0;

        System.out.println("Recording started");
    }

    private void stopRecording() {

        isRecording = false;

        System.out.println(
            "Recording stopped. Total positions: "
                + record.getPositions().size()
        );
    }

    private void startReplay() {

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

        main.batch.setProjectionMatrix(main.camera.combined);

        ScreenUtils.clear(Color.CLEAR);

        main.batch.begin();

        drawBackground();

        drawWorld();

        drawUI();

        drawReplay();

        portal.draw(main.batch);

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

        animatedPlayer.draw(main.batch);

        for (Platform platform : platforms) {
            platform.draw(main.batch);
        }

        for (PressurePlate plate : plates) {
            plate.draw(main.batch);
        }

        if (doors != null) {

            for (Door door : doors) {
                door.draw(main.batch);
            }
        }
    }

    private void drawUI() {

        leftMoveButton.draw(main.batch);

        rightMoveButton.draw(main.batch);

        jumpButton.draw(main.batch);

        recordStartButton.draw(main.batch);

        recordEndButton.draw(main.batch);

        replayButton.draw(main.batch);
    }

    private void drawReplay() {

        if (
            isReplaying
                && replay != null
                && replay.isPlaying()
        ) {

            Vector2 pos = replay.getCurrentPosition();

            if (pos == null) {
                return;
            }

            int frame = replay.getCurrentFrame();

            animatedClone.setState(
                replay.getIsWalking(frame),
                replay.getIsJumping(frame),
                replay.getFacingRight(frame)
            );

            animatedClone.setPosition(pos.x, pos.y);

            animatedClone.update(
                Gdx.graphics.getDeltaTime()
            );

            animatedClone.draw(main.batch);
        }
    }

    @Override
    public void dispose() {

        if (backgroundTexture != null) {
            backgroundTexture.dispose();
        }

        leftMoveButton.dispose();
        rightMoveButton.dispose();
        jumpButton.dispose();

        recordStartButton.dispose();
        recordEndButton.dispose();
        replayButton.dispose();

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

        record.deleteRecord();
    }
}
