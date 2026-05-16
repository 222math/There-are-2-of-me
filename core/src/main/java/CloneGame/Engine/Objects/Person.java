package CloneGame.Engine.Objects;

import static CloneGame.Engine.Main.GameSettings.SCREEN_HEIGHT;
import static CloneGame.Engine.Main.GameSettings.SCREEN_WIDTH;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class Person extends GameObject{
    int speedX = 25;
    private static final float JUMP_FORCE = 12f;
    private static final float MAX_FALL_SPEED = -20f;
    private boolean isOnGround = false;

    public void setOnGround(boolean onGround) {
        isOnGround = onGround;
    }

    public Person(String texture , int  x , int y , int width , int height , World world){
        super(texture , x , y , width , height , world , BodyDef.BodyType.DynamicBody);
    }
    public void jump(){
        if (isOnGround){
            body.setLinearVelocity(body.getLinearVelocity().x , 0);
            body.applyLinearImpulse(0 , JUMP_FORCE , body.getPosition().x , body.getPosition().y , true);
        }
    }
    public void update(){
        Vector2 vel = body.getLinearVelocity();
        if(vel.y < MAX_FALL_SPEED){
            body.setLinearVelocity(0 , MAX_FALL_SPEED);
        }
    }

    @Override
    public void draw(SpriteBatch batch){
        putInFrame();
        super.draw(batch);
    }
    private void putInFrame(){
        if(getY() > SCREEN_HEIGHT - height/2){
            setY(SCREEN_HEIGHT - height/2);
        }
        if(getY() < height/2){
            setY(height/2);
        }
        if (getX() < width/2){
            setX(width/2);
        }
        if (getX()>SCREEN_WIDTH - width/2){
            setX(SCREEN_WIDTH - width/2);
        }
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
}
