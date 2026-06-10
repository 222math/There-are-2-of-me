package CloneGame.Engine.Components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class HintWindow {

    private Texture background;
    private BitmapFont font;

    private String text;
    private boolean visible;

    private float x, y, width, height;

    public HintWindow(String text) {
        this.text = text;

        background = new Texture("img_3.png");
        font = new BitmapFont();
        font.getData().setScale(2f);
        font.setColor(Color.WHITE);


        width = 600;
        height = 300;

        x = (Gdx.graphics.getWidth() - width) / 2;
        y = (Gdx.graphics.getHeight() - height) / 2;

        visible = false;
    }

    public void show() {
        visible = true;
    }

    public void hide() {
        visible = false;
    }

    public void toggle() {
        visible = !visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public void render(SpriteBatch batch) {
        if (!visible) return;


        batch.setColor(0, 0, 0, 0.5f); // чёрный с прозрачностью
        batch.draw(
            background,
            0,
            0,
            Gdx.graphics.getWidth(),
            Gdx.graphics.getHeight()
        );

        batch.setColor(1, 1, 1, 1);


        batch.setColor(1, 1, 1, 0.9f);
        batch.draw(background, x, y, width, height);

        batch.setColor(1, 1, 1, 1);


        font.draw(
            batch,
            text,
            x + 20,
            y + height - 20,
            width - 40,
            1,
            true
        );
    }
    public void dispose() {
        background.dispose();
        font.dispose();
    }
}
