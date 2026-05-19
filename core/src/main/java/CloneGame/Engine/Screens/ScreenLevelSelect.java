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

    private TextButton menuButton;

    public ScreenLevelSelect(Main main) {

        this.main = main;

        backgroundTexture =
            new Texture(BACKGROUND_MENU_IMG_PATH);

        level1Button =
            new TextButton(
                180,
                320,
                220,
                140,
                "LEVEL 1",
                BUTTON_BG_GREEN_IMG_PATH
            );

        level2Button =
            new TextButton(
                530,
                320,
                220,
                140,
                "LEVEL 2",
                BUTTON_BG_GREEN_IMG_PATH
            );

        level3Button =
            new TextButton(
                880,
                320,
                220,
                140,
                "LEVEL 3",
                BUTTON_BG_GREEN_IMG_PATH
            );

        menuButton =
            new TextButton(
                40,
                620,
                160,
                70,
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

        level1Button.draw(main.batch);

        level2Button.draw(main.batch);

        level3Button.draw(main.batch);

        menuButton.draw(main.batch);

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

        level1Button.dispose();

        level2Button.dispose();

        level3Button.dispose();

        menuButton.dispose();
    }
}
