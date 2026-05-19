package CloneGame.Engine.Components;

import static CloneGame.Engine.Main.GameResources.*;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import CloneGame.Engine.Utils.FontManager;

public class TutorialWindow {

    private BitmapFont font;

    // Фон окна
    private Texture backgroundTexture;

    // Иконки
    private Texture recTexture;
    private Texture replayTexture;

    private Texture plateTexture;
    private Texture doorTexture;

    private Texture portalTexture;
    private Texture cloneTexture;

    // Кнопка закрытия
    private Texture closeTexture;

    public TutorialWindow() {

        font = FontManager.font32;

        backgroundTexture =
            new Texture(TUTORIAL_BG_PATH);

        recTexture =
            new Texture(BUTTON_BG_GREEN_IMG_PATH);

        replayTexture =
            new Texture(BUTTON_BG_GREEN_IMG_PATH);

        plateTexture =
            new Texture(
                PLATE_ACTIVATED_IMG_PATH
            );

        doorTexture =
            new Texture(DOOR_IMG_PATH);

        portalTexture =
            new Texture(PORTAL_IMG_PATH);

        cloneTexture =
            new Texture(
                ZORO_IDLE_LEFT
            );

        closeTexture =
            new Texture(BUTTON_BG_GREEN_IMG_PATH);
    }

    public void draw(SpriteBatch batch) {

        // ===== СИНИЙ ПОЛУПРОЗРАЧНЫЙ ФОН =====

        Color oldColor = batch.getColor();

        batch.setColor(
            0.05f,
            0.08f,
            0.15f,
            0.95f
        );

        batch.draw(
            backgroundTexture,
            180,
            60,
            920,
            600
        );

        batch.setColor(oldColor);

        // ===== КНОПКА CLOSE =====

        batch.setColor(
            0.7f,
            0.1f,
            0.1f,
            1f
        );

        batch.draw(
            closeTexture,
            980,
            590,
            80,
            50
        );

        batch.setColor(Color.WHITE);

        font.draw(
            batch,
            "X",
            1010,
            625
        );

        // ===== REC =====

        batch.draw(
            recTexture,
            240,
            560,
            60,
            60
        );

        font.draw(
            batch,
            "REC - START RECORDING",
            360,
            600
        );

        // ===== REPLAY =====

        batch.draw(
            replayTexture,
            240,
            480,
            60,
            60
        );

        font.draw(
            batch,
            "REPLAY - CREATE CLONE",
            360,
            520
        );

        // ===== CLONE =====

        batch.setColor(
            1,
            1,
            1,
            0.45f
        );

        batch.draw(
            cloneTexture,
            240,
            390,
            70,
            90
        );

        batch.setColor(Color.WHITE);

        font.draw(
            batch,
            "CLONE REPEATS YOUR ACTIONS",
            360,
            440
        );

        // ===== PLATE =====

        batch.setColor(
            0.2f,
            1f,
            0.2f,
            1f
        );

        batch.draw(
            plateTexture,
            240,
            320,
            70,
            25
        );

        batch.setColor(Color.WHITE);

        font.draw(
            batch,
            "PLATE OPENS DOORS",
            360,
            350
        );

        // ===== DOOR =====

        batch.draw(
            doorTexture,
            250,
            190,
            40,
            100
        );

        font.draw(
            batch,
            "DOOR BLOCKS THE WAY",
            360,
            250
        );

        // ===== PORTAL =====

        batch.draw(
            portalTexture,
            240,
            70,
            70,
            90
        );

        font.draw(
            batch,
            "PORTAL FINISHES LEVEL",
            360,
            130
        );
    }

    public boolean isClosePressed(float x, float y) {

        return x >= 980 &&
            x <= 1060 &&
            y >= 590 &&
            y <= 640;
    }

    public void dispose() {

        backgroundTexture.dispose();

        recTexture.dispose();

        replayTexture.dispose();

        plateTexture.dispose();

        doorTexture.dispose();

        portalTexture.dispose();

        cloneTexture.dispose();

        closeTexture.dispose();
    }
}
