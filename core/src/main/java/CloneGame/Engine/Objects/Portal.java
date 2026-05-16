package CloneGame.Engine.Objects;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class Portal extends GameObject{
    public Portal(String texture , int  x , int y , int width , int height , World world){
        super(texture , x , y , width , height , world , BodyDef.BodyType.StaticBody);
    }
    private static boolean isInPortal;

    public static void setInPortal(boolean inPortal) {
        isInPortal = inPortal;
    }
    public boolean getInPortal(){
        return isInPortal;
    }
}
