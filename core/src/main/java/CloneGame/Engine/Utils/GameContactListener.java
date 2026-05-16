package CloneGame.Engine.Utils;

import com.badlogic.gdx.physics.box2d.*;
import CloneGame.Engine.Objects.Person;
import CloneGame.Engine.Objects.Portal;
import CloneGame.Engine.Objects.PressurePlate;

public class GameContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        Object uA = a.getBody().getUserData();
        Object uB = b.getBody().getUserData();

        if (uA instanceof Person) ((Person) uA).setOnGround(true);
        if (uB instanceof Person) ((Person) uB).setOnGround(true);

        if (uA instanceof Person && uB instanceof Portal) Portal.setInPortal(true);
        if (uB instanceof Person && uA instanceof Portal) Portal.setInPortal(true);

        // Активируем плиту
        if (uA instanceof Person && uB instanceof PressurePlate) {
            ((PressurePlate) uB).setActivated(true);
        }
        if (uB instanceof Person && uA instanceof PressurePlate) {
            ((PressurePlate) uA).setActivated(true);
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        Object uA = a.getBody().getUserData();
        Object uB = b.getBody().getUserData();

        if (uA instanceof Person) ((Person) uA).setOnGround(false);
        if (uB instanceof Person) ((Person) uB).setOnGround(false);

        // Деактивируем плиту когда персонаж уходит
        if (uA instanceof Person && uB instanceof PressurePlate) {
            ((PressurePlate) uB).setActivated(false);
        }
        if (uB instanceof Person && uA instanceof PressurePlate) {
            ((PressurePlate) uA).setActivated(false);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}
}
