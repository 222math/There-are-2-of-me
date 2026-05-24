package CloneGame.Engine.Components;

import static CloneGame.Engine.Main.GameResources.*;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import CloneGame.Engine.Utils.FontManager;

public class TutorialWindow {

    private BitmapFont font;
    private BitmapFont titleFont;

    private Texture bg;

    private Texture leftBtn;
    private Texture rightBtn;
    private Texture jumpBtn;

    private Texture recBtn;
    private Texture stopBtn;
    private Texture replayBtn;

    private Texture plate;
    private Texture door;
    private Texture portal;
    private Texture clone;

    private TextButton closeButton;

    public TutorialWindow() {

        font = FontManager.font32;
        titleFont = FontManager.font32;

        bg = new Texture(TUTORIAL_BG_PATH);

        leftBtn = new Texture(LEFT_BUTTON_IMG_PATH);
        rightBtn = new Texture(RIGHT_BUTTON_IMG_PATH);
        jumpBtn = new Texture(JUMP_BUTTON_IMG_PATH);

        recBtn = new Texture(BUTTON_BG_RECORD_IMG_PATH);
        stopBtn = new Texture(BUTTON_BG_STOP_IMG_PATH);
        replayBtn = new Texture(BUTTON_BG_REPLAY_IMG_PATH);

        plate = new Texture(PLATE_ACTIVATED_IMG_PATH);
        door = new Texture(DOOR_IMG_PATH);
        portal = new Texture(PORTAL_IMG_PATH);

        clone = new Texture(ZORO_IDLE_RIGHT);

        closeButton = new TextButton(
            980,
            600,
            185,
            60,
            "CLOSE",
            BUTTON_BG_RED_IMG_PATH
        );
    }

    public void draw(SpriteBatch batch) {

        batch.setColor(
            0f,
            0f,
            0f,
            0.88f
        );

        batch.draw(
            bg,
            60,
            25,
            1160,
            670
        );

        batch.setColor(Color.WHITE);

        titleFont.setColor(Color.GOLD);

        titleFont.draw(
            batch,
            "HOW TO PLAY",
            310,
            655
        );

        font.setColor(Color.WHITE);

        closeButton.draw(batch);

        // ===== LEFT =====

        batch.draw(
            leftBtn,
            120,
            500,
            70,
            70
        );

        font.draw(
            batch,
            "MOVE LEFT",
            230,
            545
        );

        batch.draw(
            rightBtn,
            120,
            410,
            70,
            70
        );

        font.draw(
            batch,
            "MOVE RIGHT",
            230,
            455
        );

        batch.draw(
            jumpBtn,
            120,
            320,
            70,
            70
        );

        font.draw(
            batch,
            "JUMP",
            230,
            365
        );

        batch.draw(
            recBtn,
            115,
            220,
            90,
            55
        );

        font.draw(
            batch,
            "START RECORDING",
            230,
            258
        );

        batch.draw(
            stopBtn,
            115,
            135,
            90,
            55
        );

        font.draw(
            batch,
            "STOP RECORDING",
            230,
            173
        );

        batch.draw(
            replayBtn,
            115,
            50,
            90,
            55
        );

        font.draw(
            batch,
            "CREATE CLONE",
            230,
            88
        );

        // ===== RIGHT =====

        batch.setColor(
            1f,
            1f,
            1f,
            0.5f
        );

        batch.draw(
            clone,
            620,
            500,
            80,
            100
        );

        batch.setColor(Color.WHITE);

        font.draw(
            batch,
            "CLONE REPEATS",
            760,
            555
        );

        font.draw(
            batch,
            "YOUR ACTIONS",
            760,
            515
        );

        batch.draw(
            plate,
            620,
            365,
            75,
            75
        );

        font.draw(
            batch,
            "PLATES OPEN",
            760,
            425
        );

        font.draw(
            batch,
            "DOORS",
            760,
            385
        );

        batch.draw(
            door,
            620,
            220,
            70,
            120
        );

        font.draw(
            batch,
            "DOORS BLOCK",
            760,
            300
        );

        font.draw(
            batch,
            "YOUR PATH",
            760,
            260
        );

        batch.draw(
            portal,
            610,
            40,
            90,
            120
        );

        font.draw(
            batch,
            "PORTAL FINISHES",
            760,
            125
        );

        font.draw(
            batch,
            "THE LEVEL",
            760,
            85
        );
    }

    public boolean isClosePressed(
        float x,
        float y
    ) {

        return closeButton.IsHit(x, y);
    }

    public void dispose() {

        bg.dispose();

        leftBtn.dispose();
        rightBtn.dispose();
        jumpBtn.dispose();

        recBtn.dispose();
        stopBtn.dispose();
        replayBtn.dispose();

        plate.dispose();
        door.dispose();
        portal.dispose();

        clone.dispose();

        closeButton.dispose();
    }
}
