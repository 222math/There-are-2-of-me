package CloneGame.Engine.Objects;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class Platform extends GameObject{
    public Platform(String texture , int  x , int y , int width , int height , World world){
        super(texture , x , y , width , height , world , BodyDef.BodyType.StaticBody);
    }
}
