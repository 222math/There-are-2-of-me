package CloneGame.Engine.Utils;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class FontManager {

    public static BitmapFont font32;

    public static BitmapFont font48;

    public static void load() {

        FreeTypeFontGenerator generator =
            new FreeTypeFontGenerator(
                Gdx.files.internal(
                    "fonts/font.ttf"
                )
            );

        FreeTypeFontGenerator
            .FreeTypeFontParameter parameter =
            new FreeTypeFontGenerator
                .FreeTypeFontParameter();

        parameter.size = 32;

        font32 =
            generator.generateFont(parameter);

        parameter.size = 48;

        font48 =
            generator.generateFont(parameter);

        generator.dispose();
    }

    public static void dispose() {

        if (font32 != null) {
            font32.dispose();
        }

        if (font48 != null) {
            font48.dispose();
        }
    }
}
