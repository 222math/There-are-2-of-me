package CloneGame.Engine.Screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

import CloneGame.Engine.Main.Main;

public class ScreenMenu extends ScreenAdapter {
    Main main;
    public ScreenMenu(Main main){
        this.main = main;
    }
    @Override
    public void render(float delta){
        main.stepWorld();

        main.camera.update();
        main.batch.setProjectionMatrix(main.camera.combined);
        ScreenUtils.clear(Color.CLEAR);



        main.batch.begin();
        main.batch.end();
    }
}
