package CloneGame.Engine.Components;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class TextButton {
    BitmapFont font;
    String text;
    Texture texture;
    public int x;
    public int y;
    int textX , textY;
    int textWidth , textHeight;
    int buttonWidth = 700, buttonHeight = 200;
    public TextButton(int x , int y , int width , int height, String text , String pathToTexture){
        this.x = x;
        this.y = y;
        this.buttonWidth = width;
        this.buttonHeight = height;
        this.text = text;

        font = new BitmapFont();
        font.getData().setScale(5f);
        font.setColor(Color.WHITE);

        GlyphLayout gl = new GlyphLayout(font , text);
        textHeight = (int) gl.height;
        textWidth = (int) gl.width;

        texture = new Texture(pathToTexture);
        textX = x + (buttonWidth - textWidth)/2;
        textY = y + (buttonHeight + textHeight)/2;
    }
    public void draw(Batch batch){
        batch.draw(texture , x , y , buttonWidth , buttonHeight);
        font.draw(batch , text , textX , textY);
    }
    public void dispose(){
        texture.dispose();
        font.dispose();
    }
    public  boolean IsHit(float tx , float ty){
        if (tx >= x && tx <= x + buttonWidth && ty >= y && ty <= y + buttonHeight){
            return true;
        } else {
            return false;
        }
    }
}
