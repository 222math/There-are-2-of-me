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

public class ScreenWin extends ScreenAdapter {
    private Main main;
    private int completedLevel;
    private Texture backgroundTexture;
    private TextButton nextButton;
    private TextButton menuButton;

    public ScreenWin(Main main, int completedLevel) {
        this.main = main;
        this.completedLevel = completedLevel;
        backgroundTexture = new Texture(BACKGROUND_WIN_IMG_PATH);
        nextButton = new TextButton(350, 300, 220, 140, "NEXT", BUTTON_BG_GREEN_IMG_PATH);
        menuButton = new TextButton(710, 200, 220, 140, "LEVELS", BUTTON_BG_RED_IMG_PATH);
    }

    @Override
    public void render(float delta) {
        handleInput();
        ScreenUtils.clear(Color.BLACK);
        main.camera.update();
        main.batch.setProjectionMatrix(main.camera.combined);
        main.batch.begin();
        main.batch.draw(backgroundTexture, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        //nextButton.draw(main.batch);
        menuButton.draw(main.batch);
        main.batch.end();
    }

    private void handleInput() {
        if (!Gdx.input.justTouched()) return;
        Vector3 touchPos = new Vector3();
        touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        main.camera.unproject(touchPos);
        float x = touchPos.x;
        float y = touchPos.y;
//        if (nextButton.IsHit(x, y)) {
//            main.setScreen(new ScreenGame(main, completedLevel + 1));
//        }
        if (menuButton.IsHit(x, y)) {
            main.setScreen(new ScreenLevelSelect(main));
        }
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        //nextButton.dispose();
        menuButton.dispose();
    }
}
