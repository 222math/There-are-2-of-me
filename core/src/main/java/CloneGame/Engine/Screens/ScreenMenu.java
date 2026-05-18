package CloneGame.Engine.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import CloneGame.Engine.Components.TextButton;
import CloneGame.Engine.Main.Main;

public class ScreenMenu extends ScreenAdapter {
    Main main;
    private TextButton playButton;

    public ScreenMenu(Main main){
        this.main = main;

        // Кнопка PLAY по центру экрана
        playButton = new TextButton(
            (1280 - 700) / 2,   // x по центру
            (720 - 200) / 2,    // y по центру
            700, 200,           // ширина, высота
            "PLAY",             // текст
            "img.png"           // текстура кнопки
        );
    }

    @Override
    public void render(float delta){
        main.camera.update();
        main.batch.setProjectionMatrix(main.camera.combined);
        ScreenUtils.clear(Color.DARK_GRAY);

        handleInput();

        main.batch.begin();
        playButton.draw(main.batch);
        main.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            main.camera.unproject(touchPos);

            if (playButton.IsHit(touchPos.x, touchPos.y)) {
                main.setScreen(new ScreenLevelSelect(main));
            }
        }
    }

    @Override
    public void dispose() {
        playButton.dispose();
    }
}
