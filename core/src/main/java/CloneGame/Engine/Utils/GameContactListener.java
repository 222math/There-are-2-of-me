package CloneGame.Engine.Utils;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.math.Vector2;
import CloneGame.Engine.Objects.Person;
import CloneGame.Engine.Objects.Portal;
import CloneGame.Engine.Objects.Door;

public class GameContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        Object uA = a.getBody().getUserData();
        Object uB = b.getBody().getUserData();

        Person person = null;
        Door door = null;
        boolean isPortal = false;
        boolean isGround = false;

        if (uA instanceof Person) {
            person = (Person) uA;
            if (uB instanceof Door) door = (Door) uB;
            else if (uB instanceof Portal) isPortal = true;
            else isGround = true; // Платформа или земля
        }
        if (uB instanceof Person) {
            person = (Person) uB;
            if (uA instanceof Door) door = (Door) uA;
            else if (uA instanceof Portal) isPortal = true;
            else isGround = true; // Платформа или земля
        }

        // Обработка земли/платформ
        if (person != null && isGround) {
            person.setOnGround(true);
            person.incrementGroundContacts(); // Считаем контакты с землёй
        }

        // Обработка двери
        if (person != null && door != null) {
            if (!door.isOpen()) {
                
                if (isStandingOnTop(contact, person)) {
                    person.setOnGround(true);
                    person.incrementGroundContacts();
                }
            }
        }

        // Обработка портала
        if (person != null && isPortal) {
            Portal.setInPortal(true);
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        Object uA = a.getBody().getUserData();
        Object uB = b.getBody().getUserData();

        Person person = null;
        Door door = null;
        boolean isGround = false;

        if (uA instanceof Person) {
            person = (Person) uA;
            if (uB instanceof Door) door = (Door) uB;
            else if (!(uB instanceof Portal)) isGround = true;
        }
        if (uB instanceof Person) {
            person = (Person) uB;
            if (uA instanceof Door) door = (Door) uA;
            else if (!(uA instanceof Portal)) isGround = true;
        }

        // Уменьшаем счётчик контактов
        if (person != null && (isGround || (door != null && !door.isOpen()))) {
            person.decrementGroundContacts();
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        Object uA = a.getBody().getUserData();
        Object uB = b.getBody().getUserData();

        // Отключаем коллизию для открытых дверей
        if ((uA instanceof Door && ((Door) uA).isOpen()) ||
            (uB instanceof Door && ((Door) uB).isOpen())) {
            contact.setEnabled(false);
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}

    // Проверяет, стоит ли персонаж НА объекте (контакт снизу)
    private boolean isStandingOnTop(Contact contact, Person person) {
        // Получаем нормаль контакта
        WorldManifold worldManifold = contact.getWorldManifold();
        Vector2 normal = worldManifold.getNormal();

        // Если нормаль направлена вверх - игрок стоит на объекте
        
        float personBottom = person.getY() - person.getHeight() / 2f;

        // Проверяем точки контакта
        for (int i = 0; i < contact.getWorldManifold().getNumberOfContactPoints(); i++) {
            Vector2 point = worldManifold.getPoints()[i];
            // Если точка контакта ниже центра персонажа - он стоит на объекте
            if (point.y <= personBottom + 5) { 
                return true;
            }
        }

        // Дополнительная проверка
        return normal.y > 0.5f;
    }
}
