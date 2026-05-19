package CloneGame.Engine.Main;
import static CloneGame.Engine.Main.GameSettings.POSITION_ITERATIONS;
import static CloneGame.Engine.Main.GameSettings.SCREEN_HEIGHT;
import static CloneGame.Engine.Main.GameSettings.SCREEN_WIDTH;
import static CloneGame.Engine.Main.GameSettings.STEP_TIME;
import static CloneGame.Engine.Main.GameSettings.VELOCITY_ITERATIONS;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;

import CloneGame.Engine.Audio.SoundManager;
import CloneGame.Engine.Screens.ScreenGame;
import CloneGame.Engine.Screens.ScreenMenu;
import CloneGame.Engine.Utils.FontManager;
import CloneGame.Engine.Utils.GameContactListener;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    public SpriteBatch batch;

    public OrthographicCamera camera;
    public World world;
    ScreenGame screenGame;
    ScreenMenu screenMenu;
    float accumulator = 0;
    public Vector3 touch;
    public GameContactListener contactListener;  // должно быть public, не private




    @Override
    public void create() {
        Box2D.init();
        world = new World(new Vector2(0 , -20f) , true);
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false , SCREEN_WIDTH , SCREEN_HEIGHT);
        world.setContactListener(new GameContactListener());
        contactListener = new GameContactListener();
        world.setContactListener(contactListener);
        SoundManager.load();
        FontManager.load();
        screenMenu = new ScreenMenu(this );
        setScreen(screenMenu);
    }

    public void dispose(){
        batch.dispose();
    }

    public void stepWorld() {
        float delta = Gdx.graphics.getDeltaTime();
        accumulator += Math.min(delta, 0.25f);

        if (accumulator >= STEP_TIME) {
            accumulator -= STEP_TIME;
            world.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        }
    }

}
