package CloneGame.Engine.Animated;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class AnimatedPlayer extends Sprite {
    private Texture idleTexture;
    private Texture idleLeftTexture;
    private Texture[] walkFramesRight;
    private Texture[] walkFramesLeft;
    private Texture jumpTexture;
    private Texture jumpLeftTexture;

    private int currentFrame = 0;
    private float frameTimer = 0;
    private float frameDuration = 0.1f;

    private enum State { IDLE, WALKING, JUMPING }
    private State currentState = State.IDLE;
    private boolean facingRight = true;

    public AnimatedPlayer(Texture idleRight, Texture idleLeft,
                          Texture[] walkFramesRight, Texture[] walkFramesLeft,
                          Texture jumpRight, Texture jumpLeft) {
        this.idleTexture = idleRight;
        this.idleLeftTexture = idleLeft;
        this.walkFramesRight = walkFramesRight;
        this.walkFramesLeft = walkFramesLeft;
        this.jumpTexture = jumpRight;
        this.jumpLeftTexture = jumpLeft;

        setTexture(idleRight);
        setRegion(0, 0, idleRight.getWidth(), idleRight.getHeight());
    }

    public void update(float delta, boolean isWalking, boolean isJumping, boolean isMovingRight) {
        // ОБНОВЛЯЕМ НАПРАВЛЕНИЕ ВСЕГДА, когда есть движение или был прыжок
        if (isWalking || isJumping) {
            facingRight = isMovingRight;
        }

        State newState;
        if (isJumping) {
            newState = State.JUMPING;
        } else if (isWalking) {
            newState = State.WALKING;
        } else {
            newState = State.IDLE;
        }

        if (newState != currentState) {
            currentState = newState;
            currentFrame = 0;
            frameTimer = 0;
        }

        switch (currentState) {
            case IDLE:
                if (facingRight) {
                    setTexture(idleTexture);
                } else {
                    setTexture(idleLeftTexture);
                }
                break;

            case JUMPING:
                if (facingRight) {
                    setTexture(jumpTexture);
                } else {
                    setTexture(jumpLeftTexture);
                }
                break;

            case WALKING:
                Texture[] currentWalkFrames = facingRight ? walkFramesRight : walkFramesLeft;
                frameTimer += delta;
                if (frameTimer >= frameDuration) {
                    frameTimer = 0;
                    currentFrame = (currentFrame + 1) % currentWalkFrames.length;
                    Texture frame = currentWalkFrames[currentFrame];
                    setTexture(frame);
                }
                break;
        }
        setRegion(0, 0, getTexture().getWidth(), getTexture().getHeight());
    }

    public void dispose() {
        if (idleTexture != null) idleTexture.dispose();
        if (idleLeftTexture != null) idleLeftTexture.dispose();
        if (jumpTexture != null) jumpTexture.dispose();
        if (jumpLeftTexture != null) jumpLeftTexture.dispose();
        for (Texture t : walkFramesRight) if (t != null) t.dispose();
        for (Texture t : walkFramesLeft) if (t != null) t.dispose();
    }
}
