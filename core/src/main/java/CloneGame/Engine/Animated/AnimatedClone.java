
package CloneGame.Engine.Animated;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AnimatedClone {
    private Texture idleRight, idleLeft;
    private Texture[] walkRight, walkLeft;
    private Texture jumpRight, jumpLeft;

    private int currentFrame = 0;
    private float frameTimer = 0;
    private float frameDuration = 0.1f;

    private boolean facingRight = true;
    private boolean isWalking = false;
    private boolean isJumping = false;

    private float x, y;
    private int width, height;

    public AnimatedClone(Texture idleRight, Texture idleLeft,
                         Texture[] walkRight, Texture[] walkLeft,
                         Texture jumpRight, Texture jumpLeft,
                         int width, int height) {
        this.idleRight = idleRight;
        this.idleLeft = idleLeft;
        this.walkRight = walkRight;
        this.walkLeft = walkLeft;
        this.jumpRight = jumpRight;
        this.jumpLeft = jumpLeft;
        this.width = width;
        this.height = height;
    }

    public void setState(boolean walking, boolean jumping, boolean facingRight) {
        this.isWalking = walking;
        this.isJumping = jumping;
        this.facingRight = facingRight;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void update(float delta) {
        frameTimer += delta;
        if (frameTimer >= frameDuration) {
            frameTimer = 0;
            currentFrame++;
        }
    }

    public void draw(SpriteBatch batch) {
        Texture tex;

        if (isJumping) {
            tex = facingRight ? jumpRight : jumpLeft;
        } else if (isWalking) {
            Texture[] frames = facingRight ? walkRight : walkLeft;
            tex = frames[currentFrame % frames.length];
        } else {
            tex = facingRight ? idleRight : idleLeft;
        }

       
        batch.setColor(1, 1, 1, 0.45f);  
        batch.draw(tex, x - width/2f, y - height/2f, width, height);
        batch.setColor(Color.WHITE);  
    }
}
