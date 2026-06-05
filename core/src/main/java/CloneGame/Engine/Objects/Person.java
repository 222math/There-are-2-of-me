package CloneGame.Engine.Objects;

import static CloneGame.Engine.Main.GameSettings.SCREEN_HEIGHT;
import static CloneGame.Engine.Main.GameSettings.SCREEN_WIDTH;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import CloneGame.Engine.Audio.SoundManager;

public class Person extends GameObject {
    private int speedX = 15;
    private static final float JUMP_FORCE = 15f;
    private static final float MAX_FALL_SPEED = -20f;
    private boolean isOnGround = false;
    private int groundContacts = 0; // СЧЁТЧИК контактов с землёй

    public Person(String texture, int x, int y, int width, int height, World world) {
        super(texture, x, y, width, height, world, BodyDef.BodyType.DynamicBody);
    }

    public void setOnGround(boolean onGround) {
        this.isOnGround = onGround;
    }

    public boolean isOnGround() {
        return groundContacts > 0; // Используем счётчик 
    }

    
    public void incrementGroundContacts() {
        groundContacts++;
        isOnGround = true;
    }

    public void decrementGroundContacts() {
        groundContacts--;
        if (groundContacts < 0) groundContacts = 0;
        if (groundContacts == 0) {
            isOnGround = false;
        }
    }

    public void jump() {
        if (groundContacts > 0) { // Прыжок только если есть контакт с землёй
            body.setLinearVelocity(body.getLinearVelocity().x, 0);
            body.applyLinearImpulse(0, JUMP_FORCE, body.getPosition().x, body.getPosition().y, true);
            groundContacts = 0;
            isOnGround = false;
            SoundManager.playJump();
        }
    }

    public void update() {
        Vector2 vel = body.getLinearVelocity();
        if (vel.y < MAX_FALL_SPEED) {
            body.setLinearVelocity(vel.x, MAX_FALL_SPEED);
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        putInFrame();
        super.draw(batch);
    }

    private void putInFrame() {
        if (getY() > SCREEN_HEIGHT - height / 2f) {
            setY(SCREEN_HEIGHT - height / 2f);
            body.setLinearVelocity(body.getLinearVelocity().x, 0);
        }
        if (getY() < height / 2f) {
            setY(height / 2f);
            body.setLinearVelocity(body.getLinearVelocity().x, 0);
        }
        if (getX() < width / 2f) {
            setX(width / 2f);
        }
        if (getX() > SCREEN_WIDTH - width / 2f) {
            setX(SCREEN_WIDTH - width / 2f);
        }
    }

    public Rectangle getBoundingBox() {
        return new Rectangle(getX() - width / 2f, getY() - height / 2f, width, height);
    }

    public void moveRight() {
        body.setLinearVelocity(speedX, body.getLinearVelocity().y);
    }

    public void moveLeft() {
        body.setLinearVelocity(-speedX, body.getLinearVelocity().y);
    }

    public void stop() {
        body.setLinearVelocity(0, body.getLinearVelocity().y);
    }

    public float getVelocityX() {
        return body.getLinearVelocity().x;
    }

    public float getHeight() {
        return height;
    }
}
