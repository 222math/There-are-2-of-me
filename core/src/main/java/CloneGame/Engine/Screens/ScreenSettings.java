package CloneGame.Engine.Screens;

import static CloneGame.Engine.Main.GameResources.BACKGROUND_IMG_PATH;
import static CloneGame.Engine.Main.GameResources.BUTTON_BG_IMG_PATH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import CloneGame.Engine.Components.TextButton;
import CloneGame.Engine.Main.Main;

public class ScreenSettings extends ScreenAdapter {

    private final Main main;

    private Texture backgroundTexture;

    private TextButton okButton;

    public ScreenSettings(Main main) {

        this.main = main;

        backgroundTexture =
            new Texture(BACKGROUND_IMG_PATH);

        initButtons();
    }

    private void initButtons() {

        okButton =
            new TextButton(
                540,
                120,
                200,
                70,
                "OK",
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

        if (okButton.IsHit(x, y)) {

            main.setScreen(
                new ScreenMenu(main)
            );
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

        okButton.draw(main.batch);

        main.batch.end();
    }

    @Override
    public void dispose() {

        backgroundTexture.dispose();

        okButton.dispose();
    }
}
