package CloneGame.Engine.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import CloneGame.Engine.Audio.SoundManager;

public class PressurePlate extends GameObject {

    private boolean isActivated = false;

    private boolean pressedByPlayer = false;
    private boolean pressedByClone = false;

    private boolean justChanged = false;

    private int id;

    private Texture activeTexture;
    private Texture inactiveTexture;

    public PressurePlate(
        String textureOff,
        String textureOn,
        int x,
        int y,
        int width,
        int height,
        World world,
        int id
    ) {

        super(
            textureOff,
            x,
            y,
            width,
            height,
            world,
            BodyDef.BodyType.KinematicBody
        );

        this.id = id;

        inactiveTexture = texture;
        activeTexture = new Texture(textureOn);
    }

    public int getId() {
        return id;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void updatePlayerState(Person player) {

        Rectangle plateBox = new Rectangle(
            getX() - width / 2f,
            getY() - height / 2f,
            width,
            height
        );

        pressedByPlayer = plateBox.overlaps(
            player.getBoundingBox()
        );

        refreshState();
    }

    public void updateCloneState(
        float cloneX,
        float cloneY,
        int cloneWidth,
        int cloneHeight
    ) {

        Rectangle plateBox = new Rectangle(
            getX() - width / 2f,
            getY() - height / 2f,
            width,
            height
        );

        Rectangle cloneBox = new Rectangle(
            cloneX - cloneWidth / 2f,
            cloneY - cloneHeight / 2f,
            cloneWidth,
            cloneHeight
        );

        pressedByClone = plateBox.overlaps(cloneBox);

        refreshState();
    }

    private void refreshState() {

        boolean newState =
            pressedByPlayer || pressedByClone;

        if (newState != isActivated) {

            SoundManager.playPlate();

            isActivated = newState;
            justChanged = true;

            texture = isActivated
                ? activeTexture
                : inactiveTexture;
        }
    }

    public boolean wasJustChanged() {
        return justChanged;
    }
    public void setPressedByClone(boolean pressed) {

        pressedByClone = pressed;

        refreshState();
    }

    public void resetJustChanged() {
        justChanged = false;
    }

    @Override
    public void draw(SpriteBatch batch) {

        batch.draw(
            texture,
            getX() - width / 2f,
            getY() - height / 2f,
            width,
            height
        );
    }

    @Override
    public void dispose() {

        if (activeTexture != null) {
            activeTexture.dispose();
        }

        super.dispose();
    }
}
