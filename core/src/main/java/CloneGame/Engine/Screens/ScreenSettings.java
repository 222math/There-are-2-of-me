package CloneGame.Engine.Screens;

import static CloneGame.Engine.Main.GameResources.*;
import static CloneGame.Engine.Main.GameSettings.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import CloneGame.Engine.Audio.MusicManager;
import CloneGame.Engine.Audio.SoundManager;
import CloneGame.Engine.Components.TextButton;
import CloneGame.Engine.Main.Main;
import CloneGame.Engine.Utils.FontManager;

public class ScreenSettings extends ScreenAdapter {

    private Main main;

    private Texture backgroundTexture;
    private Texture sliderBg;
    private Texture sliderButton;

    private float musicSliderX = 390;
    private float musicSliderY = 420;

    private float soundSliderX = 390;
    private float soundSliderY = 260;

    private float sliderWidth = 500;

    private float musicButtonX = 640;
    private float soundButtonX = 640;

    private float buttonWidth = 40;

    private boolean draggingMusic = false;
    private boolean draggingSound = false;

    private TextButton okButton;

    public ScreenSettings(Main main){

        this.main = main;

        backgroundTexture = new Texture(BACKGROUND_SETTING_IMG_PATH);

        sliderBg = new Texture(SLIDER_BG_STOP_IMG_PATH);

        sliderButton = new Texture(SLIDER_BUT_IMG_PATH  );

        okButton = new TextButton(
            500,
            80,
            280,
            80,
            "OK",
            BUTTON_BG_GREEN_IMG_PATH
        );
    }

    @Override
    public void render(float delta){

        handleInput();

        ScreenUtils.clear(Color.BLACK);

        main.camera.update();

        main.batch.setProjectionMatrix(main.camera.combined);

        main.batch.begin();

        main.batch.draw(backgroundTexture , 0 , 0 , SCREEN_WIDTH , SCREEN_HEIGHT);

        main.batch.draw(sliderBg , musicSliderX , musicSliderY , sliderWidth , 20);

        main.batch.draw(sliderButton , musicButtonX , musicSliderY - 10 , buttonWidth , 40);

        main.batch.draw(sliderBg , soundSliderX , soundSliderY , sliderWidth , 20);

        main.batch.draw(sliderButton , soundButtonX , soundSliderY - 10 , buttonWidth , 40);

        FontManager.font32.draw(main.batch , "MUSIC" , 520 , 510);

        FontManager.font32.draw(main.batch , "SOUNDS" , 500 , 350);

        okButton.draw(main.batch);

        main.batch.end();
    }

    private void handleInput(){

        Vector3 touchPos = new Vector3();

        touchPos.set(
            Gdx.input.getX(),
            Gdx.input.getY(),
            0
        );

        main.camera.unproject(touchPos);

        float x = touchPos.x;
        float y = touchPos.y;

        if(Gdx.input.justTouched()){

            if(
                x >= musicButtonX &&
                    x <= musicButtonX + buttonWidth &&
                    y >= musicSliderY - 10 &&
                    y <= musicSliderY + 30
            ){
                draggingMusic = true;
            }

            if(
                x >= soundButtonX &&
                    x <= soundButtonX + buttonWidth &&
                    y >= soundSliderY - 10 &&
                    y <= soundSliderY + 30
            ){
                draggingSound = true;
            }

            if(okButton.IsHit(x , y)){

                main.setScreen(
                    new ScreenMenu(main)
                );
            }
        }

        if(Gdx.input.isTouched()){

            if(draggingMusic){

                musicButtonX = x - buttonWidth / 2f;

                if(musicButtonX < musicSliderX){
                    musicButtonX = musicSliderX;
                }

                if(musicButtonX > musicSliderX + sliderWidth - buttonWidth){
                    musicButtonX = musicSliderX + sliderWidth - buttonWidth;
                }

                float volume =
                    (musicButtonX - musicSliderX) /
                        (sliderWidth - buttonWidth);

                MusicManager.setVolume(volume);
            }

            if(draggingSound){

                soundButtonX = x - buttonWidth / 2f;

                if(soundButtonX < soundSliderX){
                    soundButtonX = soundSliderX;
                }

                if(soundButtonX > soundSliderX + sliderWidth - buttonWidth){
                    soundButtonX = soundSliderX + sliderWidth - buttonWidth;
                }
                float volume =
                    (soundButtonX - soundSliderX) /
                        (sliderWidth - buttonWidth);

                SoundManager.setVolume(volume);
            }

        } else {

            draggingMusic = false;

            draggingSound = false;
        }
    }

    @Override
    public void dispose(){

        backgroundTexture.dispose();

        sliderBg.dispose();

        sliderButton.dispose();

        okButton.dispose();
    }
}
