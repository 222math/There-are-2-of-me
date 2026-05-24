package CloneGame.Engine.Components;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import CloneGame.Engine.Utils.FontManager;

public class TextButton {
    BitmapFont font;
    String text;
    Texture texture;
    public int x;
    public int y;
    int textX , textY;
    int textWidth , textHeight;
    int buttonWidth = 700, buttonHeight = 200;
    private float alpha = 1f;
    public TextButton(int x , int y , int width , int height, String text , String pathToTexture){
        this.x = x;
        this.y = y;
        this.buttonWidth = width;
        this.buttonHeight = height;
        this.text = text;

        font = FontManager.font32;


        GlyphLayout gl = new GlyphLayout(font , text);
        textHeight = (int) gl.height;
        textWidth = (int) gl.width;

        texture = new Texture(pathToTexture);
        textX = x + (buttonWidth - textWidth)/2;
        textY = y + (buttonHeight + textHeight)/2;
    }
    public void draw(Batch batch){
        batch.setColor(1, 1, 1, alpha);
        batch.draw(texture , x , y , buttonWidth , buttonHeight);
        font.draw(batch , text , textX , textY);
        batch.setColor(Color.WHITE);
    }
    public void dispose(){
        texture.dispose();
    }
    public  boolean IsHit(float tx , float ty){
        if (tx >= x && tx <= x + buttonWidth && ty >= y && ty <= y + buttonHeight){
            return true;
        } else {
            return false;
        }
    }
    public void setAlpha(float alpha){
        this.alpha = alpha;
    }
}
