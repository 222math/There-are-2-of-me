package CloneGame.Engine.Components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

public class HintButton {

    private Texture texture;
    private Rectangle bounds;

    public HintButton() {
        texture = new Texture("button/hint.png");

        float size = 80;
        bounds = new Rectangle(
            925,
            625,
            90,
            90
        );
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public boolean isClicked(float x, float y) {
        return bounds.contains(x, y);
    }

    public void dispose() {
        texture.dispose();
    }
}
