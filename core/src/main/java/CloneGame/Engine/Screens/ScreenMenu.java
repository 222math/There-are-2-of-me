package CloneGame.Engine.Screens;

import static CloneGame.Engine.Main.GameResources.*;
import static CloneGame.Engine.Main.GameSettings.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import CloneGame.Engine.Audio.MusicManager;
import CloneGame.Engine.Components.TextButton;
import CloneGame.Engine.Components.TutorialWindow;
import CloneGame.Engine.Main.Main;

public class ScreenMenu extends ScreenAdapter {

    private Main main;

    private Texture backgroundTexture;

    private TextButton playButton;
    private TextButton settingsButton;
    private TextButton tutorialButton;
    private TextButton exitButton;

    private TutorialWindow tutorialWindow;

    private boolean showTutorial = false;

    public ScreenMenu(Main main) {

        this.main = main;

        backgroundTexture =
            new Texture(BACKGROUND_MENU_IMG_PATH);

        tutorialWindow =
            new TutorialWindow();

        playButton =
            new TextButton(
                430,
                50,
                420,
                200,
                "",
                BUTTON_BG_PLAY_IMG_PATH
            );

        settingsButton =
            new TextButton(
                70,
                260,
                320,
                85,
                "SETTINGS",
                BUTTON_BG_GREEN_IMG_PATH
            );

        tutorialButton =
            new TextButton(
                70,
                150,
                320,
                85,
                "TUTORIAL",
                BUTTON_BG_GREEN_IMG_PATH
            );

        exitButton =
            new TextButton(
                980,
                25,
                250,
                75,
                "EXIT",
                BUTTON_BG_RED_IMG_PATH
            );
        MusicManager.playMenuMusic();
    }

    @Override
    public void render(float delta) {

        handleInput();

        ScreenUtils.clear(Color.BLACK);

        main.camera.update();

        main.batch.setProjectionMatrix(
            main.camera.combined
        );

        main.batch.begin();

        main.batch.draw(
            backgroundTexture,
            0,
            0,
            SCREEN_WIDTH,
            SCREEN_HEIGHT
        );

        playButton.draw(main.batch);

        settingsButton.draw(main.batch);

        tutorialButton.draw(main.batch);

        exitButton.draw(main.batch);

        if (showTutorial) {
            tutorialWindow.draw(main.batch);
        }

        main.batch.end();
    }

    private void handleInput() {

        if (!Gdx.input.justTouched()) {
            return;
        }

        Vector3 touchPos = new Vector3();

        touchPos.set(
            Gdx.input.getX(),
            Gdx.input.getY(),
            0
        );

        main.camera.unproject(touchPos);

        float x = touchPos.x;
        float y = touchPos.y;

        

        if (showTutorial) {

            if (tutorialWindow.isClosePressed(x, y)) {

                showTutorial = false;
            }

            return;
        }

        if (playButton.IsHit(x, y)) {

            main.setScreen(
                new ScreenLevelSelect(main)
            );
        }

        if (settingsButton.IsHit(x, y)) {

            main.setScreen(
                new ScreenSettings(main)
            );
        }

        if (tutorialButton.IsHit(x, y)) {

            showTutorial = true;
        }

        if (exitButton.IsHit(x, y)) {

            Gdx.app.exit();
        }
    }

    @Override
    public void dispose() {

        backgroundTexture.dispose();

        playButton.dispose();

        settingsButton.dispose();

        tutorialButton.dispose();

        exitButton.dispose();

        tutorialWindow.dispose();
    }
}
