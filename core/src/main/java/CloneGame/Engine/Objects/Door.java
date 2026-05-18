package CloneGame.Engine.Objects;

import static CloneGame.Engine.Main.GameSettings.SCALE;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;

import java.util.List;

public class Door extends GameObject {

    private boolean isOpen;

    private boolean pendingOpen = false;
    private boolean pendingClose = false;

    private final int id;
    private final int[] requiredPlates;

    private final World worldRef;


    private final float originalX;
    private final float originalY;

    public Door(
        String texturePath,
        int x,
        int y,
        int width,
        int height,
        World world,
        int id,
        int[] requiredPlates
    ) {

        super(
            texturePath,
            x,
            y,
            width,
            height,
            world,
            BodyDef.BodyType.StaticBody
        );

        this.id = id;
        this.requiredPlates = requiredPlates;

        this.worldRef = world;

        this.originalX = x;
        this.originalY = y;

        this.isOpen = false;
    }


    public void checkAndUpdate(List<PressurePlate> plates) {

        if (requiredPlates == null || plates == null) {
            return;
        }

        boolean allPressed = true;

        for (int requiredId : requiredPlates) {

            boolean foundPressedPlate = false;

            for (PressurePlate plate : plates) {

                if (plate.getId() == requiredId
                    && plate.isActivated()) {

                    foundPressedPlate = true;
                    break;
                }
            }

            if (!foundPressedPlate) {
                allPressed = false;
                break;
            }
        }

        if (allPressed && !isOpen) {
            openDoor();
        }
        else if (!allPressed && isOpen) {
            closeDoor();
        }
    }



    private void openDoor() {

        if (isOpen) {
            return;
        }

        isOpen = true;
        pendingOpen = true;

        System.out.println("Door " + id + " OPENING");
    }

    private void closeDoor() {

        if (!isOpen) {
            return;
        }

        isOpen = false;
        pendingClose = true;

        System.out.println("Door " + id + " CLOSING");
    }


    public void applyPhysicsChanges() {



        if (pendingOpen) {

            pendingOpen = false;

            if (body != null) {

                worldRef.destroyBody(body);
                body = null;

                System.out.println("Door " + id + " OPENED");
            }
        }



        if (pendingClose) {

            pendingClose = false;

            if (body == null) {

                BodyDef bodyDef = new BodyDef();

                bodyDef.type = BodyDef.BodyType.StaticBody;

                bodyDef.position.set(
                    originalX * SCALE,
                    originalY * SCALE
                );

                body = worldRef.createBody(bodyDef);

                PolygonShape shape = new PolygonShape();

                shape.setAsBox(
                    (width * SCALE) / 2f,
                    (height * SCALE) / 2f
                );

                FixtureDef fixtureDef = new FixtureDef();

                fixtureDef.shape = shape;
                fixtureDef.friction = 1f;
                fixtureDef.density = 1f;

                body.createFixture(fixtureDef);

                shape.dispose();

                body.setUserData(this);

                System.out.println("Door " + id + " CLOSED");
            }
        }
    }



    @Override
    public void draw(SpriteBatch batch) {

        // Рисуем только закрытую дверь
        if (!isOpen && body != null) {

            batch.draw(
                texture,
                getX() - width / 2f,
                getY() - height / 2f,
                width,
                height
            );
        }
    }



    public boolean isOpen() {
        return isOpen;
    }

    public int getId() {
        return id;
    }



    @Override
    public float getX() {

        if (body == null) {
            return originalX;
        }

        return super.getX();
    }

    @Override
    public float getY() {

        if (body == null) {
            return originalY;
        }

        return super.getY();
    }
}
