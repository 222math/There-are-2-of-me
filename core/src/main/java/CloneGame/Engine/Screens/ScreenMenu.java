package CloneGame.Engine.Screens;

import static CloneGame.Engine.Main.GameResources.BACKGROUND_IMG_PATH;
import static CloneGame.Engine.Main.GameResources.BUTTON_BG_IMG_PATH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import CloneGame.Engine.Audio.MusicManager;
import CloneGame.Engine.Components.TextButton;
import CloneGame.Engine.Main.Main;

public class ScreenMenu extends ScreenAdapter {

    private final Main main;

    private Texture backgroundTexture;

    private TextButton playButton;
    private TextButton settingsButton;
    private TextButton exitButton;

    public ScreenMenu(Main main) {

        this.main = main;

        backgroundTexture = new Texture(BACKGROUND_IMG_PATH);

        initButtons();

        MusicManager.playMenuMusic();
    }

    private void initButtons() {

        playButton =
            new TextButton(
                490,
                420,
                300,
                90,
                "PLAY",
                BUTTON_BG_IMG_PATH
            );

        settingsButton =
            new TextButton(
                490,
                300,
                300,
                90,
                "SETTINGS",
                BUTTON_BG_IMG_PATH
            );

        exitButton =
            new TextButton(
                490,
                180,
                300,
                90,
                "EXIT",
                BUTTON_BG_IMG_PATH
            );
    }

    @Override
    public void render(float delta) {

        handleInput();

        draw();
    }

    private void handleInput() {

        if (!Gdx.input.justTouched()) {
            return;
        }

        Vector3 touchPos = new Vector3(
            Gdx.input.getX(),
            Gdx.input.getY(),
            0
        );

        main.camera.unproject(touchPos);

        float x = touchPos.x;
        float y = touchPos.y;

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

        if (exitButton.IsHit(x, y)) {

            Gdx.app.exit();
        }
    }

    private void draw() {

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
            1280,
            720
        );

        playButton.draw(main.batch);
        settingsButton.draw(main.batch);
        exitButton.draw(main.batch);

        main.batch.end();
    }

    @Override
    public void dispose() {

        backgroundTexture.dispose();

        playButton.dispose();
        settingsButton.dispose();
        exitButton.dispose();
    }
}
