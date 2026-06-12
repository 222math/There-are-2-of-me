package CloneGame.Engine.Screens;

import static CloneGame.Engine.Main.GameResources.*;
import static CloneGame.Engine.Main.GameSettings.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import CloneGame.Engine.Components.TextButton;
import CloneGame.Engine.Main.Main;

public class ScreenLevelSelect extends ScreenAdapter {

    private Main main;

    private Texture backgroundTexture;

    private TextButton level1Button;
    private TextButton level2Button;
    private TextButton level3Button;
    private TextButton level4Button;
    private TextButton level5Button;
    private TextButton level6Button;
    private TextButton level7Button;
    private TextButton level8Button;
    private TextButton level9Button;
    private TextButton level10Button;

    private TextButton menuButton;

    public ScreenLevelSelect(Main main) {

        this.main = main;

        backgroundTexture =
            new Texture(BACKGROUND_LEVELS_IMG_PATH);

        level1Button =
            new TextButton(
                80,
                380,
                220,
                140,
                "",
                BUTTON_BG_GREEN_IMG_PATH
            );

        level2Button =
            new TextButton(
                370,
                370,
                220,
                140,
                "",
                BUTTON_BG_GREEN_IMG_PATH
            );

        level3Button =
            new TextButton(
                650,
                360,
                220,
                140,
                "",
                BUTTON_BG_GREEN_IMG_PATH
            );
        level4Button =
            new TextButton(
                930,
                330,
                220,
                140,
                "",
                BUTTON_BG_SOON_IMG_PATH
            );
        level5Button =
            new TextButton(
                150,
                230,
                220,
                140,
                "",
                BUTTON_BG_SOON_IMG_PATH
            );

        level6Button = new TextButton(
            500, 200,
            220, 150,
            "",
            BUTTON_BG_SOON_IMG_PATH
        );
        level7Button = new TextButton(
            840, 180,
            220, 140,
            "",
            BUTTON_BG_SOON_IMG_PATH
        );

        level8Button = new TextButton(
            250, 80,
            220, 140,
            "",
            BUTTON_BG_SOON_IMG_PATH
        );
        level9Button = new TextButton(
            530, 70,
            220, 140,
            "",
            BUTTON_BG_SOON_IMG_PATH
        );
        level10Button = new TextButton(
            810, 60,
            220, 140,
            "",
            BUTTON_BG_SOON_IMG_PATH
        );

        menuButton =
            new TextButton(
                35,
                35,
                190,
                80,
                "MENU",
                BUTTON_BG_RED_IMG_PATH
            );
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

        level4Button.setAlpha(0.4f);
        level5Button.setAlpha(0.4f);
        level6Button.setAlpha(0.4f);
        level7Button.setAlpha(0.4f);
        level8Button.setAlpha(0.4f);
        level9Button.setAlpha(0.4f);
        level10Button.setAlpha(0.4f);

        level4Button.draw(main.batch);
        level5Button.draw(main.batch);
        level6Button.draw(main.batch);
        level7Button.draw(main.batch);
        level8Button.draw(main.batch);
        level9Button.draw(main.batch);
        level10Button.draw(main.batch);





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

        if (level1Button.IsHit(x, y)) {

            main.setScreen(
                new ScreenGame(main, 1)
            );
        }

        if (level2Button.IsHit(x, y)) {

            main.setScreen(
                new ScreenGame(main, 2)
            );
        }

        if (level3Button.IsHit(x, y)) {

            main.setScreen(
                new ScreenGame(main, 3)
            );
        }

        if (menuButton.IsHit(x, y)) {

            main.setScreen(
                new ScreenMenu(main)
            );
        }
    }

    @Override
    public void dispose() {

        backgroundTexture.dispose();


        level4Button.dispose();
        level5Button.dispose();
        level6Button.dispose();
        level7Button.dispose();
        level8Button.dispose();
        level9Button.dispose();
        level10Button.dispose();

    }
}
