package CloneGame.Engine.Screens;

import static CloneGame.Engine.Main.GameResources.PERSON_IMG_PATH;
import static CloneGame.Engine.Main.GameSettings.PERSON_HEIGHT;
import static CloneGame.Engine.Main.GameSettings.PERSON_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

import CloneGame.Engine.Components.TextButton;
import CloneGame.Engine.Main.Main;
import CloneGame.Engine.Objects.Person;
import CloneGame.Engine.Objects.Platform;
import CloneGame.Engine.Objects.Portal;
import CloneGame.Engine.Objects.PressurePlate;
import CloneGame.Engine.Recording.Record;
import CloneGame.Engine.Recording.Replay;

public class ScreenGame extends ScreenAdapter {
    Main main;
    ScreenMenu screenMenu;
    Record record;
    Replay replay;
    Person person;
    Platform platform1;
    Platform platform2;
    Portal portal;
    PressurePlate pressurePlate;
    TextButton rightMoveButton;
    TextButton leftMoveButton;
    TextButton jumpButton;
    TextButton recordStartButton;
    TextButton recordEndButton;
    TextButton replayButton;
    private List<PressurePlate> plates = new ArrayList<>();
    private float gameTime = 0;

    public ScreenGame(Main main){
        this.main = main;
        person = new Person(PERSON_IMG_PATH , 600 , 150 , PERSON_WIDTH , PERSON_HEIGHT , main.world);
        leftMoveButton = new TextButton(50 , 50 , 75 , 75 , "l" , "img.png");
        rightMoveButton = new TextButton(135 , 50 , 75 , 75 , "r" , "img.png");
        jumpButton = new TextButton(1200 , 50 , 75 , 75 , "j" , "img.png");
        recordStartButton = new TextButton(400 , 600 , 100 , 100 , "recS" , "img.png");
        recordEndButton = new TextButton(600 , 600 , 100 , 100 , "recE" , "img.png");
        replayButton = new TextButton(720 ,  600, 100 , 100 , "rep" , "img.png");
        platform1 = new Platform(PERSON_IMG_PATH , 800 , 150 , 400 , 20 , main.world);
        platform2 = new Platform(PERSON_IMG_PATH , 0 , 0 , 200000 , 25 , main.world);
        portal = new Portal("img_1.png" , 0 ,25 , 30 , 150 , main.world);
        pressurePlate = new PressurePlate("img_1.png" , "img.png" , 900 , 157 , 100 , 15 , main.world , 1);
        plates.add(pressurePlate);
        record = new Record();
        portal.setInPortal(false);
    }

    @Override
    public void render(float delta) {
        gameTime += delta;

        main.stepWorld();
        person.update();

        // Если играет replay - перезаписываем состояние плит
        if (rep && replay != null && replay.isPlaying()) {
            // Сбрасываем плиты перед применением состояний клона
            for (PressurePlate plate : plates) {
                plate.setActivated(false);
            }
            replay.updPlate(gameTime);
            replay.update(delta);
        } else if (rep && replay != null && !replay.isPlaying()) {
            rep = false;
            replay = null;
        }

        if (portal.getInPortal()) {
            main.setScreen(new ScreenMenu(main));
            return;
        }

        handleInput();
        draw();
    }

    boolean rec = false;
    boolean rep = false;

    public void handleInput(){
        float delta = Gdx.graphics.getDeltaTime();
        boolean anyButtonPressed = false;
        Vector3 touchPos = new Vector3();

        if (Gdx.input.isTouched()) {
            for (int i = 0; i < 10; i++) {
                if (!Gdx.input.isTouched(i)) continue;

                float screenX = Gdx.input.getX(i);
                float screenY = Gdx.input.getY(i);

                touchPos.set(screenX, screenY, 0);
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
                if (recordStartButton.IsHit(worldX , worldY)){
                    if (!rec){
                        rec = true;
                        record.deleteRecord();
                        gameTime = 0;
                        System.out.println("Recording started");
                    }
                }
                if (recordEndButton.IsHit(worldX , worldY)){
                    if (rec) {
                        rec = false;
                        System.out.println("Recording stopped. Total positions: " + record.getPositions().size());
                        System.out.println("Plate states recorded: " + record.getPlateStates().size());
                    }
                }
                if (replayButton.IsHit(worldX, worldY) && !rec && !rep && record.getPositions().size() > 0) {
                    rep = true;
                    gameTime = 0;
                    replay = new Replay(record.getPositions(), plates,
                        record.getPlateIds(), record.getPlateStates(),
                        record.getPlateTimes(), PERSON_IMG_PATH);
                    System.out.println("Replay created with " + record.getPositions().size() + " positions");
                }
            }
        }

        if (!anyButtonPressed) {
            person.stop();
        }

        if(portal.getInPortal()){
            screenMenu = new ScreenMenu(this.main);
            main.setScreen(screenMenu);
        }

        if (rec) {
            record.recordingPos(person.getX(), person.getY(), delta, gameTime);

            for (PressurePlate plate : plates) {
                record.recordingPlate(plate.getId(), plate.isActivated(), gameTime);
            }
        }
    }

    public void draw(){
        float delta = Gdx.graphics.getDeltaTime();
        main.camera.update();
        main.batch.setProjectionMatrix(main.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        main.batch.begin();

        person.draw(main.batch);
        leftMoveButton.draw(main.batch);
        rightMoveButton.draw(main.batch);
        jumpButton.draw(main.batch);
        recordStartButton.draw(main.batch);
        recordEndButton.draw(main.batch);
        replayButton.draw(main.batch);
        pressurePlate.draw(main.batch);
        platform1.draw(main.batch);
        platform2.draw(main.batch);

        if (rep && replay != null && replay.isPlaying()) {
            Vector2 pos = replay.getCurrentPosition();
            if (pos != null) {
                main.batch.draw(replay.getTexture(),
                    pos.x - PERSON_WIDTH/2,
                    pos.y - PERSON_HEIGHT/2,
                    PERSON_WIDTH, PERSON_HEIGHT);
            }
        } else if (replay != null && !replay.isPlaying()) {
            rep = false;
        }

        portal.draw(main.batch);

        main.batch.end();
    }
}
