package CloneGame.Engine.Objects;

import static CloneGame.Engine.Main.GameSettings.SCALE;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
public class GameObject {

    public int width;
    public int height;

    public Body body;
    Texture texture;
    BodyDef.BodyType type;

    GameObject(String texturePath, int x, int y, int width, int height, World world , BodyDef.BodyType type) {
        this.width = width;
        this.height = height;
        this.type = type;

        texture = new Texture(texturePath);
        body = createBody(x, y, world);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, getX() - (width / 2f), getY() - (height / 2f), width, height);
    }

    public float getX() {
        return (int) (body.getPosition().x / SCALE);
    }

    public float getY() {
        return (int) (body.getPosition().y / SCALE);
    }

    public void setX(float x) {
        body.setTransform(x * SCALE, body.getPosition().y, 0);
    }

    public void setY(float y) {
        body.setTransform(body.getPosition().x, y * SCALE, 0);
    }

    private Body createBody(float x, float y, World world) {
        BodyDef def = new BodyDef();
        def.type = type;
        def.fixedRotation = true;
        Body body = world.createBody(def);


        PolygonShape rectangleShape = new PolygonShape();

        rectangleShape.setAsBox((width * SCALE) / 2f, (height * SCALE) / 2f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = rectangleShape;
        fixtureDef.density = 0.1f;
        fixtureDef.friction = 1f;

        body.createFixture(fixtureDef);
        rectangleShape.dispose(); // Обязательно освобождаем память

        body.setTransform(x * SCALE, y * SCALE, 0);
        body.setUserData(this);
        return body;
    }
    public void dispose(){
        texture.dispose();
    }

}
