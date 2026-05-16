package CloneGame.Engine.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class PressurePlate extends GameObject {
    private boolean isActivated = false;
    private int id;
    private Texture activeTexture;

    public PressurePlate(String textureOff, String textureOn,
                         int x, int y, int width, int height, World world, int id) {
        super(textureOff, x, y, width, height, world, BodyDef.BodyType.StaticBody);
        this.id = id;
        this.activeTexture = new Texture(textureOn);
    }

    public int getId() {
        return id;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean activated) {
        this.isActivated = activated;
    }

    @Override
    public void draw(SpriteBatch batch) {
        // ИСПОЛЬЗУЕМ getX() и getY() из GameObject, которые учитывают SCALE!
        batch.draw(isActivated ? activeTexture : texture,
            getX() - width / 2f,
            getY() - height / 2f,
            width, height);
    }

    @Override
    public void dispose() {
        if (activeTexture != null) activeTexture.dispose();
        super.dispose();
    }
}
